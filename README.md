Package `games`
===============

This is originally from a course assignment from a first year OOP course. I have added additional games, but kept some of the ideas (MVC-pattern with Model inheritance to reuse code) from the lab.

Background
-----------
### Original assignment
Using an MVC pattern, in Java's Swing 2D graphics library, implement a GUI for at least two games from a suggested list (I chose Tic-tac-toe and 15-puzzle). Realising that these games are all very similar implementation-wise, and the View and Controller can be reused, you should do a Model base class / interface and have your games subclass/implement this Model. It should be easy to add a third game simply by just subclassing the Model class (and maybe change a couple of rows in the main program).

### My modifications
After the course assignment was done, I first added Minesweeper as a third game subclassing Model.

Then I wanted to implement the first-in-my-life Snake, but since it is a bit different (threaded, and no user clicking), I ended up mostly creating a new Model/View/Controller while copying a bit of the code. 

Since redrawing the entire board each tick is quite expensive, I changed the Model from being unaware of the View to semi-aware: having the View being an observer/listener of Model, so that it only redraws the squares that have been changed.

Several years later I wanted to clean up the code a bit, and now being a better coder, I was able to abstract a shared skeleton for the category "click games" (Tic-tac-toe, 15-puzzle and Minesweeper) and "tick games" (Snake and other tick based games). They have in common that they are both "matrix games" - there is a simple game grid with squares. I also added Tetris as another "tick game", as well as an "advanced snake" having levels with different topologies/boards. Finally I wanted to make the tick games threading secure, by adding mutex locks where needed.

Current code
------------
The result is a complicated (but motivated) OOP-designed game program where you can choose between different games. There is a makefile, so run `make click` to play the click games, and `make tick` to play the tick games.

Considering the games separately, this OOP design might slow down the games somewhat. But the code "demonstrates" my knowledge of Java and OOP in several areas:

* polymorphism and inheritance, the Java way
* MVC pattern
* Java's Swing library
* multithreading and mutex locks, the Java way
* Java's generics
* reflections in Java (a little bit in the Menu class)

Design details
-------------
There is an UML diagram showing the relations between the classes. The obvious MVC-relations between the Model, the View and the Controller are not included, to avoid having the diagram cluttered with relation lines.

The multithreading safety reasoning is in [a separate file](multithreading.md).

The levels in the "advanced snake" are described in [a separate file](snakelevels.md).

Details of parts of the design are described in [a separate file](design.md).
