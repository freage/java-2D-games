Multithreading safety of the tick games
====================================

Multithreading in Java
----------------------
Reads and writes of `int`s (and most other primitive types) are atomic. If you want atomic read/write of another variable, declare it `volatile`. 

Mutex locks can be implicitly added with the keyword `synchronized`. The executions of two synchronized methods on the same object will not interleave. There are also two synchronized blocks, i.e. a block of statements, where you specify the object that should be locked while that block is executing. (Synchronized blocks can only lock references, not primitive types. If you want to lock a primitive type, copy it to a local variable instead.)

Snake
-----
Two snake-functions are called by events (ticks or user actions) from the Controller, `simulate()` and `request(int)`. Can they overlap and is it a problem?
* `request()` and `request()`: No, they just overwrite an atomic-access variable and we want the last write anyway.
* `request()` and `simulate()`: `simulate()` uses the variable that `request()` is overwriting. Fixed by copying it to a local variable.
* `simulate()` and `simulate()` (if the ticks are too short, when the computer is busy):
    * The `direction` could change while calculating new position. Fixed by copying it to a local variable.
    * The `snake` could change while updating it. 
        * `move()` and `move()` interleaving: Put the changes in `move()` in a synchronized block locking `snake`.
        * `popTail()` and `popTail()` interleaving: `popTail()` only accesses it once, it is sufficient to declare `snake` volatile.
        * `popTail()` and `move()` interleaving: since there is only one access in `popTail()`, the lock plus atomic access is sufficient.

Advanced Snake
-------------
TODO

Tetris
------
TODO
