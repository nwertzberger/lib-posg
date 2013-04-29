lib-posg
========

A library implementing a generic "game engine" for use in agents that can have
their world expressed as a "partially observable stochastic game".


Implementation Details
======================

Background
----------

The algorithm is based on the work of Eric A. Hansen, Daniel S. Bernstein and Shlomo
Zilberstein  in "Dynamic Programming Approximations for Partially Observable Stochastic
Games". This paper covered how to extent the tools used to solve Partially Observable
Markov Decision Processes (single agent environments) into multi-agent environments
by listing each game that the robots may be currently playing.

This work aims to offer a solution to the problem of playing games with other agents
where there is not a precise knowledge of what state the agent is currently, agent
actions don't always have precisely the expected consequences, and observations are
only sometimes accurate.  Given these constraints, a statistical model is used that
decides on the path that is statistically most likely to achieve a high reward based
on the information available.

Given all this uncertainty, this program expects a complete map of the different states,
or "games" an agent could be in, as well as the probabilities of what destination states
choosing an action will result in, and the observation probabilities that would be
achieved after doing said action to reach each state. This is expected to be exhaustive.

Algorithm Used
--------------

The current algorithm used is a horizon-based depth-first-search from an agent's current
belief state. The search is exhaustive, and even the game used in the simulator example,
with a branch-factor of 8 (2 actions / 4 permutations of 2 observations) runs pretty
slowly at a horizon of 4 or more.




Defining a Game
===============

Th
The following data types are meant to be used to create a game description.

Action
------

An agent is capable of doing actions. Actions are how an agent interacts
with the world. After an action has been defined for an agent, that same
object must be used whenever a reference to an action is required.

Game
----

A game is really a set of JointActions. It is a holding data type, but
represents the destination Game to be used for other locations. Like
Actions, once a game has been created, the same reference should always
be used.

The assumption is made that you have a JointAction for every possible
combination of Agent Actions.

JointAction
-----------

A JointAction represents the mapping of agent actions to agent rewards
as well as the potential transitions to another state from this action.

You will add a lot of these.

Transition
----------

Transitions show what destination state can be reached, and what probable
observations would be made if it is the current transition.

Observation
-----------

Observations are used by an agent to sense its current state. These should
be tied to the agent using the observation. The same object should be passed
to every JointAction.

Assumptions / Shortcomings
==========================

As with any new project, There are a laundry list of TODO's.

Agent actions are the same for every game
-----------------------------------------

Every game has every joint action of every agent participating. The actions are defined
inside of the agent, and these actions are used in every game.
This is really the only way any agent can survive in a pOSG, as we really don't know
exactly which game we are playing at any given time.

Observations are only of the "true / false" variety
---------------------------------------------------

Gradient-based observations has not been attempted.

Opponent Agent responses are not planned against
---------------------------------------------------------------------

This program currently takes an average of all joint actions where this agent

I hope to change this into a more generic "strategy" action, that can then result in
mixed-strategy nash equilibria, but right now, this is not the case.

If there are two equally valued strategies, they will be chosen randomly with even weight.

Internals
=========


This algorithm works by generating a horizon "X" policy tree for the agent given the current
belief state each time generateStrategy is called. After generating the policy tree to use,
calling decideGameAction will cause the agent to traverse the generated policy tree until it hits
the edge. If generateStrategy is never called, it is automatically called when an agent hits an
"edge node" on its policy tree. Below is the traversal algorithm used:

- if horizon = 0, return null
- Normalize incoming belief vector
- Find best action available. For all actions:
    - For all games:
        - For all observations
            - Update belief for this.
    - Normalize all action - observation combo beliefs.
    - Generate an easier to parse data structure. For all games:
        - For all joint actions
            -For all transitions:
                 - For all observations:
                    - Calculate P(o|s')*P(s'|a,s)*b(s) to store in a map of observation ->
                      transition probabilities. *These are actually un-normalized belief
                      vectors with all potential games aggregated into the value*
    - For all observations:
        - Calculate the expected value of this game with horizon - 1 and belief from the
          current action + observation
    - For all games:
        - Calculate expected value of this action given our belief vector. (b(s) * R(s,a))
    - For all observations:
        - Calculate expected value of transitioning to this belief state. Aggregate.
    - Maximize / only return the best action.

License
=======

The MIT License (MIT)
Copyright (c) 2013 Nicholas Wertzberger

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
of the Software, and to permit persons to whom the Software is furnished to do
so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
