package com.ideaheap.libposg.agent;

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
        return this.policy.getAction();
    }

    /**
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
        this.policy = generatePolicyTreeNode(belief, horizon);
    }

    /**
     * Depth (horizon) limited search of actions and observations to generate
     * Expected values for different sets of belief.
     *
     * s = the current game
     * a = the action
     * o = the observation
     * P(,) = probability
     * R(,) = reward
     *
     * 0: if horizon = 0, return null
     * 1: Normalize incoming belief vector
     * 2: Find best action available. For all actions:
     *      2.1. For all games:
     *          a. for all observations
     *              i. Update belief for this.
     *      2.2. Normalize all action - observation combo beliefs.
     *      2.3. For all games:
     *          a. Calculate expected value of this action given our belief vector. (b(s) * R(s,a)
     *          b. Calculate the new belief vector based on this action. (b^a)
     *          c. Go through every possible observation
     *               i. calculate the probability of this observation (P(o|s,a)). HOLD ON TO THIS
     *               ii. calculate a new belief vector based on this observation.
     *               iii. call this function at step 0, using our new belief vector and horizon - 1.
     *          d. Test to see if action is worth saving. If not, trash it.
     *
     *
     * @param belief
     * @param horizon
     * @return
     */
    PolicyTreeNode generatePolicyTreeNode(Map<Game, Double> belief, int horizon) {
        if (horizon == 0) return null; // Task 0: early quit
        normalizeBelief(belief); // Task 1: normalize

        // Task 2: find the best actions
        PolicyTreeNode node = new PolicyTreeNode();
        Set<PolicyTreeTransition> bestAction = new HashSet<PolicyTreeTransition>();

        // For all actions:
        for (Action a : this.getActions().values()) {

            // Store b(a,o).  a is constant, so this will be implicit.
            Map<Observation, Map<Game, Double>> observationBeliefs = generateActionObservationBeliefs(belief, a);

            // Task 2.2: Normalize discovered beliefs
            for (Map<Game, Double> b: observationBeliefs.values()) {
                normalizeBelief(b);
            }

            // Task 2.3: Maximize and recurse.
            for (Game g : belief.keySet()) {
                Double currBelief = belief.get(g);

                // Calculate action value given ourbelief state.
                JointAction jointAction = g.getBestResponseJointAction(this, a);
                Double currReward = jointAction.getAgentRewards().get(this);

                // Go through every possible Observation
                // Due to the way we organize transitions, the probabilities are currently set up as:
                // P(s'|a,s), P(o|s',a,s)
                for (Transition t : jointAction.getTransitions().keySet()) {
                    Double transitionProbability = jointAction.getTransitions().get(t); // P(s'|a,s)
                    // generate a new belief state based on our actions and observations up to here.
                    Map<Game, Double> newBelief = new HashMap<Game, Double>(belief);

                }
            }
        }

        return node;
    }

    private Map<Observation, Map<Game, Double>> generateActionObservationBeliefs(Map<Game, Double> belief, Action a, Map<Observation) {
        Map<Observation, Map<Game, Double>> observationBeliefs = new HashMap<Observation, Map<Game, Double>>();
        // Task 2.1: find the correct beliefs moving forward.
        // We already know the action
        for (Game currGame : belief.keySet()) {
            Double currBelief = belief.get(currGame);
            JointAction jointAction = currGame.getBestResponseJointAction(this, a);

            // Transitions map destination games to actions... We need the reverse.
            for (Transition t : jointAction.getTransitions().keySet()) {

                Double actionTransitionProbability = jointAction.getTransitions().get(t); // P(s'|a,s)
                Game destGame = t.getDestGame();

                // b(a,o) = SUM_{s \in S} b * P(s',s|a,o)
                for (Observation o: t.getAgentObservationProbabilities(this).keySet()) {
                    Double observationProbability = t.getAgentObservationProbabilities(this).get(o);
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
        for (Game g : belief.keySet()) belief.put(g,belief.get(g) / total);
    }
}
