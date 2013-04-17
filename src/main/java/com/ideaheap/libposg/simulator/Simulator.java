package com.ideaheap.libposg.simulator;

import com.ideaheap.libposg.agent.Agent;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
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
    private World world;

    public Simulator(String worldConfig) {
        Yaml yaml = new Yaml();
        parseWorld((Map<String, Object>) yaml.load(worldConfig));
    }

    public Simulator(InputStream worldStream) {
        Yaml yaml = new Yaml();
        parseWorld((Map<String, Object>) yaml.load(worldStream));
    }

    // Used to build the world into a state space.
    // Also initializes an agent with expected belief data.
    private void parseWorld(Map<String, Object> world) {
        Map<String, Object> agentConfigs = (Map<String, Object>) world.get("agents");
        Map<String, Object> gameConfigs = (Map<String, Object>) world.get("games");

        this.world = Worlds.fromConfig(
                gameConfigs,
                Agents.fromConfig(agentConfigs)
        );
    }

    private void simulate() throws IOException {
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        do {
            world.step();
            System.out.print(" > ");
            line = reader.readLine();
        } while(line != null && !line.contains("quit"));
        reader.close();
    }

    @Override
    public String toString() {
        return "{world:" + world + "}";
    }

    public static void main(String [] argv) {
        Simulator s = new Simulator(Simulator.class
                .getClassLoader()
                .getResourceAsStream("domain.yaml")
        );
        System.out.println("Simulator:" + s);
        try {
            s.simulate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
