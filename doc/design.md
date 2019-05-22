Design details
==============

Model initialisation
---------------------
This is how initialisation works:

* `Model()` creates an empty matrix;
* `View(M)` creates a matrix without content from the model, which is not yet ready.
* The View is added as an observer to the model.
* `Controller(M,V)` creates eventual listeners and/or the control panel with instructions or buttons.
* The BaseMenu calls `ctrl.start()` which calls the following
    * `model.start()`, calling 
        * `model.reset()` (reset variables), and 
        * `model.fill()` (fill the matrix with content for starting the game, may call `set`)
    * `view.updateAll()`, redrawing the view; only time this is used
    * `ctrl.run()`, starts releasing events that the model can react on, like the timer or listening to user events.
* The BaseMenu can in the future call `ctrl.restart()` which calls
    * `ctrl.pause()`, having the controller stop listening to timer and/or user events,
    * `ctrl.start()`, letting the model and the view reset themselves before resuming listening.

The restart functionality is not implemented in all games yet.

Updating the View upon changes in the Model
-------------------------------------------
All these games are matrix based. In click games, the view is a matrix of `JButton`s; whereas in tick games, it is a matrix of `JLabel`s. There might be faster ways to implement some of these games, at least the tick games, like by having a single canvas instead of a matrix of monochrome canvases. But I chose this way because it allows much of the code to be reused. An advantage of using button components in the click games, is that they come with functionality for clicking on them as well as having text on them.

In the model, the game is represented by a 2D array of integers, with each integer being translated to a colour in the view. The game matrix is found in the `BaseModel`; to update the value in position `i, j` in the matrix, a subclassing model calls `this.set(i, j, value)`. The function `BaseModel.set()` also notifies the observers that the value at position `i, j` changes so that the observers, i.e. the view, can redraw that square. So if you are adding a new game by subclassing `Model`, you do not have to concern yourself with how the observers work - only make sure that you call the `set()` function whenever you change the game matrix.

In click games, the view will query the model on how it should represent the integer in found in the game matrix - what button colour, text and button colour should be used? For example in MineSweeper, if a user clicks a `1`, the integer representing this will be translated to *text:* 1, *text colour:* blue, *background colour:* grey. But if the user clicks on a mine, the integer representing a mine will be translated to *text:* the unicode sign for explosion, *text colour:* orange, *background colour:* red.
