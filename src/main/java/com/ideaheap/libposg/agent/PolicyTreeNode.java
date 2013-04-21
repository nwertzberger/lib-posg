package com.ideaheap.libposg.agent;

import com.ideaheap.libposg.state.Action;
import com.ideaheap.libposg.state.Observation;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * User: nwertzberger
 * Date: 4/20/13
 * Time: 12:41 PM
 * Email: wertnick@gmail.com
 *
 * I'm sorry for this abomination...
 */
public class PolicyTreeNode {
    public Map<Action, PolicyTreeTransition> transitions;
    public Action activeAction;

    public PolicyTreeNode findNextNode(Set<Observation> o) {
        return transitions.get(activeAction).findTransition(o);
    }

    /**
     * if there are more than one available action, it means that we have equivalent strategies.
     * Return one randomly.
     * @return
     */
    public Action getAction() {
        int ActionIdx = (int) (Math.random() * transitions.keySet().size());
        Iterator<Action> desiredAction = transitions.keySet().iterator();
        for (int i=0; i < ActionIdx; i++) {
            desiredAction.next();
        }
        activeAction = desiredAction.next();
        return activeAction;
    }
}
