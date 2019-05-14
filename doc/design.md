Design details
==============

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
