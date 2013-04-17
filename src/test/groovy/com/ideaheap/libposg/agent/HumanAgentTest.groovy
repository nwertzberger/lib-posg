package com.ideaheap.libposg.agent

import com.ideaheap.libposg.state.Action
import com.ideaheap.libposg.state.Observation
import org.junit.Test

/**
 *  User: nwertzberger
 *  Date: 4/16/13
 *  Time: 8:20 PM
 * Email: wertnick@gmail.com
 *
 * It looks like I forgot to explain this class.
 */
class HumanAgentTest {
    @Test
    public void canLoadAgents() {
        Agent a = new HumanAgent("test")
                .withAction(new Action("EAT"))
                .withObservation(new Observation("DARK"))
                .withHorizon(4)
                .withBelief([CAT: 1.0]);
    }
}
