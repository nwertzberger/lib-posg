package com.ideaheap.libposg.simulator;

import com.ideaheap.libposg.agent.Agent;
import com.ideaheap.libposg.state.Action;
import com.ideaheap.libposg.state.Observation;
import com.ideaheap.libposg.strategy.HumanStrategy;
import com.ideaheap.libposg.strategy.PomdpStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: nwertzberger
 * Date: 4/14/13
 * Time: 9:22 PM
 * Email: wertnick@gmail.com
 * <p/>
 * Helper methods for creating and interacting with agents for a simulator.
 */
@SuppressWarnings("unchecked")
public class Agents {
    public static Map<String, Agent> fromConfig(Map<String, Object> agentConfigurations) {
        Map<String, Agent> agents = new HashMap<String, Agent>();
        for (String agentName : agentConfigurations.keySet()) {
            Map<String, Object> agentConf = (Map<String, Object>) agentConfigurations.get(agentName);
            agents.put(agentName, createAgentFromConfig(agentName, agentConf));
        }
        return agents;
    }

    private static Agent createAgentFromConfig(String agentName, Map<String, Object> agentConf) {
        Agent agent;
        if (agentName.endsWith("Hum"))
            agent = new Agent(agentName, new HumanStrategy());
        else
            agent = new Agent(agentName, new PomdpStrategy());

        for (String action : (List<String>) agentConf.get("actions")) {
            agent.addAction(new Action(action));
        }

        for (String observation : (List<String>) agentConf.get("observations")) {
            agent.addObservation(new Observation(observation));
        }

        agent.setHorizon((Integer) agentConf.get("horizon"));

        Map<String, Double> belief = (Map<String, Double>) agentConf.get("belief");
        agent.setBeliefHack(belief);

        return agent;
    }
}
