Pair: spooky-monkeys \
Commit: [4747c26679dc96f5760501e84ca75009bb3ae789](https://github.khoury.northeastern.edu/CS4500-F23/spooky-monkeys/tree/4747c26679dc96f5760501e84ca75009bb3ae789) \
Self-eval: https://github.khoury.northeastern.edu/CS4500-F23/spooky-monkeys/blob/5ad8e75f4f699da591fc6fd45cffe8442bce91b8/2/self-2.md \
Score: 80/95 \
Grader: Jose Sulaiman Manzur

Programming [60/70]: \
[10/10] Data representation for tiles. \
[10/10] Data representation for coordinates. \
[10/10] Initializing and placing tile functionality. \
[10/10] Signatures and purpose statements. \
[10/20] Looking for available tiles: Should factor out finding all feasible places meaning free neighbor tiles \
[10/10] Unit tests 

Design [20/25]: \
[5/5] Map data representation. \
[10/15] The referee should know the order the players go in as well as their scores and tiles \
[5/5] List of cuntionality 


Notes:
 - Please do not commit your .jar files. Create a Makefile that generates it (would reccomend using maven). The .jars will get really heavy and if they're in all your commits you will quickly run out of space on the Khoury machine. 
 - For `insertTile` look into using the `computeIfAbsent` method on maps. Usually a neater way of doing the `if (!key exists) initialize ...` logic. 
 - Make your tests more readable. Could do this by naming the examoples and test methods in a more descriptive manner. 
 
