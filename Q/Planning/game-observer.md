Game Observer Interface:

fields: 
- List<PublicInfo> recordOfPublicInfo represents the list of public infos about the game (which consists of the current map, ref 
tiles remaining, the playerState and their scores).
- PublicInfo currentPublicInfo is the PublicInfo that the observers are viewing currently.

methods: 
- public void updatePublicInfo(PublicInfo) appends the current public info sent from the game state to the List<PublicInfo>.
- public void getNextPublicInfo() sets currentPublicInfo to the next publicInfo in recordOfPublicInfo.
- public void renderView() renders the current publicInfo as a graphical representation.

Protocol: 
- gameState: initializeGame() sets up the game by initializing the playerState and create starting map.
- gameState: sendPublicInfo() sends the current publicInfo to the observers.
- observer: updatePublicInfo(PublicInfo) receives the most recent publicInfo from the gameState.
- observer: getNextPublicInfo() to click to the next publicInfo.
- observer: renderView() renders a graphical view of the publicInfo.
- this continues until the game ends.