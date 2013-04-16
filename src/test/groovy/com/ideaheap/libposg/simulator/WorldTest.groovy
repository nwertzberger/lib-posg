package com.ideaheap.libposg.simulator

import org.junit.Test

/**
 *  User: nwertzberger
 *  Date: 4/15/13
 *  Time: 7:38 PM
 * Email: wertnick@gmail.com
 *
 */
class WorldTest {
    @Test
    void canLoadGame() {
        World world = new World();
        world.setGames([
            ONE : [ [
                jointAction : [ firstHuman : "eat"],
                rewards: 1.0,
                transitions : [
                    ONE: [ p: 1.0, observations: [] ]                        ]
                ]
            ]
        ]);

        assert world.getGames().size() == 1
    }
}
