package com.ideaheap.libposg.agent;

import com.ideaheap.libposg.state.Observation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * User: nwertzberger
 * Date: 4/20/13
 * Time: 5:47 PM
 * Email: wertnick@gmail.com
 * <p/>
 * It looks like I forgot to explain this class.
 */
public class PolicyTreeTransition {
    Map<Set<Observation>, PolicyTreeNode> observations = new HashMap<Set<Observation>, PolicyTreeNode>();
    public Double expectedValue;

    public void addTransition(Set<Observation> obs, PolicyTreeNode dest) {
        observations.put(obs, dest);
    }

    public PolicyTreeNode findTransition(Set<Observation> o) {
        return observations.get(o);
    }
}
