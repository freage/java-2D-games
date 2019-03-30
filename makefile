HELLO=hello/Hello
# Hello world program (for testing)

TICKGAME=games/tickgames/Menu
# Menu where you choose between snake, snake with levels, and tetris

CLICKGAME=games/clickgames/Menu
# Menu where you choose between 15-puzzle, tic-tac-toe and minesweeper


COMPILE=javac --source-path src -d bin

RUN=java --class-path bin

c_hello: 	src/$(HELLO).java
	$(COMPILE) $<

c_tick:  	src/$(TICKGAME).java
	$(COMPILE) $<

tick:	c_tick
	$(RUN) $(TICKGAME)

c_click:  	src/$(CLICKGAME).java
	$(COMPILE) $<

click:	c_click
	$(RUN) $(CLICKGAME)

test_all: c_tick c_click


hello:	c_hello
	$(RUN) $(HELLO)

wc:
	cat src/games/*.java src/games/*/*.java | wc
