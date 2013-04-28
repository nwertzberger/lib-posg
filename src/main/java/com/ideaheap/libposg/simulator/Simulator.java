package com.ideaheap.libposg.simulator;

import com.ideaheap.libposg.agent.Agent;
import com.ideaheap.libposg.agent.AgentException;
import com.ideaheap.libposg.state.*;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.*;

/**
 * User: nwertzberger
 * Date: 4/14/13
 * Time: 3:21 PM
 * Email: wertnick@gmail.com
 * <p/>
 * A simulator is a generated world from either a YAML file or a YAML String.
 */
@SuppressWarnings("unchecked")
public class Simulator {
    private World world = null;
    private Map<Agent, Double> actualReward = new HashMap<Agent, Double>();
    private Map<String, Agent> agents;
    private Game currentGame;

    /**
     * Simulators shouldn't be parsing YAML...
     *
     * @param worldConfig
     */
    public Simulator(String worldConfig) {
        Yaml yaml = new Yaml();
        parseWorld((Map<String, Object>) yaml.load(worldConfig));
    }

    public Simulator(InputStream worldStream) {
        Yaml yaml = new Yaml();
        parseWorld((Map<String, Object>) yaml.load(worldStream));
    }

    /**
     * This needs to be pulled out into a SimulatorBuilder or something.
     *
     * @param worldConfig
     */
    // Used to build the world into a state space.
    // Also initializes an agent with expected belief data.
    private void parseWorld(Map<String, Object> worldConfig) {
        Map<String, Object> agentConfigs = (Map<String, Object>) worldConfig.get("agents");
        Map<String, Object> gameConfigs = (Map<String, Object>) worldConfig.get("games");
        String startingGame = (String) worldConfig.get("startingGame");

        agents = Agents.fromConfig(agentConfigs);

        this.world = Worlds.fromConfig(gameConfigs, agents);
        currentGame = this.world.getGame(startingGame);

        for (Agent agent : agents.values()) {
            agent.setWorld(this.world);
        }
    }

    /**
     * The actual simulation step.
     *
     * @throws AgentException
     */
    public void step() throws AgentException {

        // Calculate the joint action
        Map<Agent, Action> agentActions = new HashMap<Agent, Action>();
        for (Agent agent : agents.values()) {
            agentActions.put(agent, agent.decideGameAction());
        }
        JointAction jointAction = currentGame.getJointAction(agentActions);

        // Determine reward
        Map<Agent, Double> rewards = jointAction.getAgentRewards();
        for (Agent agent : rewards.keySet()) {
            actualReward.put(agent, rewards.get(agent));
        }

        // Determine the transition
        Transition t = jointAction.determineTransition();
        for (Agent a : agents.values()) {
            Map<Observation, Double> observationProbabilities = t.getAgentObservationProbabilities(a);
            Set<Observation> observations = new HashSet<Observation>();
            for (Observation ob : observationProbabilities.keySet()) {
                if (Math.random() < observationProbabilities.get(ob)) {
                    observations.add(ob);
                }
            }
            a.observe(observations);
        }
        this.currentGame = t.getDestGame();
    }

    private void simulate() throws AgentException {
        String line;
        do {
            step();
            System.out.println("(actual state = " + currentGame.getName() + ")");
            Scanner scan = new Scanner(System.in);
            System.out.print(" > ");
            line = scan.nextLine();
        } while (line != null && !line.contains("quit"));
    }


    public static void main(String[] argv) {
        Simulator s = new Simulator(Simulator.class
                .getClassLoader()
                .getResourceAsStream("domain.yaml")
        );
        System.out.println("Simulator:" + s);
        try {
            s.simulate();
        } catch (AgentException e) {
            e.printStackTrace();
        }
    }

    /* CRUFT */

    @Override
    public String toString() {
        return "{world:" + world + "}";
    }
}
