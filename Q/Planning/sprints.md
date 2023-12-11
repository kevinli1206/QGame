##### TO: CEOs
##### FROM: Software Developers
##### DATE: September 12, 2023
##### SUBJECT: Sprint Goals

Below is a list of our sprints and the goals for each of them.

1. The goal of our first sprint is to implement the referee/rules of the game.
    This will be the model. Our model will contain a board, player data, and tiles.
    These will make up the game state. Our model will have methods including startGame(), 
    scorePoints(), handleMove(), checkGameEnded(). A board will contain a tile representation.
    It will also contain methods including handlePlacement(), checkQ(), and checkExtend().
    Player data will include their points and the tiles in their hand. tiles will be a collection of
    all the tiles in the game that is not in a player's hand or on the board.
    Based on these components, the model will handle all the rule logic and ensure that the
    game runs as intended.
2. The goal of our second sprint is to create the communication layer between playerState
    and the referee. This will involve creating a controller that will handle input
    being passed in by playerState. This input will be processed as JSON messages communicated
    through TCP. Our controller will accept input and pass it to the model to handle logistics.
    Our controller will then send messages back to the playerState to communicate the game state.
    The controller will always be either in a state of waiting for game to start or waiting for
    clients to pass in input for their turn.
3. The goal of the third sprint is to implement the playerState/observers for our game. These 
    persons will represent our clients. The clients will communicate with our server through JSON
    messaged passed through TCP. These messages will be processed by the controller and it will
    send output back to our clients in the form of a view representation so that our clients can see
    the current game state. We will also create simple AI that can be used to test our server
    connections and game logic.

Thank you!