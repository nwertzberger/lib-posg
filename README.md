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
