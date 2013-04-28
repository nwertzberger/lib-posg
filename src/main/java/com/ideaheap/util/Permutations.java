package com.ideaheap.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * User: nwertzberger
 * Date: 4/27/13
 * Time: 7:06 PM
 */
public class Permutations {
    public static <K> Set<Set<K>> of(final Collection<K> items) {
        Set<Set<K>> sets = new HashSet<Set<K>>();
        sets.add(new HashSet<K>(items));
        for (K k : items) {
            Set<K> newSet = new HashSet<K>(items);
            newSet.remove(k);
            for (Set<K> nk : of(newSet)) {
                sets.add(nk);
            }
        }
        return sets;
    }
}
