Cleaning
--------
* Go through the code and change comments and variable names into English. 
* Stop using capitalised method names, instead of camel case. 

Fixed in all the implemented models.  <--- TODO: I was in the midst of this.
Fixed in `tick.Controller`. Fixing it in `tick.View` seems to have introduced a bug.

NOT fixed in `click.Controller` nor `click.Menu`.

Remove the debug trace printing.

Changing the code
----------------

FIXED

* Merge `MatrixObservers` into `BaseModel`. FIXED
* In `tickgames`, merge `Simulation` into `Controller`. FIXED.
* Merged `AdvancedSnake` (cheese and points) into `Snake`; keeping empty class for "real" advanced snake.
* Real 15-puzzle (guaranteed to solve) by applying a set of random valid moves. Seems to actually be done correctly - check this. Yep, FIXED.
* Move `Position` to the top package? FIXED.
* Start using `Position` in the 15-puzzle for the empty square. FIXED.
* Abstract a `BaseView` for the views to inherit from. FIXED.
* Change the Model interfaces to abstract classes inheriting from `BaseModel`. FIXED.
* Make sure the notifying (model notifying the observer/view) is done in a canonical way. FIXED.

TODO list

* Change the BaseMenu, so that it calls restart instead of creating a new game from scratch, in the case that the user wants play the same game again. (Avoids having the old model/view/controller garbage collected.) Maybe also keep a deactivated instance of model/view/controller for each game?
* Add a general restart functionality like the one in snake (pressing space), controlled by the menu instead of the controller.
* Avoid calling methods from constructors.
* Have the control panels display meaningful information. Snake does not reset the control panel properly. 
* Implement another tick game. A simple pong has been suggested.

Click game variations
---------------------
* Other dimensions of 4x4 in the 15-puzzle sems to work.
* Other variations in tic-tac-toe? Then what should be the rule for winning?
* Minesweeper: allow "double click" functionality, as an option.

Tick game variations
-------------------
* Snake:

    * Measuring user clicks per cheese.

* Pong? Single player pong against wall; two player pong with threading. That could be hard, though, it is better with games that consists of squares.
* Tetris. Sort of works, still a little buggy. 

Tetris
-------
Issues:

* BUG? At least before, it seemed from the stdout prints, that new blocks were created long before they appeared - even before the previous one had frozen.
* BUG? Go through everything again and think about what methods should be synchronized.
* ISSUE: Add preview of the next block? Might be hard, what class should display it and where? Also, there is an assumption in the code about only one brick existing at the time that becomes invalid if you add a preview.

Advanced Snake
--------------
Added leveling functionality. See `snakelevels.md`.

Issues:

* If hitting enter to restart, it restarts the current level, even if you failed and should restart at level 0. If you have not failed, it is reasonable to restart at the same level. (But it is so hard, so right now it feels reasonable to not restart everything!)
* Fire a leveling event to the Controller? Avoid funny recursion in `actionPerformed`.
* Display level info in the control panel, not in the terminal.
* Pausing and counting down should be done in the Controller.
* Resizing the game grid can be tricky, but it would be nice to have a larger grid when the topology is strange.
* While we check that the initial head position is not inside a wall, we do not check the other segments... Actually in the ordinary snake, the segments behind the body can start outside the board as well. But here it leaves a hole in the wall, both in the model and the view. Any cheese appearing there cannot be taken without collision. FIXED.
* Add "portal walls", maybe light blue, signalling that this is neither a wall nor an ordinary torus edge. FIXED.
* Sometimes the commands seem to have no effect. FIXED: the `request` variable was reset in the case of interleaving executions. I removed this "feature".
* BUG? Go through everything again and think about what methods should be synchronized.
* The `columns2D` snake does not have visible portals. West/east walls should be portals.

UML diagram
------------
Make sure the UML diagram is up to date.

Missing in the diagram:

* The View and the Controller should have access to the Model
* The Controller should have access to the View.
* Maybe the Menu does not need the View? -- Yes, it does, to add the visual component.

Mutex locks in tick games
------------------------
See `multithreading.md`.

`Snake` has been covered. TODO: Go through `AdvancedSnake` as well.

TODO: Go through `Tetris`.
