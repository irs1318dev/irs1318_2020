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
        Poach,
        ThreePlusTwo,
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
        this.routineChooser.addObject("EightPowerCellClose", AutoRoutine.EightPowerCellClose);
        this.routineChooser.addObject("Poach", AutoRoutine.Poach);
        this.routineChooser.addObject("ThreePlusTwo", AutoRoutine.ThreePlusTwo);
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

        if (routine == AutoRoutine.Poach) 
        {
            return poachDrive();
        }

        if (routine == AutoRoutine.Shoot3Pick3)
        {
            return shootDriveShoot(1.25, "shoot 3 pick 3 forward", "shoot 3 pick 3 back");
        }

        if (routine == AutoRoutine.EightPowerCellClose)
        {
            return shootDriveShoot(3.5, "eight power cell close forward", "eight power cell close back");
        }

        if (routine == AutoRoutine.ThreePlusTwo)
        {
            return driveShoot(1.5,"3 plus 2 straight forwards", "3 plus 2 straight backwards");
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
            "shoot 3 pick 3 forward", 
            RoadRunnerTankTranslator.convert(
                new PathBuilder(new Pose2d(0, 0, 0))
                    .lineTo(new Vector2d(157.66, 0), interpolator)
                    .build(),
                false));

        this.pathManager.addPath(
            "shoot 3 pick 3 back", 
            RoadRunnerTankTranslator.convert(
                new PathBuilder(new Pose2d(0, 0, 0))
                    .lineTo(new Vector2d(66.058, 0), interpolator) // tune x value for final shooting position
                    .build(),
                true));

        this.pathManager.addPath(
            "eight power cell close forward",
            RoadRunnerTankTranslator.convert(
                new PathBuilder(new Pose2d(0, 0, 0))
                    .splineTo(new Pose2d(80.6, 65.869, 0), interpolator)
                    .lineTo(new Vector2d(218.413, 65.869))
                    .build(),
                false));

        this.pathManager.addPath(
            "eight power cell close back",
            RoadRunnerTankTranslator.convert(
                new PathBuilder(new Pose2d(218.413, 65.869, 0)) 
                    .lineTo(new Vector2d(80.6, 65.869)) // tune x value for final shooting position
                    .build(),
                true));

        this.pathManager.addPath(
            "3 plus 2 straight forward",
            RoadRunnerTankTranslator.convert(
                new PathBuilder(new Pose2d(0, 0, 0)) 
                    .lineTo(new Vector2d(120, 0)) 
                    .build(),
                false));

        this.pathManager.addPath(
            "3 plus 2 straight back",
            RoadRunnerTankTranslator.convert(
                new PathBuilder(new Pose2d(0, 0, 0)) 
                    .lineTo(new Vector2d(36, 0)) 
                    .build(),
                true));

        this.pathManager.addPath(
            "poach segment 1",
            RoadRunnerTankTranslator.convert(
                new PathBuilder(new Pose2d(0, 0, 0)) 
                    .lineTo(new Vector2d(132, 0)) 
                    .build(),
                false));
                
        this.pathManager.addPath(
            "poach segment 2",
            RoadRunnerTankTranslator.convert(
                new PathBuilder(new Pose2d(0, 0, 0)) 
                    .splineTo(new Pose2d(128, 200, 0), interpolator) // y value and angle for shooting position (90 might need to be 270) 
                    //.lineTo(new Vector2d(128, 200))
                    .build(),
                true));

        this.pathManager.addPath(
            "poach segment 3",
            RoadRunnerTankTranslator.convert(
                new PathBuilder(new Pose2d(0, 0, 0)) 
                    .splineTo(new Pose2d(112, -50, 157.5)) 
                    .build(),
                false));

        this.pathManager.addPath(
            "poach segment 4", 
            RoadRunnerTankTranslator.convert(
                new PathBuilder(new Pose2d(0, 0, 0)) 
                    .splineTo(new Pose2d(112, 50, 0)) 
                    .build(),
                true));
                    
            
        this.pathManager.addPath(
            "simple back",
            RoadRunnerTankTranslator.convert(
                new PathBuilder(new Pose2d(0, 0, 0)) 
                    .lineTo(new Vector2d(12, 0)) 
                    .build(),
                false));

        this.pathManager.addPath(
            "eight power cell far forward",
            RoadRunnerTankTranslator.convert(
                new PathBuilder(new Pose2d(0, 0, 0))
                    .splineTo(new Pose2d(80.6, 65.869, 0), interpolator)
                    .lineTo(new Vector2d(218.413, 65.869))
                    .build(),
                false));
    }

    private static IControlTask shootDriveShoot(double intakingDuration, String pathName1, String pathName2) // shoots three pc on init. line, then picks three from trench and shoots them
    {
        return SequentialTask.Sequence(
            /*ConcurrentTask.AnyTasks(
                new FlyWheelVisionSpinTask(),
                SequentialTask.Sequence(
                    new TurretVisionCenteringTask(false, true),
                    new WaitTask(1.0),
                    new FullHopperShotTask())),
            ConcurrentTask.AllTasks(
                SequentialTask.Sequence(
                    new IntakePositionTask(true),
                    new IntakeOuttakeTask(intakingDuration, true)),*/
                new FollowPathTask(pathName1),
            /*new WaitTask(0.15),
            ConcurrentTask.AllTasks(
                new IntakePositionTask(false),*/
                new FollowPathTask(pathName2)
            /*ConcurrentTask.AnyTasks(
                new FlyWheelVisionSpinTask(),
                SequentialTask.Sequence(
                    new TurretVisionCenteringTask(false, true),
                    new WaitTask(1.0),
                    new FullHopperShotTask()))*/
        );
    }

    private static IControlTask driveShoot(double intakingDuration, String forward, String back)
    {
        return SequentialTask.Sequence(
            ConcurrentTask.AllTasks(
                SequentialTask.Sequence(
                    new IntakePositionTask(true),
                    new IntakeOuttakeTask(intakingDuration, true)),
                new FollowPathTask(forward),
            new WaitTask(0.15),
            ConcurrentTask.AllTasks(
                new IntakePositionTask(true),
                new FollowPathTask(back)),
            ConcurrentTask.AnyTasks(
                new FlyWheelVisionSpinTask(),
                SequentialTask.Sequence(
                    new TurretVisionCenteringTask(false, true),
                    new WaitTask(1.0),
                    new FullHopperShotTask()))));

    }

    private static IControlTask poachOnlyTwo() 
    {
        return SequentialTask.Sequence(
            ConcurrentTask.AllTasks(
                SequentialTask.Sequence(
                    new IntakePositionTask(true),
                    new IntakeOuttakeTask(2.0, true)),
                new FollowPathTask("poach segment 1")),
            ConcurrentTask.AllTasks(
                new IntakePositionTask(false),
                new FollowPathTask("poach segment 2"),
                new TurretAlignTask(270.0)),
            ConcurrentTask.AnyTasks(
                new FlyWheelVisionSpinTask(),
                SequentialTask.Sequence(
                    new TurretVisionCenteringTask(false, true),
                    new WaitTask(1.0),
                    new FullHopperShotTask()))
        );
    }
    
    private static IControlTask poachTwoAndMore()
    {
        return SequentialTask.Sequence(
            poachOnlyTwo(),
            new FollowPathTask("poach segment 3"),
            ConcurrentTask.AllTasks(
                new IntakePositionTask(true),
                new IntakeOuttakeTask(1.0, true)),
            new FollowPathTask("poach segment 4"),
            ConcurrentTask.AnyTasks(
                new FlyWheelVisionSpinTask(),
                SequentialTask.Sequence(
                    new TurretVisionCenteringTask(false, true),
                    new WaitTask(1.0),
                    new FullHopperShotTask()))
        );
    }
    private static IControlTask shootGoBack(double intakingDuration)
    {
        return SequentialTask.Sequence(
            ConcurrentTask.AnyTasks(
                new FlyWheelVisionSpinTask(),
                SequentialTask.Sequence(
                    new TurretVisionCenteringTask(false, true),
                    new WaitTask(1.0),
                    new FullHopperShotTask()),
            new FollowPathTask("simple back")));
    }  

    private static IControlTask shootSplineBackShoot(double intakingDuration) // shoots three pc on init. line, then picks three from trench and shoots them
    {
        return SequentialTask.Sequence(
            ConcurrentTask.AnyTasks(
                new FlyWheelVisionSpinTask(),
                SequentialTask.Sequence(
                    new TurretVisionCenteringTask(false, true),
                    new WaitTask(1.0),
                    new FullHopperShotTask())),
            new FollowPathTask("eight power cell close forward"),
            ConcurrentTask.AllTasks(
                SequentialTask.Sequence(
                    new IntakePositionTask(true),
                    new IntakeOuttakeTask(intakingDuration, true)),
                new FollowPathTask("3 balls backwards line")),
            new WaitTask(0.15),
            ConcurrentTask.AnyTasks(
                new FlyWheelVisionSpinTask(),
                SequentialTask.Sequence(
                    new TurretVisionCenteringTask(false, true),
                    new WaitTask(1.0),
                    new FullHopperShotTask())));
    }

    private static IControlTask poachDrive() 
    {
        return SequentialTask.Sequence(
            new FollowPathTask("poach segment 1"),
            new FollowPathTask("poach segment 2"),
            new FollowPathTask("poach segment 3"),
            new FollowPathTask("poach segment 4"));
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
