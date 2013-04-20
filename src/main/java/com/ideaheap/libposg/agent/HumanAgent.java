package com.ideaheap.libposg.agent;

import com.ideaheap.libposg.state.Action;
import com.ideaheap.libposg.state.Game;
import com.ideaheap.libposg.state.Observation;

import java.util.*;

/**
 * User: nwertzberger
 * Date: 4/14/13
 * Time: 9:25 PM
 * Email: wertnick@gmail.com
 *
 */
public class HumanAgent extends Agent {

    public HumanAgent(String name) {
        this.setName(name);
    }

    @Override
    public void observe(Set<Observation> observations) {
        for (Observation ob : observations) {
            System.out.println("You observed " + ob + "!");
        }
    }

    @Override
    public Action decideGameAction() {
        Action chosenAction = null;
        Scanner scan = new Scanner(System.in);
        while (chosenAction == null) {
            System.out.print("Action? " + getActions().keySet() + " > ");
            chosenAction = getActions().get(scan.nextLine().trim());
        }
        return chosenAction;
    }

    @Override
    public void onGenerateStrategy(Map<String, Game> games, Map<String, Double> belief, Integer horizon) {
        System.out.println("Please generate your strategy");
    }

}
