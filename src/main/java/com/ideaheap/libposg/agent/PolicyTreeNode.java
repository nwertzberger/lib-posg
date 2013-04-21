package com.ideaheap.libposg.agent;

import com.ideaheap.libposg.state.Action;
import com.ideaheap.libposg.state.Observation;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * User: nwertzberger
 * Date: 4/20/13
 * Time: 12:41 PM
 * Email: wertnick@gmail.com
 *
 * This is simply here to deal with the fact that more than one action can be equivalent...
 */
public class PolicyTreeNode {
    public Set<PolicyTreeTransition> transitions;
    private static final Random rand = new Random();

    /**
     * if there are more than one available action, it means that we have equivalent strategies.
     * Return one randomly.
     * @return
     */
    public PolicyTreeTransition getTransition() {
        int ActionIdx = rand.nextInt(transitions.size());
        Iterator<PolicyTreeTransition> desiredTransition = transitions.iterator();
        for (int i=0; i < ActionIdx; i++) {
            desiredTransition.next();
        }
        return desiredTransition.next();
    }
}
