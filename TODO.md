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

    * Walls, no torus.
    * Moebius strip instead of torus, and other topology. Mark strange portals with gradients?
    * Walls in the middle of the field. Maybe some randomness in the construction.
    * Measuring user clicks per cheese.
    * Levels; after clearing XX cheeses on the current level, advanced to new level (small snake again) with other topology.
    * Funny topology: interleaving columns `delta_n` is always `+/- 2`; has to be odd width to be able to reach all cheeses once passing through the edge. Better have the snake have different colors when it is in even/odd column position.

* Pong? Single player pong against wall; two player pong with threading. That could be hard, though, it is better with games that consists of squares.
* Tetris. Sort of works, still a little buggy. 

Tetris
-------
Issues:

* ISSUE: No support for rotating the blocks. No random rotation at start either.
* ISSUE: A block cannot be moved at the last row before freezing. Solution: Freezing should be done the tick after touching the heap.
* BUG: Horizontally inserting a block into a hole does not make it stick - it continues to fall down. Understandable, because it stuck "under" the heap "top line", so it is not detected.
* BUG? Still not sure if the point checking and collapsing of rows is done correctly. Have not seen anything clearly incorrect yet.
* BUG? At least before, it seemed from the stdout prints, that new blocks were created long before they appeared - even before the previous one had frozen.
* BUG? Go through everything again and think about what methods should be synchronized.
* ISSUE: Add preview of the next block? Might be hard, what class should display it and where? Also, there is an assumption in the code about only one brick existing at the time that becomes invalid if you add a preview.
* BUG? Have not tried to see what happens if you lose. The game might crash.

Advanced Snake
--------------
Added leveling functionality. Levels:

* Ordinary torus
* A square (walls along the edges)
* "Inverted square": the edges have been moved to form a plus and the edges are torus edges.
* Mirrored columns: when exiting north/south you will enter the *same* wall in the mirrored column. Ordinary torus edges in east/west. (Adding a vertical bar wall could be interesting? No, at the same time, this prevents the funny collisions.)
* Four L-corners: 4 L-shaped walls, as if originally in the corners but moved towards the centrum.

Issues:

* If hitting enter to restart, it restarts the current level, even if you failed and should restart at level 0. If you have not failed, it is reasonable to restart at the same level. (But it is so hard, so right now it feels reasonable to not restart everything!)
* Fire a leveling event to the Controller? Avoid funny recursion in `actionPerformed`.
* Display level info in the control panel, not in the terminal.
* Pausing and counting down should be done in the Controller.
* Resizing the game grid can be tricky, but it would be nice to have a larger grid when the topology is strange.
* While we check that the initial head position is not inside a wall, we do not check the other segments... Actually in the ordinary snake, the segments behind the body can start outside the board as well. But here it leaves a hole in the wall, both in the model and the view. Any cheese appearing there cannot be taken without collision.
* Add "portal walls", maybe light blue, signalling that this is neither a wall nor an ordinary torus edge.

UML diagram
------------
Make sure the UML diagram is up to date.

Missing in the diagram:

* The View and the Controller should have access to the Model
* The Controller should have access to the View.
* Maybe the Menu does not need the View? -- Yes, it does, to add the visual component.
* The `AdvancedSnake` is empty as of now.

Model initialisation
---------------------
This is how initialisation works:

* `Model()` creates an empty matrix;
* `View(M)` creates a matrix without content from the model, which is not yet ready.
* The View is added as an observer to the model.
* `Constructor(M,V)` creates eventual listeners and/or the control panel with instructions or buttons.
* The BaseMenu calls `ctrl.start()` which calls the following
    * `model.start()`, calling 
        * `model.reset()` (reset variables), and 
        * `model.fill()` (fill the matrix with content for starting the game, may call `set`)
    * `view.updateAll()`, redrawing the view; only time this is used
    * `ctrl.run()`, starts releasing events that the model can react on, like the timer or listening to user events.
* The BaseMenu can in the future call `ctrl.restart()` which calls
    * `ctrl.pause()`, having the controller stop listening to timer and/or user events,
    * `ctrl.start()`, letting the model and the view reset themselves before resuming listening.
