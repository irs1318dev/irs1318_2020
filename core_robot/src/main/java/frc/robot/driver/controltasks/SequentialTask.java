package frc.robot.driver.controltasks;

import frc.robot.driver.common.IControlTask;

/**
 * Task that holds multiple other tasks and executes them sequentially (in order).
 * 
 */
public class SequentialTask extends DecisionSequentialTask implements IControlTask
{
    /**
     * Initializes a new SequentialTask
     * @param tasks to run
     */
    public SequentialTask(IControlTask[] tasks)
    {
    }

    /**
     * Create a sequential task from one or more tasks
     * @param tasks to create the sequence from
     * @return sequential task
     */
    public static SequentialTask Sequence(IControlTask... tasks)
    {
        return new SequentialTask(tasks);
    }
}
