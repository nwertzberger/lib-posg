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
 */
public class PolicyTreeNode {
    public Map<Set<Observation>, PolicyTreeNode> children;
    public Set<Action> actions;

    public PolicyTreeNode findNextNode(Set<Observation> o) {
        if (children.containsKey(o)) {
            return children.get(o);
        }
        else return null;
    }

    /**
     * if there are more than one available action, it means that we have equivalent strategies.
     * Return one randomly.
     * @return
     */
    public Action getAction() {
        int ActionIdx = (int) (Math.random() * actions.size());
        Iterator<Action> desiredAction = actions.iterator();
        for (int i=0; i < ActionIdx; i++) {
            desiredAction.next();
        }
        return desiredAction.next();
    }
}
