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

* When starting a new game (or restarting) in `click`, the old View does not become garbage collected, since there is a reference to it in the Observer list. Fix this!!!
    * Nope, that should not be true (not seen in trace print). (Restarting calls `BaseModel::initialise()`, which creates a new observer list and discards the old.) No, real reason should be that we also create a new Model anyway, so if there are no references to the old one, it should be fine...
* Abstract a `BaseMenu` for the menus to inherit from. Possible?
* Avoid calling methods from constructors.
* Have the control panels display meaningful information. Snake does not reset the control panel properly. 

Click game variations
---------------------
* Other dimensions of 4x4 in the 15-puzzle sems to work.
* Other variations in tic-tac-toe? Then what should be the rule for winning?
* Minesweeper: allow "double click" functionality, as an option.

Tick game variations
-------------------
* Snake:

    * Walls, no torus.
    * Moebius strip instead of torus, and other topology.
    * Walls in the middle of the field. Maybe some randomness in the construction.
    * Measuring user clicks per cheese.
    * Levels; after clearing XX cheeses on the current level, advanced to new level (small snake again) with other topology.

* Pong; single player pong against wall; two player pong with threading.

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
Consider fixing this:

* Decide whether there should even be a `restart()` functionality or one should simply create a new model at restart. The game initialization has been standardized, so there is theoretical support for restarting, but it has not been tested, besides in Snake only. 
* To test the restart functionality, one needs to change the BaseMenu, so that it calls restart instead of creating a new game from scratch, in the case that the user wants play the same game again.
* The restart works in Snake. The Controller does the restarting itself, not the Menu. Maybe the Menu should do that instead.

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
