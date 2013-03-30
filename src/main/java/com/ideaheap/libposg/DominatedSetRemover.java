package com.ideaheap.libposg;

/**
 * User: nwertzberger
 * Date: 3/30/13
 * Time: 3:42 PM
 *
 * This class is responsible for removing dominated sets from a game.
 * It is a pruning thing.
 *
 * Algorithm from text:
 *
 * a =
 * U =
 * e =
 * g =
 * b(s) =
 * S =
 *
 * eDominate(a, U, e):
 *      var g, b(s) for s in S
 *      var sum = 0
 *      for s in S:
 *          for u in U:
 *              b(s)[a(s) - u(s)] >= a + e
 *          sum += b(s)
 *      sum == 1
 *      if g >= 0:
 *          return b
 *      else:
 *          return null
 *
 *
 */
public class DominatedSetRemover {

}
