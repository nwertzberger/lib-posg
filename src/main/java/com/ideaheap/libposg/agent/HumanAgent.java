package com.ideaheap.libposg.agent;

import com.ideaheap.libposg.state.Action;
import com.ideaheap.libposg.state.Observation;

/**
 * User: nwertzberger
 * Date: 4/14/13
 * Time: 9:25 PM
 * Email: wertnick@gmail.com
 * <p/>
 * It looks like I forgot to explain this class.
 */
public class HumanAgent implements Agent {
    private String name;

    public HumanAgent(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Action getAction(String actionName) {
        return new Action(actionName);
    }

    @Override
    public Observation getObservation(String observationName) {
        return new Observation(observationName);
    }
}
