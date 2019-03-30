The levels in Advanced Snake
----------------------------

* Ordinary torus
* A square (walls along the edges)
* "Inverted square": the edges have been moved to form a plus and the edges are torus edges.
* Mirrored columns: when exiting north/south you will enter the *same* wall in the mirrored column. Ordinary torus edges in east/west. (Adding a vertical bar wall could be interesting? No, at the same time, this prevents the funny collisions.)
* Four L-corners: 4 L-shaped walls, as if originally in the corners but moved towards the centrum.
* Möbius strip.
* "2D columns": The snake always advances 2 steps when going horizontally and the width is odd, so it is either always in odd or even columns, but changes to the other mode when it exits a wall on the left/right side.
