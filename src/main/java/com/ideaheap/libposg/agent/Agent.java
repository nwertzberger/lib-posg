package com.ideaheap.libposg.agent;

import com.ideaheap.libposg.state.Action;
import com.ideaheap.libposg.state.Observation;

/**
 * User: nwertzberger
 * Date: 4/14/13
 * Time: 3:33 PM
 * Email: wertnick@gmail.com
 *
 * The agent is the interface that must be implemented for using the simulator.
 */
public interface Agent {
    public String getName();

    public Action getAction(String actionName);
    public Observation getObservation(String observationName);
}
