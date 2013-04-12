package com.ideaheap.libposg.state;

import java.util.HashMap;
import java.util.Map;

/**
 * User: nwertzberger
 * Date: 4/11/13
 * Time: 10:18 PM
 */
public class StateNode {
    private Map<StateNode, Double> state;

    public StateNode(Map<StateNode, Double> state) {
        this.state = state;
    }
    public StateNode() {
        this.state = new HashMap<StateNode, Double>();
    }


}
