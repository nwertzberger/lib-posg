package com.ideaheap.libposg.agent;

import com.ideaheap.libposg.state.Action;
import com.ideaheap.libposg.state.Game;
import com.ideaheap.libposg.state.Observation;

import java.util.Map;
import java.util.Set;

/**
 * User: nwertzberger
 * Date: 4/17/13
 * Time: 8:49 PM
 * Email: wertnick@gmail.com
 *
 * This class generates a tree of horizon depth based on the current belief.
 *
 */
public class StrategyTreeAgent extends Agent {

    private Set<Observation> observations = null;

    public StrategyTreeAgent(String name) {
        this.setName(name);
    }

    @Override
    public void observe(Set<Observation> o) {
        observations = o;
    }

    @Override
    public Action decideGameAction() {
        return null;
    }

}
