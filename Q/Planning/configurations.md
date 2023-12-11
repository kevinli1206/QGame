Changes for Milestone 10

Client
- Added a new quiet field to represent whether we want debug to be run
- Modified the existing constructor to accept an IPlayer that the client is initialized with
- Deleted the connectToServer method and moved its content to the constructor
- Modified the constructor to keep trying to reconnect to a server that hasn't started up yet
- Added a 10 second timeout for the client to attempt to try to connect to the server
- Added functionality for client to gracefully exit if server hasn't started up yet
- Added functionality to print debugging messages if quiet flag is false


Server
- Added new fields for each of the serverConfigs
- Added a new constructor that takes in each serverConfig and sets the new fields to these values
- Changed all occurrences of previous static ints for server-tries, server-waits, and wait-for-signup to use the new fields
- Refactored the number of tries for looking for players to use a for loop to run rounds
- Added functionality to print debugging messages if quiet flag is false

Referee
- Added new fields int perTurn and boolean quiet which represent how long a referee should wait for player input and whether or not we want to print to debug port
- Added a new constructor that takes in boolean addObserver, perTurn, and quiet and adds an observer to our referee is the flag is true and sets perTurn and quiet equal to the given values 
- changed all occurences within the referee that uses the TIME_TO_MAKE_MOVE(our original constant) to now use the given perTurn
- Added functionality to print debugging messages if quiet flag is false

Gamestate
- Added new fields int qbo and int fbo that represent the bonuses for placing a Q and for finishing
- Added a new constructor that takes in qbo and fbo and sets the fields to those values
- Modified our existing bonuses to use the new qbo and fbo fields