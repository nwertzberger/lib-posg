package com.ideaheap.libposg.simulator

import org.junit.Test

/**
 *  User: nwertzberger
 *  Date: 4/14/13
 *  Time: 3:40 PM
 * Email: wertnick@gmail.com
 *
 * It looks like I forgot to explain this class.
 */
class SimulatorTest {

    @Test
    void canParseYamlFile() {
        InputStream domain = this
                .getClass()
                .getClassLoader()
                .getResourceAsStream("domain.yaml")
        Simulator world = new Simulator(domain)
    }

    @Test
    void canParseYamlString() {
        String data = """
            agents:
                thingOne:
                    actions: [ LEFT, RIGHT ]
                    observations: [ BUMP ]
                    belief:
                        ONE: 1.0
                    start: ONE

            games:
                ONE:
                    -   jointAction: {thingOne: LEFT}
                        rewards: {thingOne: 1.0}
                        transitions:
                            ONE:
                                p: 0.8
                                observations: {}
            """
        Simulator world = new Simulator(data)
    }


}
