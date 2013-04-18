package com.ideaheap.libposg.agent;

import com.ideaheap.libposg.state.Action;
import com.ideaheap.libposg.state.Game;
import com.ideaheap.libposg.state.Observation;

import java.util.HashMap;
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
public abstract class Agent {

    private String name;
    protected Map<String, Action> actions = new HashMap<String, Action>();
    private Map<String, Observation> observations = new HashMap<String, Observation>();
    private Integer horizon;
    private Map<String, Double> belief;
    private Map<String, Game> games;

    public abstract void observe(Set<Observation> observations);
    public abstract Action decideGameAction();

    public void setGames(Map<String, Game> games) {
        this.games = games;
    }

    public Agent withHorizon(int h) {
        this.setHorizon(h);
        return this;
    }

    public Agent withBelief(Map<String, Double> belief) {
        this.setBelief(belief);
        return this;
    }

    /*
     * Literally everything after this is boiler plate... Go Java!
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agent that = (HumanAgent) o;

        if (actions != null ? !actions.equals(that.actions) : that.actions != null) return false;
        if (horizon != null ? !horizon.equals(that.horizon) : that.horizon != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (observations != null ? !observations.equals(that.observations) : that.observations != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (actions != null ? actions.hashCode() : 0);
        result = 31 * result + (observations != null ? observations.hashCode() : 0);
        result = 31 * result + (horizon != null ? horizon.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{" + this.name + "}";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Action getAction(String actionName) {
        return actions.get(actionName);
    }

    public void addAction(Action action) {
        actions.put(action.getName(), action);
    }

    public Observation getObservation(String observationName) {
        return observations.get(observationName);
    }

    public void addObservation(Observation observation) {
        observations.put(observation.getName(), observation);
    }

    public void setHorizon(Integer horizon) {
        this.horizon = horizon;
    }

    public void setBelief(Map<String, Double> belief) {
        this.belief = belief;
    }

    public Agent withAction(Action a) {
        this.addAction(a);
        return this;
    }

    public Agent withObservation(Observation o) {
        this.addObservation(o);
        return this;
    }

    public Map<String, Double> getBelief() {
        return belief;
    }

    public Map<String, Game> getGames() {
        return games;
    }

}
