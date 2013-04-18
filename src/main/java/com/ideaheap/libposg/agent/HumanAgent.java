package com.ideaheap.libposg.agent;

import com.ideaheap.libposg.state.Action;
import com.ideaheap.libposg.state.Game;
import com.ideaheap.libposg.state.Observation;

import java.util.*;

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
    private Map<String, Action> actions = new HashMap<String, Action>();
    private Map<String, Observation> observations = new HashMap<String, Observation>();
    private Integer horizon;
    private Map<String, Double> belief;
    private Map<String, Game> games;

    public HumanAgent(String name) {
        this.name = name;
    }

    @Override
    public void observe(Set<Observation> observations) {
        for (Observation ob : observations) {
            System.out.println("You observed " + ob + "!");
        }
    }

    @Override
    public Action decideGameAction() {
        System.out.print("Action? " + actions.keySet() + " > ");
        Action chosenAction = null;
        Scanner scan = new Scanner(System.in);
        while (chosenAction == null) {
            chosenAction = actions.get(scan.nextLine().trim());
        }
        return chosenAction;
    }

    @Override
    public void setGames(Map<String, Game> games) {
        this.games = games;
    }

    /*
     * Literally everything after this is boiler plate... Go Java!
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HumanAgent that = (HumanAgent) o;

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
        return "{" + this.name + "(Human)}";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Action getAction(String actionName) {
        return actions.get(actionName);
    }

    @Override
    public void addAction(Action action) {
        actions.put(action.getName(), action);
    }

    @Override
    public Observation getObservation(String observationName) {
        return observations.get(observationName);
    }

    @Override
    public void addObservation(Observation observation) {
        observations.put(observation.getName(), observation);
    }

    @Override
    public void setHorizon(Integer horizon) {
        this.horizon = horizon;
    }

    @Override
    public void setBelief(Map<String, Double> belief) {
        this.belief = belief;
    }

    public HumanAgent withAction(Action a) {
        this.addAction(a);
        return this;
    }

    public HumanAgent withObservation(Observation o) {
        this.addObservation(o);
        return this;
    }

    public HumanAgent withHorizon(int h) {
        this.setHorizon(h);
        return this;
    }

    public HumanAgent withBelief(Map<String, Double> belief) {
        this.setBelief(belief);
        return this;
    }
}
