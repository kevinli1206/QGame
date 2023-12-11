The commit we tagged for your submission is 7cc109f1cf27cfb4cf09729c18cca81706e8e01c.
**If you use GitHub permalinks, they must refer to this commit or your self-eval will be rejected.**
Navigate to the URL below to create permalinks and check that the commit hash in the final permalink URL is correct:

https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/tree/7cc109f1cf27cfb4cf09729c18cca81706e8e01c

## Self-Evaluation Form for Milestone 10

Indicate below each bullet which file/unit takes care of each task.

The data representation of configurations clearly needs the following
pieces of functionality. Explain how your chosen data representation 

- implements creation within programs _and_ from JSON specs 

creation within programs:
- Implemented builder pattern within the ServerConfig class that builds a Server based on its configs.
- https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/7cc109f1cf27cfb4cf09729c18cca81706e8e01c/Q/Server/ServerConfig.java#L50
- implemented builder pattern within RefereeConfig class that builds a Referee based on its configs.
- https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/7cc109f1cf27cfb4cf09729c18cca81706e8e01c/Q/Referee/RefereeConfig.java#L29-L34

creation from JSON specs:
https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/7cc109f1cf27cfb4cf09729c18cca81706e8e01c/Q/Common/xserverMain.java#L29-L35

https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/7cc109f1cf27cfb4cf09729c18cca81706e8e01c/Q/Common/xclientMain.java#L28-L32

https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/7cc109f1cf27cfb4cf09729c18cca81706e8e01c/Q/Common/JSONParsingUtils.java#L101-L108

We have a method that gets the Client/Server configs from the JSON specs and we call this method within our programs to create servers/clients from the parsed configs.

Constructor for ServerConfig and ClientConfig takes in the configurations retrieved from JSON specs.(example below)
https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/7cc109f1cf27cfb4cf09729c18cca81706e8e01c/Q/Server/ServerConfig.java#L26

- enforces that each configuration specifies a fixed set of properties (no more, no less)

Constructor for RefereeConfig: state0 and configs are taken care of in the GameState
https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/7cc109f1cf27cfb4cf09729c18cca81706e8e01c/Q/Referee/RefereeConfig.java#L22
Constructor for ServerConfig:
https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/7cc109f1cf27cfb4cf09729c18cca81706e8e01c/Q/Server/ServerConfig.java#L26

Each configuration must have the data that we expect to parse from the JSON configs. If there is any more or less properties given to the configuration constructor than specified, it simply would not construct.

- supports the retrieval of properties 
https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/7cc109f1cf27cfb4cf09729c18cca81706e8e01c/Q/Server/ServerConfig.java#L50
Instead of having getters in the config classes, we use the builder pattern to use the configs to build a server or a referee directly, so the server or referee or the gamestate has access to its own configs/fields. We do not support the retrieval of properties directly from the configurations because it should not be public knowledge and getters are bad design.

https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/7cc109f1cf27cfb4cf09729c18cca81706e8e01c/Q/Referee/referee.java#L32-L36

https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/7cc109f1cf27cfb4cf09729c18cca81706e8e01c/Q/Common/game_state.java#L30-L37

https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/7cc109f1cf27cfb4cf09729c18cca81706e8e01c/Q/Server/server.java#L22-L28

https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/7cc109f1cf27cfb4cf09729c18cca81706e8e01c/Q/Client/client.java#L16-L19


- sets properties (what happens when the property shouldn't exist?) 

https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/7cc109f1cf27cfb4cf09729c18cca81706e8e01c/Q/Server/ServerConfig.java#L26-L45

Within the ServerConfig class and RefereeConfig class, we contract checks if the given properties are valid. If not, it throws an exception. Config classes should not set properties because user has only one chance to configure.

- unit tests for these pieces of functionality

We do not have unit tests for these pieces of functionality.

Explain how the server, referee, and scoring functionalities are abstracted
over their respective configurations.

Server takes in RefereeConfig that includes gameState and other referee-specific configs used to construct the referee.
https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/7cc109f1cf27cfb4cf09729c18cca81706e8e01c/Q/Server/server.java#L34-L51
Then, server runs existing game with the RefereeConfig's gamestate and the referee created using the RefereeConfig.
https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/7cc109f1cf27cfb4cf09729c18cca81706e8e01c/Q/Server/server.java#L71
Scoring functionalities and thus the QBonus and the Finishing bonus only appear in the game state included in the RefereeConfig.


For both our referee and gamestate(scoring), we have multiple constructors that set default values for configurations if the properties are missing. This way, we support our old functionality and the new ones. We do not have previous constructors for the server side, just a single one that expects all properties/configs to exist.

Does the server touch the referee or scoring configuration, other than
passing it on?

https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/7cc109f1cf27cfb4cf09729c18cca81706e8e01c/Q/Server/server.java#L34-L51

No, our server sets a RefereeConfig variable that describes the configs for both the referee and scoring and passes it on to the referee when creating a new referee to run a game.

Does the referee touch the scoring configuration, other than passing
it on?

https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/7cc109f1cf27cfb4cf09729c18cca81706e8e01c/Q/Referee/referee.java#L49-L65

https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/7cc109f1cf27cfb4cf09729c18cca81706e8e01c/Q/Server/server.java#L77-L79

No, our referee sets its own configurations once passed the referee config. The scoring config is set when our referee runs an existing game with a gamestate where we set the scoring configs.

The ideal feedback for each of these three points is a GitHub
perma-link to the range of lines in a specific file or a collection of
files.

A lesser alternative is to specify paths to files and, if files are
longer than a laptop screen, positions within files are appropriate
responses.

You may wish to add a sentence that explains how you think the
specified code snippets answer the request.

If you did *not* realize these pieces of functionality, say so.

