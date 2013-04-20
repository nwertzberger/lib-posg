package com.ideaheap.libposg.agent;

import com.ideaheap.libposg.state.Action;
import com.ideaheap.libposg.state.Game;
import com.ideaheap.libposg.state.JointAction;
import com.ideaheap.libposg.state.Observation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User: nwertzberger
 * Date: 4/17/13
 * Time: 8:49 PM
 * Email: wertnick@gmail.com
 *
 * This class generates a tree of horizon depth based on the current belief.
 *
 */
public class StrategyTreeAgent extends Agent {
    private static final Double EPSILON = Double.MIN_VALUE;
    private PolicyTreeNode policy = null;

    public StrategyTreeAgent(String name) {
        this.setName(name);
    }

    @Override
    public void observe(Set<Observation> o) {
        this.policy = policy.findNextNode(o);
    }

    @Override
    public Action decideGameAction() throws AgentException {
        if (policy == null) this.generateStrategy();
        if (policy == null) throw new AgentException("Could not generate new policy");
        return this.policy.action;
    }

    /**
     * An implementation of:
     *
     *
     * @param games
     * @param beliefMap
     * @param horizon
     */
    @Override
    public void onGenerateStrategy(
            Map<String, Game> games,
            Map<String, Double> beliefMap,
            Integer horizon) {

        // Initialize b0
        Map<Game, Double> belief = new HashMap<Game, Double>();
        for (String gameName : beliefMap.keySet()) {
            belief.put(games.get(gameName), beliefMap.get(gameName));
        }
        this.policy = generatePolicyTreeNode(belief);
    }

    PolicyTreeNode generatePolicyTreeNode(Map<Game, Double> belief) {
        Set<Action> bestActions = new HashSet<Action>();
        Double bestReward = null;

        // Figure out our best move.
        PolicyTreeNode node = new PolicyTreeNode();
        for (Action a : getActions().values()) {
            Double reward = getActionExpectedPayoff(a, belief);
            if (bestReward == null || reward >= bestReward - EPSILON) {
                if (bestReward == null || reward > bestReward + EPSILON) {
                    bestReward = reward;
                    bestActions = new HashSet<Action>();
                }
                bestActions.add(a);
            }
        }



        return node;
    }

    Double getActionExpectedPayoff(Action a, Map<Game, Double> belief) {
        Double expectedPayoff = 0.0;
        for (Game g : belief.keySet()) {
            Double currentBelief = belief.get(g);
            Set<JointAction> actions = g.getJointActionsWithAgentAction(this, a);
        }
        return expectedPayoff;
    }

}
