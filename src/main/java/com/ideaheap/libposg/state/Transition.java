package com.ideaheap.libposg.state;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.ideaheap.libposg.agent.Agent;

import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: nwertzberger
 * Date: 4/11/13
 * Time: 11:41 PM
 *
 */
public class Transition {
    public Transition(
            Game destGame,
            Double probability,
            ImmutableMap<Agent, Map<Observation, Double>> observations) {

    }
}
