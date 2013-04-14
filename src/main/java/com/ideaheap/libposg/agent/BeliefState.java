package com.ideaheap.libposg.agent;

import com.ideaheap.libposg.state.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * User: nwertzberger
 * Date: 4/11/13
 * Time: 10:06 PM
 *
 * A Belief State is the set of probabilities that a robot uses to determine
 * what state it believes it is in.
 */
class BeliefState {
    private static final Logger logger = LoggerFactory.getLogger(BeliefState.class);
    private final Map<State, Double> state;

    public BeliefState(Map<State, Double> initialState) {
        this.state = initialState;
    }
}
