HELLO=hello/Hello

MINESWEEPER=games/clickgames/MineSweeper
# Skriver ut övertäckt matris i terminalen

SNAKE=games/tickgames/Menu
# Får ett lite hackigt snakespel

SNAKE_M=games/tickgames/Snake
# Skriver ut pixelmatris i terminalen

CLICKGAME=games/clickgames/Menu
# Får en meny där man väljer mellan 15-spel, luffarschack och minröj

FEMTONSPEL=games/clickgames/FifteenPuzzle
# Skriver ut matris i terminalen


GAMES=$(FEMTONSPEL)

COMPILE=javac --source-path src -d bin

RUN=java --class-path bin

c_hello: 	src/$(HELLO).java
	$(COMPILE) $<

c_games: 	src/$(GAMES).java
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

games: 	c_games
	$(RUN) $(GAMES)

