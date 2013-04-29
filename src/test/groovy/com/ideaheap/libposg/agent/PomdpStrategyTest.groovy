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
class PomdpStrategyTest {

    Agent agent
    PomdpStrategy strategy

    @Before
    void setUp() {
        strategy = new PomdpStrategy()
        agent = new Agent("agent", strategy)
        agent.addAction(new Action("JUMP"))
        agent.addAction(new Action("SIT"))
    }

    @Test
    public void onlyOneGameChoice() {
        Game g = new Game("G1")
        attachJointActions(g, generateSingleAgentJointAction("JUMP", 1.0d, g))
        def belief = [:]
        belief[g] = 1.0d
        agent.setBelief(belief)
        agent.setHorizon(1)
        agent.setWorld(new World().withAgents([agent]).withGames(["G1": g]))
        assert agent.decideGameAction() == agent.getAction("JUMP")
    }

    @Test
    public void onlyOneOtherGameChoice() {
        Game g = new Game("G1")
        attachJointActions(g, generateSingleAgentJointAction("SIT", 1.0d, g))
        def belief = [:]
        belief[g] = 1.0d
        agent.setBelief(belief)
        agent.setHorizon(1)
        agent.setWorld(new World().withAgents([agent]).withGames(["G1": g]))
        assert agent.decideGameAction() == agent.getAction("SIT")
    }

    @Test
    void canNormalizeMap() {
        def stuff = [:]
        stuff[new Game("G1")] = 0.2d
        stuff[new Game("G2")] = 0.3d
        strategy.normalizeBelief(stuff)
        assertEquals(stuff.inject(0d) { acc, k, v -> acc + v } as Double, 1.0d, Double.MIN_VALUE)
    }

    JointAction generateSingleAgentJointAction(String action, Double payoff, Game targetGame) {
        def observations = [:]
        observations[agent] = [:]
        Transition t = new Transition(targetGame, observations)
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

    static Game attachJointActions(Game g, JointAction... jointActions) {
        def gameActs = [:]
        for (JointAction act : jointActions)
            gameActs[act.getAgentActions()] = act
        g.setJointActions(gameActs)
        return g;
    }

}
