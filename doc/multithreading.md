Multithreading safety of the tick games
====================================

Multithreading in Java
----------------------
Reads and writes of `int`s (and most other primitive types) are atomic. If you want atomic read/write of another variable, declare it `volatile`. 

Mutex locks can be implicitly added with the keyword `synchronized`. The executions of two synchronized methods on the same object will not interleave. There are also two synchronized blocks, i.e. a block of statements, where you specify the object that should be locked while that block is executing. (Synchronized blocks can only lock references, not primitive types. If you want to lock a primitive type, copy it to a local variable instead.)

Multithreading in the tick games
----
During the execution of the program, the model, view and controller are first all set up, sequentially. Then the program starts listening to events: events from the timer (ticks) and events from the user (requests). In reaction to events, the controller will call three methods in the model

* `simulate()` - called on each tick
* `start()` - used for setting up the game, so it is called on "restart" requests
* `request(int)` - called on each other request

As for the method `start()`, there is a pausing mechanism in the controller. The game is paused, preventing the controller from _starting_ other method calls at the same time the `model.start()` method is executed, _but_, there could be unfinished method calls executing at the same time, that were started _before_. Therefore its execution could also overlap with other method calls on the model (unless we add locks). 

These three functions are the "writes"; they change the Model. The Controller and the View also do some "reads" of the Model. The solution is then to 

* set these three methods as synchronized, making the writes atomic
* make sure that all reads of the model (by other classes) are atomic

### Atomic writes
The function `Model::start()` is `final` in the BaseModel, so we have to declare it synchronized there. As for `simulate()` and `request(int)`, they are declared in `tick.Model`, but they are abstract, and that modifier cannot be combined with synchronized. So it is up to the Model implementations to declare those two synchronized.

### Atomic reads
The View updates itself by reading from the Model. How do we know that the read is atomic?

We do, since we use a listener pattern. The Model _itself_ tells the View to update itself, and the Model does so from the functions that modify the Model. Those are declared synchronized and thus blocking. So the Model cannot change while the View is reading from it. 

In fact, `BaseController::start()` also calls an update-function on the View, but it does so after calling one of the synchronized functions in the Model, `Model::start()` (so all unfinished executions on the Model will have to finish first). At this point, between `model.start()` and `run()` a few rows later, the program executes sequentially, so this read of the Model will also be atomic.

The Controller modifies the Model through the three synchronized functions, so as for writes, they are atomic. How about reads?

The Controller does two reads from the Model, in the function `Controller::actionPerformed(AE)`. The result of the branching assumes that the state that decided the branching (game over or not) is still valid, but it could change if the user requests a restart in between. Therefore, we add a lock on the Model around this branching.

