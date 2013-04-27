package com.ideaheap.libposg.agent;

import com.google.common.collect.ImmutableSet;
import com.ideaheap.libposg.state.Action;
import com.ideaheap.libposg.state.Game;
import com.ideaheap.libposg.state.Observation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * User: nwertzberger
 * Date: 4/20/13
 * Time: 12:41 PM
 * Email: wertnick@gmail.com
 * <p/>
 * This is simply here to deal with the fact that more than one action can be equivalent...
 */
public class PolicyTreeNode {
    private final Action action;
    private final Double expectedValue;
    private final Map<Game, Double> belief;

    private Map<ImmutableSet<Observation>, PolicyTreeNode> transitions =
            new HashMap<ImmutableSet<Observation>, PolicyTreeNode>();

    public PolicyTreeNode(Action action, Double expectedValue, Map<Game, Double> belief) {
        this.action = action;
        this.expectedValue = expectedValue;
        this.belief = belief;
    }

    public PolicyTreeNode next(Set<Observation> o) {
        return transitions.get(ImmutableSet.copyOf(o));
    }

    public void addTransition(Set<Observation> o, PolicyTreeNode node) {
        transitions.put(ImmutableSet.copyOf(o), node);
    }

    public Double getExpectedValue() {
        return expectedValue;
    }

    public Map<Game, Double> getBelief() {
        return belief;
    }

    public Action getAction() {
        return action;
    }

    public PolicyTreeNode withTransitions(Map<Set<Observation>, PolicyTreeNode> transitions) {
        for (Set<Observation> obs : transitions.keySet()) {
            addTransition(obs, transitions.get(obs));
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuffer res = new StringBuffer();

        res.append("{");
        res.append(action);
        res.append(", ");
        for (ImmutableSet<Observation> obs : transitions.keySet()) {
            res.append(obs);
            res.append(" => ");
            res.append(transitions.get(obs));
        }
        res.append("}");
        return res.toString();
    }
}
