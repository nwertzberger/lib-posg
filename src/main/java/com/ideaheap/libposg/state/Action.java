package com.ideaheap.libposg.state;

/**
 * User: nwertzberger
 * Date: 4/11/13
 * Time: 11:26 PM
 *
 * The action type is used to elicit changes in the environment.
 *
 */
public class Action {
    private Runnable execution;
    public Action withExecution(Runnable exec) {
        this.execution = exec;
        return this;
    }

    public void act() {
        execution.run();
    }
}
