Merging
-------

* Merge `MatrixObservers` into `BaseModel`. FIXED
* In `tickgames`, merge `Simulation` into `Controller`. FIXED.
* Consider merging the snake models. Not done in UML-diagram.

Cleaning
--------
* Go through the code and change comments and variable names into English. 
* Stop using capitalised method names, instead of camel case. 

Fixed in all the implemented models.  <--- TODO: I was in the midst of this.
Fixed in `tick.Controller`. Fixing it in `tick.View` seems to have introduced a bug.

Remove the debug trace printing.

Changing the code
----------------

* When starting a new game (or restarting) in `click`, the old View does not become garbage collected, since there is a reference to it in the Observer list. Fix this!!!
    * Nope, that should not be true (not seen in trace print). (Restarting calls `BaseModel::initialise()`, which creates a new observer list and discards the old.) No, real reason should be that we also create a new Model anyway, so if there are no references to the old one, it should be fine...
* Real 15-puzzle (guaranteed to solve) by applying a set of random valid moves. Seems to actually be done correctly - check this. Yep, FIXED.
* Move `Position` to the top package? FIXED.
* Start using `Position` in the 15-puzzle for the empty square. FIXED.
* Abstract a `BaseMenu` for the menus to inherit from. Possible?
* Make sure the notifying (model notifying the observer/view) is done in a canonical way. 

    * 15-puzzle. FIXED.
    * TicTacToe. FIXED.
    * Minesweeper. FIXED.
    * Snake. FIXED. Only time where `notifyObservers()` (redraw everything) is used. It is only done once.


A bug
---------
The `restart` functionality in Snake has been broken. It does not work when you have lost (maybe not in the middle of the game either) as per commit `948ef82dbb1c` but it was probably the commit before ("Cleaned snake") that broke it. <--- TODO: high priority

* Yeah, restarting in current version does not work mid-game either. The trace log looks the same.
* Nope, at "Cleaned snake", it was working. So it must have been `948ef82dbb1c` that broke it.
* It was probably the changes in the function `Snake.start()` that broke it.
* Nope, the model seems to restart properly, as shown by trace printouts. 
* The View is stuck?
  - Check if it properly loops trough the squares to update? Maybe width/height was set to 0?
* FOUND the problem. Upon restart, this happens: `Model::restart() -> Model::start() -> BaseModel::initialise()` and the last function creates a new observer list. This is apparently not a problem in the click-games.
* Cannot see how come this worked in the commit before, based on the diffs.
* NOW how to solve this?
* As mentioned under "Changing the code", `click` creates new views in a problematic way. However you probably want something scalable allowing for multiple games in the menu, just like in `click`. 
    * So how about doing that generic `BaseMenu<T extends JComponent>`? 
    * Wait, why is there even a restart-function in the Click-games? They always create new instances.
    * Maybe a shared Menu, with a click- and a tick-instance? Pros and cons of compiling separately.
    * Maybe easiest to make a shared basemenu first and then merge?

UML diagram
------------
Make sure the UML diagram is up to date.

Missing in the diagram:

* The View and the Controller should have access to the Model
* The Controller should have access to the View.
* Maybe the Menu does not need the View? -- Yes, it does, to add the visual component.
