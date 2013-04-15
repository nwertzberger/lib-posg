package com.ideaheap.libposg.simulator;

import com.ideaheap.libposg.agent.Agent;
import com.ideaheap.libposg.agent.HumanAgent;

import java.util.HashMap;
import java.util.Map;

/**
 * User: nwertzberger
 * Date: 4/14/13
 * Time: 9:22 PM
 * Email: wertnick@gmail.com
 *
 * Helper methods for creating and interacting with agents for a simulator.
 *
 */
public class Agents {
    public static Map<String, Agent> fromConfig(Map<String, Object> agentConfigurations) {
        Map<String, Agent> agents = new HashMap<String, Agent>();
        for(String agentName : agentConfigurations.keySet()) {
            Agent agent;
            if (agentName.endsWith("Human"))
                agent = new HumanAgent(agentName);
            else
                agent = new HumanAgent(agentName);

            agents.put(agentName, agent);
        }
        return agents;
    }
}
