package com.ideaheap.libposg.state;

/**
 * User: nwertzberger
 * Date: 4/11/13
 * Time: 11:26 PM
 */
public class Action {
    private StateNode destState;

    public Action(StateNode destState) {
        this.destState = destState;
    }

    public Action withExecution(Runnable r) {
        return this;
    }


}
