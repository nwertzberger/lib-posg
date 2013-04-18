package com.ideaheap.libposg.agent;

import com.ideaheap.libposg.state.Action;
import com.ideaheap.libposg.state.Game;
import com.ideaheap.libposg.state.Observation;

import java.util.Map;
import java.util.Set;

/**
 * User: nwertzberger
 * Date: 4/14/13
 * Time: 3:33 PM
 * Email: wertnick@gmail.com
 *
 * The agent is the interface that must be implemented for using the simulator.
 */
public interface Agent {
    public String getName();

    public Action getAction(String actionName);
    public void addAction(Action action);

    public Observation getObservation(String observationName);
    public void addObservation(Observation observation);

    public void setHorizon(Integer horizon);
    public void setBelief(Map<String,Double> belief);

    public void observe(Set<Observation> o);
    public Action decideGameAction();
    public void setGames(Map<String,Game> games);
}
