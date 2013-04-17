package com.ideaheap.libposg.state;

/**
 * User: nwertzberger
 * Date: 4/11/13
 * Time: 11:26 PM
 *
 * The action type is used to elicit changes in the environment.
 *
 */
public class Action {
    private Runnable execution = null;
    private String name;

    public Action(String name) {
        this.name = name;
    }

    public Action withExecution(Runnable exec) {
        this.execution = exec;
        return this;
    }

    public void act() {
        execution.run();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Action action = (Action) o;

        if (execution != null ? !execution.equals(action.execution) : action.execution != null) return false;
        if (name != null ? !name.equals(action.name) : action.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = execution != null ? execution.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{" +name + (execution != null ? "(Exec)" : "") + "}";
    }
}
