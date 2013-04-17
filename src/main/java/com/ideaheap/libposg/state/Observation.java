package com.ideaheap.libposg.state;

import java.util.concurrent.Callable;

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
    private Callable<Boolean> process = null;

    public Observation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Observation withProcess(Callable<Boolean> process) {
        this.process = process;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Observation that = (Observation) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "{" + name + "}";
    }

}
