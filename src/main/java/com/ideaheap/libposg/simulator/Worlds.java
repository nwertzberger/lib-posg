package com.ideaheap.libposg.simulator;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.ideaheap.libposg.agent.Agent;
import com.ideaheap.libposg.state.*;

import java.util.*;

/**
 * User: nwertzberger
 * Date: 4/15/13
 * Time: 7:57 PM
 * Email: wertnick@gmail.com
 *
 * A helper class for building worlds from config files.
 */
@SuppressWarnings("unchecked")
public class Worlds {

    /**
     * Generate all required game configurations from a Map of Maps. See "domain.yaml" for an example layout.
     */
    public static World fromConfig(Map<String, Object> gameConfigurations, Map<String, Agent> agents) {
        Map<String, Game> games = new HashMap<String, Game>();
        for(String game: gameConfigurations.keySet()) {
            games.put(game, new Game(game));
        }
        // Get all the games
        for(String gameName: gameConfigurations.keySet()) {

            // Take each game's joint actions.
            Game game = games.get(gameName);
            List<Map<String, Object>> jointActions = (List<Map<String, Object>>) gameConfigurations.get(gameName);
            for (Map<String, Object> jointAction : jointActions) {
                addJointAction(game, jointAction, agents, games);
            }
        }
        return new World().withGames(games).withAgents(agents.values());
    }

    /**
     * Expected format of a jointAction object:
     *  jointAction: String (Agent) -> String (Action)
     *  reward: String (Agent) -> Double (Reward)
     *  destStates:
     *      stateName :
     *         p (probability)
     *         observations:
     *            agentName: String (Observation) -> Double (Probability),
     */
    private static void addJointAction(Game game, Map<String, Object> config, Map<String, Agent> agents, Map<String, Game> games) {
        Map<Agent, Action> parsedJointAction = new HashMap<Agent, Action>();
        Map<Agent, Double> parsedRewards    = new HashMap<Agent, Double>();
        Map<Transition, Double> parsedTransitions   = new HashMap<Transition, Double>();

        // Build the new joint action.
        Map<String, String> jointAction = (Map<String, String>) config.get("jointAction");
        for (String agentName: jointAction.keySet()) {
            String actionName = jointAction.get(agentName);
            parsedJointAction.put(agents.get(agentName), agents.get(agentName).getAction(actionName));
        }

        // Get the rewards:
        Map<String, Double> rewards = (Map<String, Double>) config.get("rewards");
        for (String agentName: rewards.keySet()) {
            Double reward = rewards.get(agentName);
            parsedRewards.put(agents.get(agentName), reward);
        }

        // Generate the transitions.
        Map<String, Object> transitions = (Map<String, Object>) config.get("transitions");
        for (String destGame : transitions.keySet()) {
            Map<String, Object> transition = (Map<String, Object>) transitions.get(destGame);
            Double probability = (Double) transition.get("p");
            Map<String, Object> agentObservations = (Map<String, Object>) transition.get("observations");
            Map<Agent, Map<Observation, Double>> observations = getObservations(
                    agentObservations,
                    agents);
            parsedTransitions.put(
                    new Transition(
                            games.get(destGame),
                            ImmutableMap.copyOf(observations)
                    ),
                    probability
            );
        }

        game.addJointAction(new JointAction(
                ImmutableMap.copyOf(parsedJointAction),
                ImmutableMap.copyOf(parsedRewards),
                ImmutableMap.copyOf(parsedTransitions))
        );

    }

    /**
     * Grab all agent related observations and add them.
     */
    private static Map<Agent, Map<Observation, Double>> getObservations(
            Map<String, Object> agentsWithObservations,
            Map<String, Agent> agents) {
        Map<Agent, Map<Observation, Double>> parsedAgentObservations = new HashMap<Agent, Map<Observation,Double>>();

        // Go through each agent
        for (String agentName : agentsWithObservations.keySet()) {
            Map<String, Double> observations = (Map<String, Double>) agentsWithObservations.get(agentName);
            Map<Observation, Double> parsedObservationProbabilities = new HashMap<Observation, Double>();

            // Find all the observations
            Agent agent = agents.get(agentName);
            for(String observationName: observations.keySet()) {
                parsedObservationProbabilities.put(
                        agent.getObservation(observationName),
                        observations.get(observationName)
                );
            }
            parsedAgentObservations.put(agent, parsedObservationProbabilities);
        }
        return parsedAgentObservations;
    }
}
