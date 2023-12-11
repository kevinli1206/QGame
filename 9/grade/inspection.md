Pair: mysterious-alligators \
Commit: [b5c8d2a231793bb68b92e444ffe42f486ef1fdc0](https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/tree/b5c8d2a231793bb68b92e444ffe42f486ef1fdc0) \
Self-eval: https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/87ed5e17353749a575245d77e175566794444842/9/self-9.md \
Score: 148/210 \
Grader: Vish Jeyaraman

#### [148/210pts] Program Inspection
1. [20/20pts] Helpful and accurate self-eval. 
2. [64/80pts] Proxy Player.
   - [10/10pts] accept *name* calls and return the name sent by the client.
   - [10/10pts] accept *setup* calls, turn them into JSON, get result ("void") in JSON.
   - [10/10pts] accept *take-turn* calls, turn them into JSON, receive a JSON ACTION, return it as an internal value.
   - [10/10pts] accept *new-tiles* calls, turn them into JSON, get result ("void") in JSON.
   - [10/10pts] accept *win* calls, turn them into JSON, get result ("void") in JSON
   - [5/15pts] constructor receive handles for sending/receiving over streams (to mock TCP).
     - Constructor should have stream handlers and not sockets 
   - [9/15pts] unit tests.
     - Missing. Partial credit for honesty. 
3. [54/70pts] Proxy Referee 
   - [10/10pts] call player *setup* when receive corresponding JSON expression, write the resut as JSON to the output stream.
   - [10/10pts] call player *take-turn* when receive corresponding JSON expression, write the resut as JSON to the output stream.
   - [10/10pts] call player *new-tiles* when receive corresponding JSON expression, write the resut as JSON to the output stream.
   - [10/10pts] call player *win* when receive corresponding JSON expression, write the resut as JSON to the output stream.
   - [5/15pts] constructor receive (1) a player and (2) handles for sending/receiving over streams.
     - Constructor should have stream handlers and not sockets 
   - [9/15pts] unit tests.
     - Missing. Partial credit for honesty. 
4. [10/40pts] Client-Server
   - [0/20pts] client wait or shut down gracefully when it cannot connect to the server.
      - Does not gracefully shutdown, throws exception
   - [10/20pts] abstract over the “wait for two periods” property of the server.
      - Hardcoded the wait period  
  

