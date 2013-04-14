package com.ideaheap.libposg.state;

import java.util.HashMap;
import java.util.Map;

/**
 * User: nwertzberger
 * Date: 4/11/13
 * Time: 10:18 PM
 */
public class State {
    private Map<Transition, Double> state;

    public State(Map<Transition, Double> transitions) {
        this.state = state;
    }
    public State() {
        this.state = new HashMap<Transition, Double>();
    }
}
