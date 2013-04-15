package com.ideaheap.libposg.simulator;

import com.ideaheap.libposg.agent.Agent;
import org.yaml.snakeyaml.Yaml;
import sun.plugin2.message.GetAppletMessage;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * User: nwertzberger
 * Date: 4/14/13
 * Time: 3:21 PM
 * Email: wertnick@gmail.com
 *
 * A world is a generated world from either a YAML file or a YAML String.
 */
@SuppressWarnings("unchecked")
public class Simulator {

    private Map<String, Agent> activeAgents;
    private World world;

    public Simulator(String worldConfig) {
        Yaml yaml = new Yaml();
        Map<String, Object> world = (Map<String, Object>) yaml.load(worldConfig);
        parseWorld(world);
    }

    public Simulator(InputStream worldStream) {
        Yaml yaml = new Yaml();
        Map<String, Object> world = (Map<String, Object>) yaml.load(worldStream);
        parseWorld(world);
    }

    // Used to build the world into a state space.
    // Also initializes an agent with expected belief data.
    private void parseWorld(Map<String, Object> world) {
        Map<String, Object> agentConfigs = (Map<String, Object>) world.get("agents");
        Map<String, Object> gameConfigs = (Map<String, Object>) world.get("games");

        this.world = World.fromConfig(gameConfigs);
        this.world.addAgents(Agents
            .fromConfig(agentConfigs)
            .values()
        );
    }

    public static void main(String [] argv) {
        Simulator s = new Simulator(Simulator.class
            .getClassLoader()
            .getResourceAsStream("domain.yaml")
        );
        s.simulate();
    }

    private void simulate() {
        System.out.println("TODO");
    }
}
