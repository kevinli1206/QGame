# Directory Overview
- Common folder contains our own code for the project
  - The place folder/package contains classes to represent a place on a board. In our case, this is a x and y point.
  - The tiles folder/package contains classes used to represent a tile on the game board with a color and a shape.
  - The data folder/package contains classes to represent information to be sent out to playerState of the Q game.
  - The playerState folder/package contains classes to represent playerState of the Q game that have an id, tiles in their hand, and points. 
  - The map and IMap files contain code for our implementation of the game board for the Q game
  - The game_state and IGameState files contain code for our implementation of the game state for the Q game.
- The lib folder contains external jars and packages used for our project
- the Planning folder contains design documents for Q game
- The test folder contains tests for each piece of functionality in the Common folder
- The xtest file contains a script to run all tests in our project
# Road Map
<img width="672" alt="Screenshot 2023-10-25 at 9 32 31 PM" src="https://media.github.khoury.northeastern.edu/user/9890/files/212d63ca-8566-4a81-a378-00d4a988cd8b">


# Running Test Script 
- Run ```./xtest``` on the command line  to run all tests
- Running ```./xtest``` does not work on rendering our game_state in the GUI. Test GUI with individual tests using junit instead.
# Running Individual Tests
- Compile all files to a target folder
  - ex. ```find . -name "*.java" -print | xargs javac -d target -cp "Common/lib/*"```
    compiles all java files and stores artifacts in a target folder
- Run individual test classes using junit console launcher
  - ex. ```java -jar Common/lib/junit-platform-console-standalone-1.10.0.jar --class-path [Artifact folder, ex. target] --select-class [Test Class name with package directory path]``` 
