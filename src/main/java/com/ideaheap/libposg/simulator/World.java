package com.ideaheap.libposg.simulator;

import com.ideaheap.libposg.agent.Agent;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

/**
 * User: nwertzberger
 * Date: 4/14/13
 * Time: 3:21 PM
 * Email: wertnick@gmail.com
 *
 * A world is a generated world from either a YAML file or a YAML String.
 */
public class World {

    private Map<String, Object> world;
    private Agent agent;

    @SuppressWarnings("unchecked")
    public World(String worldConfig, Agent agent) {
        Yaml yaml = new Yaml();
        Map<String, Object> world = (Map<String, Object>) yaml.load(worldConfig);
        this.agent = agent;
        parseWorld(world);
    }

    @SuppressWarnings("unchecked")
    public World(InputStream worldStream, Agent agent) {
        Yaml yaml = new Yaml();
        Map<String, Object> world = (Map<String, Object>) yaml.load(worldStream);
        this.agent = agent;
        parseWorld(world);
    }

    // Used to build the world into a state space.
    // Also initializes an agent with expected belief data.
    private void parseWorld(Map<String, Object> world) {
        Map<String, Object> agentMap = (Map<String, Object>) world.get("agents");
        Map<String, Object> gameMap = (Map<String, Object>) world.get("games");
    }

}
