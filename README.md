# Cygni task.
Simple REST API of a game Rock, Paper, Scissors. The rules are simple, best of 1 match wins. A tie is counted as a result, this means that the game does not need to be restarted in the event of a tie.

Examples of requests to the service are created with Postman and could be found here:
https://documenter.getpostman.com/view/2855941/2sA2rCVN92
## Requirements:
* No persistence mechanism is allowed. The entire process (state) must be kept in memory.
* The application does not need to support return matches.
* There needs to be no client implementation. (Type CLI, GUI).

## Example of a game flow:
1. Player 1 sends a request to create a new game and gets back a game ID from the server.
2. Player 1 sends ID to player 2 via any communication channel. (e.g. email, slack or fax)
3. Player 2 joins the game using ID.
4. Player 1 makes his move (Stone).
5. Player 2 makes his move (Scissors).
6. Player 1 checks the state of the game and discovers that he won.
7. Player 2 checks the state of the game and discovers that he lost.
## Example API design
   Below is an example of the endpoints the API could expose to a client:
* POST /api/games
* POST /api/games/{id}/join
* POST /api/games/{id}/move
* GET /api/games/{id} where id can be a UUID.
* GET /api/games/{id} Returns the current state of a given game with included attributes. Consider which attributes should be displayed to whom and when.
* POST /api/games Creates a new game. Enter player name in request-body:
``` 
{
"name": "Lisa"
}
```
* POST /api/games/{id}/join Joins a game with given ID. Enter player name in request-body:
``` 
{
 "name": "Pelle" 
}
``` 
* POST api/games/{id}/move Make a move. Enter name and traits in request-body:
``` 
{ 
"name": "Lisa", "move": "Rock" 
}
``` 

