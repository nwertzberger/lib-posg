package com.ideaheap.libposg.state;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.ideaheap.libposg.agent.Agent;
import com.ideaheap.libposg.simulator.PosgSimulatorException;

import java.util.Map;

/**
 * User: nwertzberger
 * Date: 4/16/13
 * Time: 9:21 PM
 * Email: wertnick@gmail.com
 *
 * JointActions are only defined by their agentAction, two JointActions with
 * different agent rewards or transitions are considered equal.
 */
public class JointAction {
    private final ImmutableMap<Agent, Action> agentActions;
    private final ImmutableMap<Agent, Double> agentRewards;
    private final ImmutableMap<Transition, Double> transitions;

    /**
     *
     * @param agentActions
     * @param agentRewards
     * @param transitions
     * The set of transitions and their probabilities of occurring,  Adding to 1.
     */
    public JointAction(
            ImmutableMap<Agent, Action> agentActions,
            ImmutableMap<Agent, Double> agentRewards,
            ImmutableMap<Transition, Double> transitions) {
        this.agentActions = agentActions;
        this.agentRewards = agentRewards;
        this.transitions = transitions;
    }

    public JointAction(
            Map<Agent, Action> agentActions,
            Map<Agent, Double> agentRewards,
            Map<Transition, Double> transitions) {
        this(
            ImmutableMap.copyOf(agentActions),
            ImmutableMap.copyOf(agentRewards),
            ImmutableMap.copyOf(transitions)
        );
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JointAction that = (JointAction) o;
        if (agentActions != null
                ? !agentActions.equals(that.agentActions)
                : that.agentActions != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return agentActions != null ? agentActions.hashCode() : 0;
    }

    @Override
    public String toString(){
        return "{" + agentActions + " => Reward:" + agentRewards +
                ", transitions:" + transitions + "}";
    }

    public ImmutableMap<Agent, Action> getAgentActions() {
        return agentActions;
    }

    public ImmutableMap<Agent, Double> getAgentRewards() {
        return agentRewards;
    }

    public Transition determineTransition() {
        Double choice = Math.random();
        for (Transition t: transitions.keySet()) {
            Double p = transitions.get(t);
            if (choice < p)
                return t;
            choice -= p;
        }
        throw new RuntimeException("Transition probabilities must add up to 1");
    }
}
