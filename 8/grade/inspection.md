Pair: mysterious-alligators \
Commit: [9773f24d61b4b081b2ee8c05649f074429bbabfd](https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/tree/9773f24d61b4b081b2ee8c05649f074429bbabfd) \
Self-eval: https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/b0ffb265eea6493920db3c72999d46255371a7d5/8/self-8.md \
Score: 256/315 \
Grader: Derek Leung


**interact: [116/150] points**

- [20/20] points for a GUI that informs users of how to navigate, how to save states;

- [10/10] points for the ability to go back and forth in the sequence of states;

- [6/10] points for images that include (1) maps, (2) player tiles, (3) scores; -- no player tiles

- [10/10] points for for saving a state properly in a file; make sure the file is JSON;

- [10/10] points for gracefully dealing with canceled attempts to a state via a dialog (no crash). Ideally, you should be able to just continue interacting with the observer’s view.

- [30/30] points for generating the images in Tmp/ (no partial credit);

- [30/30] points for generating 0.png ... 2.png (10 points partial credit);

- [0/30] points for conveying the same information as Matthias' screenshots for the observer. -- no player tiles or tile bag in images



**git-inspection: [60/60] points**

- [60/60] points, if they did not incorporate code from the wrong code base -- you seem to be porting your whole codebase over in https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/commit/776d3128f02f9a0b168a7b74e37882d850d7cb83

**inspect: [70/90] points**

- [20/20] points, for accurate self eval

Inspect the referee for its wiring to the observer:

- [6/10] points for not linking to the concrete observer class/module; -- 60% for admitting you didn't 

- [10/10] points for a single point of control from which the referee informs an observer;

- [10/10] points for ensuring that the observer is handed every state exactly once

an informative purpose statement that brings across:

[12/20] points for how the observer works -- 60% for admitting you didn't 

[12/20] points for how its view works. -- 60% for admitting you didn't 


**design: [10/15] points**

[5/5] points for showing that each call from the referee to a player goes across “the wire” via a remote proxy;

[0/5] points for pointing to JSON representations for each call: general shape of calls, its arguments, and results;

[5/5] points for specifying how the client-players sign up with our server.

The codebase they inherited is [stoic-eagles](https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles)
The codebase they should NOT use is [zesty-elephants](https://github.khoury.northeastern.edu/CS4500-F23/zesty-elephants)

Contact information: Kevin Li <li.kevin1@northeastern.edu>, Haiyi Jiang <jiang.hai@northeastern.edu>
