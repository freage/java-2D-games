HELLO=hello/Hello
# Hello world program (for testing)

SNAKE=games/tickgames/Menu
# Play snake

CLICKGAME=games/clickgames/Menu
# Menu where you choose between 15-puzzle, tic-tac-toe and minesweeper


COMPILE=javac --source-path src -d bin

RUN=java --class-path bin

c_hello: 	src/$(HELLO).java
	$(COMPILE) $<

c_snake:  	src/$(SNAKE).java
	$(COMPILE) $<

snake:	c_snake
	$(RUN) $(SNAKE)

c_click:  	src/$(CLICKGAME).java
	$(COMPILE) $<

click:	c_click
	$(RUN) $(CLICKGAME)

test_all: c_snake c_click


hello:	c_hello
	$(RUN) $(HELLO)

wc:
	cat src/games/*.java src/games/*/*.java | wc
