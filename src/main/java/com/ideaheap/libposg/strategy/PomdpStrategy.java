package com.ideaheap.libposg.strategy;

import com.ideaheap.libposg.agent.Agent;
import com.ideaheap.libposg.agent.PolicyTreeNode;
import com.ideaheap.libposg.state.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User: nwertzberger
 * Date: 4/17/13
 * Time: 8:49 PM
 * Email: wertnick@gmail.com
 * <p/>
 * This class generates a tree of horizon depth based on the current belief.
 */
public class PomdpStrategy implements Strategy {
    private Agent agent = null;

    /**
     * @param games
     * @param beliefMap
     * @param horizon
     */
    @Override
    public PolicyTreeNode generateStrategy(World w, Agent me, Map<Game, Double> belief, int horizon) {
        agent = me;
        PolicyTreeNode node = generatePolicyTreeNode(belief, horizon);
        System.out.println(node);
        return node;

    }

    /**
     * Depth (horizon) limited search of actions and observations to generate
     * Expected values for different sets of belief.
     * <p/>
     * s = the current game
     * a = the action
     * o = the observation
     * P(,) = probability
     * R(,) = reward
     * <p/>
     * 0: if horizon = 0, return null
     * 1: Normalize incoming belief vector
     * 2: Find best action available. For all actions:
     * 2.1. For all games:
     * a. for all observations
     * i. Update belief for this.
     * 2.2. Normalize all action - observation combo beliefs.
     * 2.3. For all games:
     * a. Calculate expected value of this action given our belief vector. (b(s) * R(s,a)
     * b. Calculate the new belief vector based on this action. (b^a)
     * c. Go through every possible observation
     * i. calculate the probability of this observation (P(o|s,a)). HOLD ON TO THIS
     * ii. calculate a new belief vector based on this observation.
     * iii. call this function at step 0, using our new belief vector and horizon - 1.
     * d. Test to see if action is worth saving. If not, trash it.
     *
     * @param belief
     * @param horizon
     * @return
     */
    PolicyTreeNode generatePolicyTreeNode(Map<Game, Double> belief, int horizon) {
        if (horizon == 0) return null; // Task 0: early quit
        normalizeBelief(belief); // Task 1: normalize

        // Task 2: find the best actions
        Double bestValue = null;
        PolicyTreeNode bestNode = null;

        for (Action a : agent.getActions().values()) {

            // Store b(a,o).  a is constant, so this will be implicit.
            Map<Observation, Map<Game, Double>> observationBeliefs = generateActionObservationBeliefs(belief, a);

            // Task 2.2: Normalize discovered beliefs
            for (Map<Game, Double> b : observationBeliefs.values()) {
                normalizeBelief(b);
            }

            // Task 2.3: Calculate expected value
            Double expectedValue = 0.0;
            for (Game g : belief.keySet()) {
                Double currBelief = belief.get(g);

                // Calculate action value given ourbelief state.
                JointAction jointAction = g.getJointActionsWithAgentAction(agent, a).iterator().next();
                Double currReward = jointAction.getAgentRewards().get(agent);

                expectedValue += currBelief * currReward;

                // Go through every possible Observation
                // Due to the way we organize transitions, the probabilities are currently set up as:
                // At this time, we have chosen the game and the action.
                // We need to take each observation's probability of existing given the current action...
                // This should be P(o|s,a) = \alpha \SUM_{t \in T} P(o|t) * P(t|s,a)
                // P(s'|a,s), P(o|s',a,s)

                Map<Observation, Double> observationProb = new HashMap<Observation, Double>();
                for (Transition t : jointAction.getTransitions().keySet()) {
                    // P(s'|a,s)
                    Double transitionProbability = jointAction.getTransitions().get(t); // P(s'|a,s)
                    for (Observation o : t.getAgentObservations(agent).keySet()) {
                        // P(o|s',a,s)
                        Double observationProbability = t.getAgentObservations(agent).get(o);
                        if (!observationProb.containsKey(o)) observationProb.put(o, 0.0);
                        observationProb.put(o, observationProb.get(o) + transitionProbability * observationProbability);
                    }
                }

                // Now go through and calculate the value
                Map<Set<Observation>, PolicyTreeNode> transitions = new HashMap<Set<Observation>, PolicyTreeNode>();
                for (Observation o : observationProb.keySet()) {
                    Set<Observation> observations = new HashSet<Observation>();
                    observations.add(o);
                    PolicyTreeNode newNode = generatePolicyTreeNode(observationBeliefs.get(o), horizon - 1);
                    transitions.put(observations, newNode);
                    expectedValue += newNode == null ? 0 : newNode.getExpectedValue();
                }

                // MAXIMIZE
                if (bestValue == null || expectedValue > bestValue) {
                    bestNode = new PolicyTreeNode(a, expectedValue, belief).withTransitions(transitions);
                    bestValue = expectedValue;
                }
            }
        }

        return bestNode;
    }

    private Map<Observation, Map<Game, Double>> generateActionObservationBeliefs(Map<Game, Double> belief, Action a) {
        Map<Observation, Map<Game, Double>> observationBeliefs = new HashMap<Observation, Map<Game, Double>>();
        // Task 2.1: find the correct beliefs moving forward.
        // We already know the action
        for (Game currGame : belief.keySet()) {
            Double currBelief = belief.get(currGame);
            JointAction jointAction = currGame.getJointActionsWithAgentAction(agent, a).iterator().next();

            // Transitions map destination games to actions... We need the reverse.
            for (Transition t : jointAction.getTransitions().keySet()) {

                Double actionTransitionProbability = jointAction.getTransitions().get(t); // P(s'|a,s)
                Game destGame = t.getDestGame();

                // b(a,o) = SUM_{s \in S} b * P(s',s|a,o)
                for (Observation o : t.getAgentObservationProbabilities(agent).keySet()) {
                    Double observationProbability = t.getAgentObservationProbabilities(agent).get(o);
                    // First time
                    if (!observationBeliefs.containsKey(o)) {
                        observationBeliefs.put(o, new HashMap<Game, Double>());
                    }
                    if (!observationBeliefs.get(o).containsKey(destGame)) {
                        observationBeliefs.get(o).put(destGame, 0d);
                    }

                    Double accBelief = observationBeliefs.get(o).get(destGame);
                    Double nextBelief = currBelief * actionTransitionProbability * observationProbability;
                    observationBeliefs.get(o).put(destGame, accBelief + nextBelief);
                }
            }
        }
        return observationBeliefs;
    }

    public void normalizeBelief(Map<Game, Double> belief) {
        Double total = 0.0;
        for (Double bi : belief.values()) total += bi;
        for (Game g : belief.keySet()) belief.put(g, belief.get(g) / total);
    }

}
