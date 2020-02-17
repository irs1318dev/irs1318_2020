package frc.robot.driver;

import com.acmerobotics.roadrunner.geometry.*;
import com.acmerobotics.roadrunner.path.*;
import com.acmerobotics.roadrunner.path.heading.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.robot.common.robotprovider.*;
import frc.robot.driver.common.*;
import frc.robot.driver.controltasks.*;

@Singleton
public class AutonomousRoutineSelector
{
    private static final String LogName = "auto";
    private final IDashboardLogger logger;

    private final PathManager pathManager;

    private final ISendableChooser<StartPosition> positionChooser;
    private final ISendableChooser<AutoRoutine> routineChooser;

    public enum StartPosition
    {
        Center,
        Left,
        Right
    }

    public enum AutoRoutine
    {
        None,
        Shoot3Pick3,
        EightPowerCellClose,
    }

    /**
     * Initializes a new AutonomousRoutineSelector
     */
    @Inject
    public AutonomousRoutineSelector(
        IDashboardLogger logger,
        PathManager pathManager,
        IRobotProvider provider)
    {
        // initialize robot parts that are used to select autonomous routine (e.g. dipswitches) here...
        this.logger = logger;
        this.pathManager = pathManager;

        INetworkTableProvider networkTableProvider = provider.getNetworkTableProvider();

        this.routineChooser = networkTableProvider.getSendableChooser();
        this.routineChooser.addDefault("None", AutoRoutine.None);
        this.routineChooser.addObject("Shoot3Pick3", AutoRoutine.Shoot3Pick3);
        networkTableProvider.addChooser("Auto Routine", this.routineChooser);

        this.positionChooser = networkTableProvider.getSendableChooser();
        this.positionChooser.addDefault("center", StartPosition.Center);
        this.positionChooser.addObject("left", StartPosition.Left);
        this.positionChooser.addObject("right", StartPosition.Right);
        networkTableProvider.addChooser("Start Position", this.positionChooser);

        this.generateDynamicPaths();
    }

    /**
     * Check what routine we want to use and return it
     * 
     * @return autonomous routine to execute during autonomous mode
     */
    public IControlTask selectRoutine()
    {
        StartPosition startPosition = this.positionChooser.getSelected();
        AutoRoutine routine = this.routineChooser.getSelected();
        this.logger.logString(AutonomousRoutineSelector.LogName, "selected", startPosition.toString() + "." + routine.toString());

        if (startPosition == StartPosition.Right) 
        {
            if (routine == AutoRoutine.Shoot3Pick3) 
            {
                return shootDriveShoot(1.25, "shoot 3 pick 3 back", "shoot 3 pick 3 forward");
            }
        }

        if (startPosition == StartPosition.Center)
        {
            if (routine == AutoRoutine.EightPowerCellClose)
            {
                return shootDriveShoot(3.5,  "eight power cell close back",  "eight power cell close forward");
            }
        }
        return AutonomousRoutineSelector.GetFillerRoutine();
    }

    /**
     * Gets an autonomous routine that does nothing
     */
    private static IControlTask GetFillerRoutine()
    {
        return new WaitTask(0);
    }

    /**
     * Generate any ad-hoc paths and add them to the mapping
     */
    private void generateDynamicPaths()
    {
        TangentInterpolator interpolator = new TangentInterpolator();

        this.pathManager.addPath(
            "example",
            RoadRunnerTankTranslator.convert(
                new PathBuilder(new Pose2d(0, 0, 0))
                    .lineTo(new Vector2d(120, 0), interpolator)
                    .build(),
                false));

        this.pathManager.addPath(
            "shoot 3 pick 3 back", 
            RoadRunnerTankTranslator.convert(
                new PathBuilder(new Pose2d(0, 0, 0))
                    .lineTo(new Vector2d(157.66, 0), interpolator)
                    .build(),
                true));

        this.pathManager.addPath(
            "shoot 3 pick 3 forward", 
            RoadRunnerTankTranslator.convert(
                new PathBuilder(new Pose2d(0, 0, 0))
                    .lineTo(new Vector2d(66.058, 0), interpolator) // tune x value for final shooting position
                    .build(),
                false));

        this.pathManager.addPath(
            "eight power cell close back",
            RoadRunnerTankTranslator.convert(
                new PathBuilder(new Pose2d(0, 0, 0))
                    .splineTo(new Pose2d(80.6, 65.869, 0), interpolator)
                    .lineTo(new Vector2d(218.413, 65.869))
                    .build(),
                true));

        this.pathManager.addPath(
            "eight power cell close forward",
            RoadRunnerTankTranslator.convert(
                new PathBuilder(new Pose2d(218.413, 65.869, 0)) 
                    .lineTo(new Vector2d(80.6, 65.869)) // tune x value for final shooting position
                    .build(),
                false));

        this.pathManager.addPath(
            "poach segment 1",
            RoadRunnerTankTranslator.convert(
                new PathBuilder(new Pose2d(0, 0, 0)) 
                    .lineTo(new Vector2d(132, 0)) 
                    .build(),
                true));
                
        this.pathManager.addPath(
            "poach segment 2",
            RoadRunnerTankTranslator.convert(
                new PathBuilder(new Pose2d(0, 0, 0)) 
                    .splineTo(new Pose2d(0, 200, 90)) // tune y value and angle for shooting position (90 might need to be 270) 
                    .build(),
                true));

        this.pathManager.addPath(
            "poach segment 3 for additional power cell",
            RoadRunnerTankTranslator.convert(
                new PathBuilder(new Pose2d(0, 0, 0)) 
                    .lineTo(new Vector2d(132, 0)) 
                    .build(),
                true));
    }

    private static IControlTask shootDriveShoot(double intakingDuration, String pathName1, String pathName2) // shoots three pc on init. line, then picks three from trench and shoots them
    {
        return SequentialTask.Sequence(
            ConcurrentTask.AnyTasks(
                new FlyWheelVisionSpinTask(),
                SequentialTask.Sequence(
                    new TurretVisionCenteringTask(false),
                    new WaitTask(1.0),
                    new FullHopperShotTask())),
            ConcurrentTask.AllTasks(
                SequentialTask.Sequence(
                    new IntakePositionTask(true),
                    new IntakeOuttakeTask(intakingDuration, true)),
                new FollowPathTask(pathName1)),
            new WaitTask(0.15),
            ConcurrentTask.AllTasks(
                new IntakePositionTask(false),
                new FollowPathTask(pathName2)),
            ConcurrentTask.AllTasks(
                new FlyWheelVisionSpinTask(),
                SequentialTask.Sequence(
                    new TurretVisionCenteringTask(false),
                    new WaitTask(1.0),
                    new FullHopperShotTask()))
        );
    }
}








































































































































/*
                                      .                                                             
                                    .;+;+                                                           
                                    .+;;'   `,+'.                                                   
                                    ;';;+:..`` :+'+                                                 
                                    ,'+`    .+;;;;;+                                                
                                     ;,,, .+;;;;;'+++;                                              
                                     ;' `+;;;;;#+'+'+''#:.                                          
                                     '`+';;;'+;+;+++'''+'.                                          
                                     #';;;;#';+'+'''+''+'                                           
                                     ;;;;#;,+;;+;;;'''''':                                          
                                     ';'++'.`+;;'';;''+'',                                          
                                     :#'#+'``.'+++'#++'':`                                          
                                      `';++##```##+.''.##                                           
                                      +++#   #`#  `++++                                             
                                      +'#+ # :#: # ##'+                                             
                                      `#+#   +`+   #'#`                                             
                                       :,.+,+,`:+,+..,                                              
                                       `,:```,`,`.`;,                                               
                                        :+.;``.``;.#;                                               
                                        .'``'+'+'``'.                                               
                                         ,````````..                                                
                                          :```````:                                                 
                                          +``.:,``'                                                 
                                          :```````:                                                 
                                           +`````+                                                  
                                            ';+##                                                   
                                            '```'                                                   
                                           `'```'`                                                  
                                         .+''''''''                                                 
                                        +;;;;;;;;''#                                                
                                       :       `   `:                                               
                                      `,            '                                               
                                      +              '                                              
                                     ,;';,``.``.,,,:;#                                              
                                     +;;;;;;;;;;;;;;;'                                              
                                    ,';;;;;;;;;;;;;;;',                                             
                                    +:;;;;;;';;;;;;;;;+                                             
                                   `.   .:,;+;;:::;.``,                                             
                                   :`       #,       `.`                                            
                                   +       # ;        .;                                            
                                  .;;,`    ,         `,+                                            
                                  +;;;;;;''';;;;;;;';;';                                            
                                  +;;;;;;;';;;;;;;;;;'';;                                           
                                 `';;;;;;';;;;;;;;;;;';;+                                           
                                 + `:;;;;+;;;;;;;;';'''::                                           
                                 '     `:  ```````    ,  ,                                          
                                :       '             ;  +                                          
                                '`     ..             ,  ,                                          
                               ,;;;;;..+,`        ```.':;',                                         
                               +;;;;;;'+;;;;;;;;;;;;;;+;;;+                                         
                               ';;;;;;++;;;;;;;;;;;;;;';;;+                                         
                              `.:';;;;;#;;;;;;;;;;;;;;';;;;`                                        
                              ;    `,; ',:;;';;';;;;;:;``  +                                        
                              +      ; ;              ;    `                                        
                              ;      : +              '    `;                                       
                              ';:`` `` '              :`,:;;+                                       
                             `';;;;'+  +,..```````..:;#;;;;;;.                                      
                             `;;;;;;+  +;;;;;;;;;;;;;':';;;;;#                                      
                             .;;;;;;+  ';;;;;;;;;;;;;;,';;;;` .                                     
                             : `.;;'+  +;;;;;;;;;;;;;','.`    +                                     
                             '      ;  +.,,;:;:;;;,..`: ,     ``                                    
                             +      ,  '              : ;   .;'+                                    
                             +.`   ``  +              ;  ;:;;;;':                                   
                             ';;;';;`  +             .'  ;;;;;;;+                                   
                             ';;;;;'   :+++#++##+#+''',   +;;;;.`.                                  
                             +;;;;;'   +;;::;;;+:+;;'',   ,;;.   +                                  
                            ``:;;;;+   +;;:;;;:+;+;;++;    +     .`                                 
                             `   ``'   +;;;;;;;+;+;;'+;     ,   ;#,                                 
                            .      ;   ';;;;;;;;;;;;++'     + .+``.;                                
                            ``     ;   ';;;;;;+;';;;'+'      #`````:,                               
                             +++;,:.   ':;''++;:';:;'';      +``````,`                              
                             ,```,+    +;;';:;;+;;;;'';      +``````,+                              
                            .``````:   ;:;;++';;;;;;';,      ,``:#``+`.                             
                            ,``````'   `';;;;:;;;;;;+;`     '+``+:'`..'                             
                            ,``````'    +;;;;;;;;;;;''     ;:'``#;;.`++                             
                            ```````;    `;:;;;;;;;;;;#     ':'``++:+`+;                             
                            ```'`.`;     +;;;;;;;;;;;+    :::#``' +#`';                             
                            ,``'`:`#     `';;;;;;;;;;+    +:'.`,. ++`;;                             
                            +`.``+`'     :#;;;;;;;;;;;`   +:# ,`  +;`.'                             
                           ,.`+`.:.      ##;;;;;;;;;;;'   ,'`     ;:+#                              
                           '`;.`+`#      ##+;;;;;;;;;;+          ,::;                               
                           ,+,`:``,     :###;;;;;;;;;:'          +:;`                               
                            '`,,`+      ';##';;;;;;;;;;.         +:#                                
                             '+.+       +;;##;;;;;;;;;;'         ;:;                                
                               `       :;;;+#;;;;;;;;;;+        ;::`                                
                                       +;;;;#+;;;;;;;;;;        +:'                                 
                                       ';;;;+#;;;;;;;;;;.       ;:'                                 
                                      ,;;;;;;#;;;;;;;;;;+      +::.                                 
                                      +;;;;;;'';;;;;;;;;'      +:+                                  
                                     `;;;;;;;;#;;;;;;;;;;`    `;:+                                  
                                     ,;;;;;;;;+;;;;;;;;;;+    ':;,                                  
                                     +;;;;;;;;;+;;;;;;;;;'    +:+                                   
                                    .;;;;;;;;;+,;;;;;;;;;;`   ;;+                                   
                                    ';;;;;;;;;, ';;;;;;:;;,  +;:,                                   
                                    ';;;;;;;;'  +;;;;;;;;;'  +:+                                    
                                   ;;;;;;;;;;+  ,;;;;;;;;;+  ;:'                                    
                                   +;;;;;;;;;    ';;;;;;;;;`;:;`                                    
                                   ;;;;;;;;;+    +;;;;;;;;;+#:+                                     
                                  ';;;;;;;;;:    ;;;;;;;;;;';:'                                     
                                 `';;;;;;;:'      ';;;;;;;;;;:.                                     
                                 .;;;;;;;;;+      +;;;;;;;;;'+                                      
                                 +;;;;;;;;;       ';;;;;;;;;#+                                      
                                `;;;;;;;;;+       `;;;;;;;;;;`                                      
                                +;;;;;;;;;.        +;;;;;;;;;`                                      
                                ';;;;;;;:'         ;;;;;;;;;;;                                      
                               :;;;;;;;;;:         `;;;;;;;;;+                                      
                               +;;;;;;;;;           ';;;;;;;;;`                                     
                               ;;;;;;;;;+           ';;;;;;;;;:                                     
                              ';;;;;;;;;;           ,;;;;;;;;;+                                     
                              ':;;;;;;;'             +;;;;;;;;;                                     
                             .;:;;;;;;;'             +;;;;;;;;;:                                    
                             +;;;;;;;;;`             .;;;;;;;;;+                                    
                            `;;;;;;;;;+               ;:;;;;;;;;`                                   
                            ;;;;;;;;;;.               +;;;;;;;::.                                   
                            ';;;;;;;;'`               :;;;;;;;;:+                                   
                           :;;;;;;;;:'                ';;;;;;;;;'                                   
                           ';;;;;;;;'`                +#;;;;;;;;;`                                  
                          `;;;;;;;;;+                 '';;;;;;;;;+                                  
                          +;;;;;;;;;.                '::;;;;;;;;;+                                  
                          ;;;;;;;;;+                 #:'';;;;;;;;;`                                 
                         .#;;;;;;;;'                `;:+;;;;;;;;;;;                                 
                         ':'';;;;;;                 '::.,;;;;;;;;;+                                 
                        +::::+';;;+                 ':'  +:;;;;;;;;`                                
                       `;;;::::;#+:                `;:+  +;;;;;;;:;;      '#+,                      
                       +#::::::::;'`               +:;,  `;;;;:;;'#';;;;;::;:'`                     
                       ;:''::::::::#`              +:'    ';:;;+'::;;:;::::::''                     
                       +::;+':::::::'.            .:;+    '''+;::;:;:::;:::;':'                     
                        ';;:;'';:::::':           +::.     +:::::::::::::;#;:#                      
                         .''##;#;:;;:::'+        `+;'      ;:;::::::::;'+;:'+                       
                           ` `+:;+:;::;::+       +:;#      ';:::;:+#+';:::+.                        
                              ,+::+#';::;+       ';::      #:;;'+';'''++:`                          
                                '':::;'''#      ,:;;`      #';:;;:+                                 
                                 `:'++;;':       :++       .;;:;;#,                                 
                                       `                    '':``                                   


*/
