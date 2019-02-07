HELLO=hello/Hello

MINESWEEPER=games/clickgames/MineSweeperModel
# Skriver ut övertäckt matris i terminalen

SNAKE=games/tickgames/Spel2D
# Får ett lite hackigt snakespel

SNAKE_M=games/tickgames/SnakeModel
# Skriver ut pixelmatris i terminalen

VIEW=games/tickgames/View
# Avslutar tyst

PACSPEL=games/clickgames/PaCSpel
# Får en meny där man väljer mellan 15-spel, luffarschack och minröj

FEMTONSPEL=games/clickgames/FemtonModel
# Skriver ut matris i terminalen


GAMES=$(SNAKE_M)

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

c_click:  	src/$(PACSPEL).java
	$(COMPILE) $<

click:	c_click
	$(RUN) $(PACSPEL)

test_all: c_snake c_click


hello:	c_hello
	$(RUN) $(HELLO)

games: 	c_games
	$(RUN) $(GAMES)

