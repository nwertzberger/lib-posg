package com.ideaheap.libposg.agent;

import com.ideaheap.libposg.state.Action;
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
            System.out.print("Action? " + actions.keySet() + " > ");
            chosenAction = actions.get(scan.nextLine().trim());
        }
        return chosenAction;
    }
}
