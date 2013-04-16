package com.ideaheap.libposg.agent;

import com.ideaheap.libposg.state.Action;
import com.ideaheap.libposg.state.Observation;

import java.util.Map;

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
    public void addAction(Action action) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Observation getObservation(String observationName) {
        return new Observation(observationName);
    }

    @Override
    public void addObservation(Observation observation) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setHorizon(Integer horizon) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setBelief(Map<String, Double> belief) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
