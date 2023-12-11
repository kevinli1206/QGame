The commit we tagged for your submission is b5c8d2a231793bb68b92e444ffe42f486ef1fdc0.
**If you use GitHub permalinks, they must refer to this commit or your self-eval will be rejected.**
Navigate to the URL below to create permalinks and check that the commit hash in the final permalink URL is correct:

https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/tree/b5c8d2a231793bb68b92e444ffe42f486ef1fdc0

## Self-Evaluation Form for Milestone 9

Indicate below each bullet which file/unit takes care of each task.

For `Q/Server/player`,

- explain how it implements the exact same interface as `Q/Player/player`

https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/b5c8d2a231793bb68b92e444ffe42f486ef1fdc0/Q/Server/player.java#L26-L29

Both Q/Server/player and Q/Player/player implement the IPlayer interface.

- explain how it receives the TCP connection that enables it to communicate with a client

https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/b5c8d2a231793bb68b92e444ffe42f486ef1fdc0/Q/Server/player.java#L59-L75

Q/Server/player receives the Socket that represents the connection between itself and a client as input to its constructor. When a client connects on a port, the Socket is connected to that port and passed to the constructor of Q/Server/player.

- point to unit tests that check whether it writes (proper) JSON to a mock output device

We do not have unit tests for checking writing proper JSON to an output device.

For `Q/Client/referee`,

- explain how it implements the same interface as `Q/Referee/referee`

Q/Client/referee does not implement the same interface as Q/Referee/referee. To our understanding, Q/Client/Referee simply serves as a communication layer between the proxy player and the real player. It would not make sense for the Client referee to run a game with a list of players and/or keep track of some gamestate.

- explain how it receives the TCP connection that enables it to communicate with a server

https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/b5c8d2a231793bb68b92e444ffe42f486ef1fdc0/Q/Client/referee.java#L45-L62

Q/Client/referee also receives the Socket that represents the connection between itself and a server as input to its constructor. When a client makes a connection on a port to the server, the Socket is connected to that port and passed to the constructor of Q/Client/referee.

- point to unit tests that check whether it reads (possibly broken) JSON from a mock input device

We do not have unit tests for checking reading JSON from an input device.

For `Q/Client/client`, explain what happens when the client is started _before_ the server is up and running:

- does it wait until the server is up (best solution)

No


- does it shut down gracefully (acceptable now, but switch to the first option for 10)

https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/b5c8d2a231793bb68b92e444ffe42f486ef1fdc0/Q/Client/client.java#L30-L38

https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/b5c8d2a231793bb68b92e444ffe42f486ef1fdc0/Q/Common/xclientMain.java#L14-L28

Right now, we create a new Socket with the given host and port number. If no server is created yet that is listening for a connection, then an exception is thrown in Q/Client/client and caught in xClientMain which is our script that shuts down.

For `Q/Server/server`, explain how the code implements the two waiting periods. 

https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/b5c8d2a231793bb68b92e444ffe42f486ef1fdc0/Q/Server/server.java#L42-L59

We run a connection to findPlayers twice and return if we have a minimum number of players connected after each round. We will abstract this functionality out in the future so it's not hardcoded to always run twice.

The ideal feedback for each of these three points is a GitHub
perma-link to the range of lines in a specific file or a collection of
files.

A lesser alternative is to specify paths to files and, if files are
longer than a laptop screen, positions within files are appropriate
responses.

You may wish to add a sentence that explains how you think the
specified code snippets answer the request.

If you did *not* realize these pieces of functionality, say so.

