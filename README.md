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

Data Types
==========

### BeliefState

The basic node of state. It contains itself and a list of actions

### Action

An action is an interface. It can have an attached Runnable that will be executed
if there is an activity to do.

### State

A State represents a game state. It has a

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

