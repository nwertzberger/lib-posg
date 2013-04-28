package com.ideaheap.util

import org.junit.Test

/**
 * User: nwertzberger
 * Date: 4/27/13
 * Time: 7:20 PM
 */
class PermutationsTest {

    @Test
    public void canPermute() {
        assert Permutations.of([1, 2]) == [[], [1], [2], [1, 2]]
    }
}
