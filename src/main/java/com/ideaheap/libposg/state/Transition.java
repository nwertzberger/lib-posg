package com.ideaheap.libposg.state;

import com.google.common.collect.ImmutableMap;
import com.ideaheap.libposg.agent.Agent;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nwertzberger
 * Date: 4/11/13
 * Time: 11:41 PM
 *
 */
public class Transition {

    private final Game game;
    private final ImmutableMap<Agent, Map<Observation, Double>> observations;

    public Transition(
            Game destGame,
            ImmutableMap<Agent, Map<Observation, Double>> observations) {
        this.game = destGame;
        this.observations = observations;
    }

    public Map<Observation, Double> getAgentObservations(Agent agent) {
        return this.observations.get(agent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transition that = (Transition) o;
        // One of the uses of == on purpose
        if (game != that.game) return false;
        if (observations != null ? !observations.equals(that.observations) : that.observations != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (game != null ? game.getName().hashCode() : 0);
        result = 31 * result + (observations != null ? observations.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{observes " + observations + " and transitions to " + game.getName() + "}";
    }

    public Game getGame() {
        return game;
    }
}
