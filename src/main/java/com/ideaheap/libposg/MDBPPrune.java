package com.ideaheap.libposg;

/**
 * User: nwertzberger
 * Date: 3/30/13
 * Time: 4:03 PM
 *
 * A pruner from the text. Algorithm below:
 *
 * Qi =
 * MaxJointPolicies =
 * Ag =
 * Q1 =
 * Q2 =
 * O =
 *
 * MDBPPrune({Q[i]}, MaxJointPolicies):
 *      JointPolicies = |Q1| * |Q2|
 *      do:
 *          for agent i in Ag:
 *              i.size = MaxJointPolicies/Q[-i]
 *              Q[i]' = random selection of size i.size policies from Q[i]
 *              evaluate(Q[i]', Q[-i])
 *              IEPrune(V[Q[i]'], e, O, k)
 *              IncreaseEpsilon?
 *              JointPolicies = |Q1| |Q2|
 *      until JointPolicies <= MaxJointPolicies
 */
public class MDBPPrune {
}
