package com.ideaheap.libposg.agent

import com.ideaheap.libposg.state.*
import com.ideaheap.libposg.strategy.PomdpStrategy
import org.junit.Before
import org.junit.Test

import static junit.framework.Assert.assertEquals

/**
 *  User: nwertzberger
 *  Date: 4/20/13
 *  Time: 10:18 AM
 * Email: wertnick@gmail.com
 *
 * It looks like I forgot to explain this class.
 */
class StrategyTreeAgentTest {

    PomdpStrategy agent;

    @Before
    void setUp() {
        agent = new PomdpStrategy("agent")
        agent.addAction(new Action("JUMP"))
        agent.addAction(new Action("SIT"))
    }

    @Test
    public void onlyOneGameChoice() {
        Game game = new Game("G1");
        game.addJointAction(generateSingleAgentJointAction("JUMP", 1.0d, game));
        agent.onGenerateStrategy([G1: game], [G1: 1.0], 1)
        assert agent.decideGameAction() == agent.getAction("JUMP")
    }

    @Test
    public void onlyOneOtherGameChoice() {
        Game game = new Game("G1");
        game.addJointAction(generateSingleAgentJointAction("SIT", 1.0d, game));
        agent.onGenerateStrategy([G1: game], [G1: 1.0], 1)
        assert agent.decideGameAction() == agent.getAction("SIT")
    }

    JointAction generateSingleAgentJointAction(String action, Double payoff, Game targetGame) {
        Transition t = new Transition(targetGame, new HashMap<Agent, Map<Observation, Double>>())
        def agentAction = [:]
        agentAction[agent] = agent.getAction(action)
        def agentPayoff = [:]
        agentPayoff[agent] = payoff
        def transitions = [:]
        transitions[t] = 1.0d

        return new JointAction(
                agentAction as Map<Agent, Action>,
                agentPayoff as Map<Agent, Double>,
                transitions as Map<Transition, Double>
        )
    }

    @Test
    void canNormalizeMap() {
        def stuff = [:]
        stuff[new Game("G1")] = 0.2d
        stuff[new Game("G2")] = 0.3d
        agent.normalizeBelief(stuff)
        assertEquals(stuff.inject(0d) { acc, k, v -> acc + v } as Double, 1.0d, Double.MIN_VALUE)
    }
}
