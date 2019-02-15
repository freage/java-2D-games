Merging
-------

* Merge `MatrixObservers` into `BaseModel`.
* In `tickgames`, merge `Simulation` into `Controller`.
* Consider merging the snake models.

Cleaning
--------
* Go through the code and change comments and variable names into English. 
* Stop using capitalised method names, instead of camel case. 

Fixed in all the implemented models.  <--- TODO: I was in the midst of this.

Changing the code
----------------

* When starting a new game (or restarting), the old View does not become garbage collected, since there is a reference to it in the Observer list. Fix this!!!
* Real 15-puzzle (guaranteed to solve) by applying a set of random valid moves. Seems to actually be done correctly - check this. Yep, FIXED.
* Move `Position` to the top package? FIXED.
* Start using `Position` in the 15-puzzle for the empty square. FIXED.
* Abstract a `BaseMenu` for the menus to inherit from. Possible?
* Make sure the notifying (model notifying the observer/view) is done in a canonical way. 

    * 15-puzzle. FIXED.
    * TicTacToe. FIXED.
    * Minesweeper. FIXED.
    * Snake. FIXED. Only time where `notifyObservers()` (redraw everything) is used. It is only done once.

