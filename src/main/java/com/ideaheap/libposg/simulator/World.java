package com.ideaheap.libposg.simulator;

import com.google.common.collect.ImmutableMap;
import com.ideaheap.libposg.agent.Agent;
import com.ideaheap.libposg.state.*;

import java.util.*;

/**
 * User: nwertzberger
 * Date: 4/14/13
 * Time: 9:32 PM
 * Email: wertnick@gmail.com
 *
 * A simulated world based on a configured set of games and agents.
 *
 */
public class World {

    private Set<Agent> agents = new HashSet<Agent>();
    private Map<String, Game> games = new HashMap<String, Game>();
    private Game currentGame = null;

    /*
     *  Statistical stuff
     */
    private Map<Agent,Double> actualReward = new HashMap<Agent, Double>();

    public void addAgents(Collection<Agent> agents) {
        this.agents.addAll(agents);
    }

    public void step() {
        // Calculate the joint action
        Map<Agent, Action> agentActions = new HashMap<Agent, Action>();
        for (Agent agent : agents) {
            agentActions.put(agent, agent.decideGameAction());
        }
        JointAction jointAction = currentGame.getJointAction(agentActions);

        // Determine reward
        Map<Agent, Double> rewards = jointAction.getAgentRewards();
        for (Agent agent: rewards.keySet()) {
            actualReward.put(agent, rewards.get(agent));
        }

        // Determine the transition
        Transition t = jointAction.determineTransition();
        for (Agent a: agents) {
            Map<Observation,Double> observationProbabilities = t.getAgentObservations(a);
            Set<Observation> observations = new HashSet<Observation>();
            for (Observation ob: observationProbabilities.keySet()) {
                if (Math.random() < observationProbabilities.get(ob)) {
                    observations.add(ob);
                }
            }
            a.observe(observations);
        }
        this.currentGame = t.getGame();
    }

    public Map<String,Game> getGames() {
        return games;
    }

    public World withGames(Map<String, Game> games) {
        this.games = games;
        return this;
    }

    public World withAgents(Collection<Agent> agents) {
        addAgents(agents);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        World world = (World) o;

        if (agents != null ? !agents.equals(world.agents) : world.agents != null) return false;
        if (games != null ? !games.equals(world.games) : world.games != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = agents != null ? agents.hashCode() : 0;
        result = 31 * result + (games != null ? games.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append("agents:" + agents);
        buffer.append(", games:" + games);
        buffer.append("}");
        return buffer.toString();
    }

    public World withStartingGame(String startingGame) {
        this.currentGame = games.get(startingGame);
        return this;
    }

    public Game getCurrentGame() {
        return currentGame;
    }
}
