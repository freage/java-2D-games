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

* Pong; single player pong against wall; two player pong with threading.

Advanced Snake
--------------
Added leveling functionality. Only two levels. Issues:

* If hitting enter to restart, it restarts the current level, even if you failed and should restart at level 0. If you have not failed, it is reasonable to restart at the same level.
* Fire a leveling event to the Controller? Avoid funny recursion in `actionPerformed`.
* Display level info in the control panel, not in the terminal.
* Pausing and counting down should be done in the Controller.
* Resizing the game grid can be tricky, but it would be nice to have a larger grid when the topology is strange.
* While we check that the initial head position is not inside a wall, we do not check the other segments... Actually in the ordinary snake, the segments behind the body can start outside the board as well. But here it leaves a visual hole in the wall, while the model thinks there is still a wall there.

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
