package com.ideaheap.libposg.state;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.ideaheap.libposg.agent.Agent;
import com.ideaheap.libposg.agent.StrategyTreeAgent;

import java.util.*;

/**
 * User: nwertzberger
 * Date: 4/15/13
 * Time: 7:56 PM
 * Email: wertnick@gmail.com
 *
 * A game is a set of joint actions that agents can take.
 *
 */
public class Game {
    private Map<ImmutableMap<Agent, Action>, JointAction> jointActions = new HashMap<ImmutableMap<Agent, Action>, JointAction>();

    private String name;

    public Game(String name) {
        this.name = name;
    }

    public void addJointAction(JointAction action) {
        jointActions.put(action.getAgentActions(), action);
    }

    public Game withJointAction(JointAction action) {
        addJointAction(action);
        return this;
    }

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

    public JointAction getJointAction(Map<Agent, Action> agentActions) {
        return agentActions == null ?  null : jointActions.get(ImmutableMap.copyOf(agentActions));
    }

    public Set<JointAction> getJointActionsWithAgentAction(Agent agent, Action action) {
        Set<JointAction> actions = new HashSet<JointAction>();
        for (JointAction a : actions) {
            if (a.getAgentActions().get(agent) == action) {
                actions.add(a);
            }
        }
        return actions;
    }
}
