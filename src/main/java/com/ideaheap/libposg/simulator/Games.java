package com.ideaheap.libposg.simulator;

import com.google.common.collect.ImmutableMap;
import com.ideaheap.libposg.agent.Agent;
import com.ideaheap.libposg.state.Action;
import com.ideaheap.libposg.state.Game;
import com.ideaheap.libposg.state.JointAction;

import java.util.HashMap;
import java.util.Map;

/**
 * User: nwertzberger
 * Date: 4/27/13
 * Time: 2:40 PM
 */
public class Games {
    private final Game game;
    private Map<ImmutableMap<Agent, Action>, JointAction> jointActions = new HashMap<ImmutableMap<Agent, Action>, JointAction>();

    public Games(Game g) {
        this.game = g;
    }

    public Games withJointAction(JointAction ja) {
        jointActions.put(ja.getAgentActions(), ja);
        return this;
    }

    public Game build() {
        game.setJointActions(jointActions);
        return game;
    }
}
