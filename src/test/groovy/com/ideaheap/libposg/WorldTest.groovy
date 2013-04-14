package com.ideaheap.libposg

import com.ideaheap.libposg.agent.Agent
import com.ideaheap.libposg.simulator.World
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 *  User: nwertzberger
 *  Date: 4/14/13
 *  Time: 3:40 PM
 * Email: wertnick@gmail.com
 *
 * It looks like I forgot to explain this class.
 */
class WorldTest {

    @Mock
    Agent agent;

    @Before
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void canParseYamlFile() {
        InputStream domain = this.getClass().getClassLoader().getResourceAsStream("domain.yaml")
        World world = new World(domain, agent)
    }

    @Test
    void canParseYamlString() {
        String data = """
            agents:

            games:
                ONE:
                TWO:
            """
        World world = new World(data, agent);
    }
}
