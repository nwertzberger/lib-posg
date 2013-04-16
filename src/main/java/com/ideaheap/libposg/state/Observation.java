package com.ideaheap.libposg.state;

/**
 * User: nwertzberger
 * Date: 4/11/13
 * Time: 11:02 PM
 *
 * An Observation is related to states and actions.
 *
 * "If you are in this state and doing this action, you will see X with this
 * probability."
 */
public class Observation {
    private String name;

    public Observation(String name) {
        this.name = name;
    }
}
