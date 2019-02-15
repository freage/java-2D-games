Merging
-------

* Merge `MatrixObservers` into `BaseModel`.
* In `tickgames`, merge `Simulation` into `Controller`.
* Consider merging the snake models.

Cleaning
--------
* Go through the code and change comments and variable names into English. 
* Stop using capitalised method names, instead of camel case. 

(Fixed in 15-puzzle & Tic-tac-toe.)

Changing the code
----------------

* Real 15-puzzle (guaranteed to solve) by applying a set of random valid moves. Seems to actually be done correctly - check this. Yep, FIXED.
* Move `Position` to the top package? FIXED.
* Start using `Position` in the 15-puzzle for the empty square. FIXED.
* Abstract a `BaseMenu` for the menus to inherit from. Possible?
* Make sure the notifying (model notifying the observer/view) is done in a canonical way. 

    * 15-puzzle. FIXED.
    * TicTacToe. FIXED.

