package com.ideaheap.libposg;

/**
 * User: nwertzberger
 * Date: 3/30/13
 * Time: 3:53 PM
 *
 * U = set
 * e =
 * O =
 * o = the empty set?
 * k =
 * V = set
 * W =
 *
 * # Returns the set V e dominated by U
 * IEPrune(U, e, O, k):
 *      V = (remove) best vectors from U at belief simplex
 *      W = o, Cliques = o
 *      do:
 *          v = O(U)
 *          b = eDominate(v, V, e)
 *          if b == null:
 *              next
 *          v' = (remove) best vector at b from U
 *          V.add(v')
 *          S = {{vi}|vi in V and |{vi}| = k}
 *          for s in S:
 *              X = s . W, V' = V\s
 *              if !isEConsistent(X, V', e):
 *                  Cliques = Cliques . s
 *          Candidates = Ui * Si | Si in Cliques
 *          V' = V\Candidates
 *          Pruned = o
 *          for v in Candidates:
 *              X = {v} . W
 *              if !isEConsistent(X,V',e):
 *                  V' = V' . {v}
 *              else:
 *                  Pruned = Pruned . {v}
 *          V = V', W = W . Pruned
 *      until U = o
 *      return V
 */
public class IEPrune {
}
