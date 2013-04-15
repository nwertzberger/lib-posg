package com.ideaheap.libposg.simulator;

import com.ideaheap.libposg.agent.Agent;

import java.util.Collection;
import java.util.Map;

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

    public static World fromConfig(Map<String, Object> gameConfigurations) {
        return new World();
    }

    public void addAgents(Collection<Agent> agents) {

    }
}
