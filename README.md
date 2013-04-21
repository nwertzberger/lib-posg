lib-posg
========

A library implementing a generic "game engine" for use with "partially
observable stochastic games". Basically, if you are given a problem where you
know what "games" will be played, and probabilities of actions leading to
certain games, this would be usable by you.  NOTE: Path finding can be shown
as a game. This is a more generic version of a POMDP.

Background
==========

This is for a project in school. However, it sounds awesome and should be open
sourced.  The algorithm is based on the work of Eric A. Hansen, Daniel S.
Bernstein and Shlomo Zilberstein  in "Dynamic Programming Approximations for
Partially Observable Stochastic Games".

Defining a Game
==========

The following datatypes are meant to be used to create a game description.

### Action

An agent is capable of doing actions. Actions are how an agent interacts
with the world. After an action has been defined for an agent, that same
object must be used whenever a reference to an action is required.

### Game

A game is really a set of JointActions. It is a holding datatype, but
represents the destination Game to be used for other locations. Like
Actions, once a game has been created, the same reference should always
be used.

The assumption is made that you have a JointAction for every possible
combination of Agent Actions.

### JointAction

A JointAction represents the mapping of agent actions to agent rewards
as well as the potential transitions to another state from this action.

You will add a lot of these.

### Transition

Transitions show what destination state can be reached, and what probable
observations would be made if it is the current transition.

### Observation

Observations are used by an agent to sense its current state. These should
be tied to the agent using the observation. The same object should be passed
to every JointAction.

Assumptions / Shortcomings
==========================

As with any new project, There are a laundry list of TODO's.

### Agent actions are the same for every game.

Every game has every joint action of every agent participating. The actions are defined
inside of the agent, and these actions are used in every game.
This is really the only way any agent can survive in a pOSG, as we really don't know
exactly which game we are playing at any given time.

### Observations are only of the "true / false" variety.

At least in this version, attacking gradient observations has not been attempted.

### Only pure strategies are evaluated

I hope to change this into a more generic "strategy" action, that can then result in
mixed-strategy nash equilibria, but right now, this is not the case.

If there are two equally valued strategies, they will be chosen randomly with even weight.

### Opponent Agent responses are only determined based on the current game.

I should draw out a policy tree of horizon h for each of these agents as well.

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
    - For all games:
        - Calculate expected value of this action given our belief vector. (b(s) * R(s,a)
        - Calculate the new belief vector based on this action. (b^a)
        - Go through every possible observation
            - calculate the probability of this observation (P(o|s,a)). HOLD ON TO THIS
            - calculate a new belief vector based on this operation.
            - call this function at step 0, using our new belief vector and horizon - 1.
        - Test to see if action is worth saving. If not, trash it.

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
