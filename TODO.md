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
* Consider merging the snake models. Not done in UML-diagram.

A bug - restarting snake
---------
The `restart` functionality in Snake has been broken. It does not work when you have lost (maybe not in the middle of the game either).

* At "Cleaned snake", it was working. So it must have been `948ef82dbb1c` that broke it.
* The model seems to restart properly, as shown by trace printouts. 
* FOUND the problem. Upon restart, this happens: `Model::restart() -> Model::start() -> BaseModel::initialise()` and the last function creates a new observer list. So the old View is discarded by the Model, but not in the Menu. The Model ends up having no observer. This is apparently not a problem in the click-games.
* Cannot see how come this worked in the commit before, based on the diffs.
* NOW how to solve this?
* As mentioned under "Changing the code", `click` creates new views in a problematic way. However you probably want something scalable allowing for multiple games in the menu, just like in `click`. 
    * So how about doing that generic `BaseMenu<T extends JComponent>`? 
    * Wait, why is there even a restart-function in the Click-games? They always create new instances.
    * Maybe a shared Menu, with a click- and a tick-instance? Pros and cons of compiling separately.
    * Maybe easiest to make a shared basemenu first and then merge?
* Update: Now the Models became abstract classes instead of interfaces, and the View got an abstract `BaseView`. Trying to abstract as much as possible, as to get a canonical way of doing things. Working towards a `BaseMenu`!

UML diagram
------------
Make sure the UML diagram is up to date.

Missing in the diagram:

* The View and the Controller should have access to the Model
* The Controller should have access to the View.
* Maybe the Menu does not need the View? -- Yes, it does, to add the visual component.

Model initialisation
---------------------
Consider fixing this:
* Have a `BaseModel` constructor
* Do not call functions e.g. `start()` from constructor at all
* The `initialise()` stuff is constructor-stuff, but it is called at restart as well, so it cannot simply be moved to the constructor.
* Decide whether there should even be a `restart()` functionality or one should simply create a new model at restart. The `notifyObservers() --> updateMatrix()` functionality which redraws the entire matrix is only used when restartting snake, it seems.
