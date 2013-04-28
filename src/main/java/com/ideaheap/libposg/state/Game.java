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
        for (JointAction a : jointActions.values()) {
            if (a.getAgentActions().get(agent) == action)
                actions.add(a);
        }
        return actions;
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

        if (jointActions != null ? !jointActions.equals(game.jointActions) : game.jointActions != null) return false;
        if (name != null ? !name.equals(game.name) : game.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (jointActions != null ? jointActions.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{" + name + "}";
    }

}
