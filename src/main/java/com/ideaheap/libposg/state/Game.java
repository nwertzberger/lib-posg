package com.ideaheap.libposg.state;

import com.google.common.collect.ImmutableMap;
import com.ideaheap.libposg.agent.Agent;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User: nwertzberger
 * Date: 4/15/13
 * Time: 7:56 PM
 * Email: wertnick@gmail.com
 * <p/>
 * A game is a set of joint actions that agents can take.
 */
public class Game {
    private final String name;
    private ImmutableMap<ImmutableMap<Agent, Action>, JointAction> jointActions;

    public Game(String name) {
        this.name = name;
    }

    public void setJointActions(Map<ImmutableMap<Agent, Action>, JointAction> jointActions) {
        this.jointActions = ImmutableMap.copyOf(jointActions);
    }

    public JointAction getJointAction(Map<Agent, Action> agentActions) {
        if (agentActions == null)
            return null;
        else
            return jointActions.get(ImmutableMap.copyOf(agentActions));
    }

    public Set<JointAction> getJointActionsWithAgentAction(Agent agent, Action action) {
        Set<JointAction> actions = new HashSet<JointAction>();
        for (JointAction a : actions) {
            if (a.getAgentActions().get(agent) == action)
                actions.add(a);
        }
        return actions;
    }

    public JointAction getBestResponseJointAction(Agent agent, Action action) {
        Set<JointAction> actions = getJointActionsWithAgentAction(agent, action);
        return actions.iterator().next();
    }

    /* CRUFT */

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;
        if (jointActions != null
                ? !jointActions.equals(game.jointActions)
                : game.jointActions != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return jointActions != null ? jointActions.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "{" + jointActions + "}";
    }

}
