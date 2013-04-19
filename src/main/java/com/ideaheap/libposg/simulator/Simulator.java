package com.ideaheap.libposg.simulator;

import com.ideaheap.libposg.agent.Agent;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

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
        String startingGame = (String) world.get("startingGame");

        Map<String, Agent> agents = Agents.fromConfig(agentConfigs);
        this.world = Worlds.fromConfig(
                gameConfigs, agents
        ).withStartingGame(startingGame);

        for (Agent agent : agents.values()) {
            agent.setGames(this.world.getGames());
        }
    }

    private void simulate() {
        String line;
        do {
            world.step();
            System.out.println("(actual state = " + world.getCurrentGame().getName() + ")");
            Scanner scan = new Scanner(System.in);
            System.out.print(" > ");
            line = scan.nextLine();
        } while(line != null && !line.contains("quit"));
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
        s.simulate();
    }
}
