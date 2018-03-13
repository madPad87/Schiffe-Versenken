
package salvo.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


//The Restcontroller is responsible for managing Data
//coming from and flowing to the database
@RestController
@CrossOrigin
@RequestMapping("/api")
public class SalvoController {

    //WIRE THE CONTROLLER TO THE REPOSITORY
    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;
    //CALL METHOD WHEN GET REQUEST FOR URL /API
    public List<Game> getAllGames() {

        //RETURN A LIST OF GAMES INSTANCES
        return gameRepository.findAll();
    }
    public List<Player> getAllPlayers() {
        //RETURN A LIST OF GAMES INSTANCES
        return playerRepository.findAll();
    }
    @RequestMapping("/games")
    public Map<String, Object> getAllGamesDTO(Authentication authentication){
        Map<String, Object> gamesDto = new HashMap<>();
        List<Object> games = new ArrayList<>();
        getAllGames().forEach(game-> {
            games.add(makeGameDTO(game));
        });
        gamesDto.put("user", makeCurrentDto(authentication));
        gamesDto.put("games", games);

        return gamesDto;
    }
        //This is the method for determining the logged In player.
        //this player's games are shown in the /game url
        public Map<String, Object> makeCurrentDto(Authentication authentication) {
        Map<String, Object> current = new LinkedHashMap<>();
        //If that player is authenticated through the Spring Authentication library
        if(authentication != null) {
            Player player =  playerRepository.findByUserName(authentication.getName());
            current.put("username", player.getUserName());
            current.put("id", player.getId());

            return current;

        } else {
            return current;
        }


        }

        //Create a new Method for starting a new game! It gets saved in api/
        @RequestMapping("/games/new")
        public ResponseEntity<Object> createNewGame(Authentication authentication) {
        //Only possible if player is logged In!
        if(authentication == null) {
            return new ResponseEntity<>("Please LogIn", HttpStatus.UNAUTHORIZED);
        }
        //Get the player and create a new game with own creation Date (New Game)
        Player player = playerRepository.findByUserName(authentication.getName());
        //initialize  new Game!!
        Game game = new Game(new Date());
        //Create a new GamePlayer with the three premises (Date Game and Player) from the GamePlayer Constructor in its class
        GamePlayer gamePlayer = new GamePlayer(new Date(),game, player);
        //Save both
        gameRepository.save(game);
        gamePlayerRepository.save(gamePlayer);

        //As a response the gamePlayer is given!
        Object response = gamePlayer;

        //Return an new Response Entity with the status created if it worked out
        return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        //Creating a method for joining a game. First, get the List of all joinable games!
    //In the Java script only show joinable games!!! new!
        @RequestMapping("games/join")
        public ResponseEntity<Map<String, Object>> joinGame(Authentication authentication) {
        //find the player that wants to enter by Username (e.g jack bauer)
            Player player = playerRepository.findByUserName(authentication.getName());
            //Get a list of all games from the repository -> findAll()
            List<Game> gameList = new ArrayList<>();
            gameList = gameRepository.findAll();
            // and put each game for which the if statements hold in a list, the JoinableGamesList
            List<Object> joinableGameList = new ArrayList<>();
            gameList.forEach(game -> {
                if( !game.isFinished() && game.getGamePlayers().size() == 1 && game.getSinglePlayer() != player) {
                    joinableGameList.add(makeGameDTO(game));
                }
            });
            //the joinable Games List which is in turn later put into the response!
            Map<String, Object> response = new HashMap<>();
            response.put("joinableGames", joinableGameList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        //If a player joins a Game, a new gamePlayer has to be created
        @RequestMapping(path = "games/join/{gameId}")
        //The @path variable again to transport the player's info in the URL
        public ResponseEntity<Object> joinedGames(Authentication authentication, @PathVariable Long id) {
            //find the joining player by username
            Player player = playerRepository.findByUserName(authentication.getName());
            //find the game he enters
            Game game = gameRepository.findOne(id);
            //Create new gamePlayer
            GamePlayer gamePlayer = new GamePlayer(new Date(), game, player);
            //save the new gameplayer
            gamePlayerRepository.save(gamePlayer);

            return new ResponseEntity<>(gamePlayer.getId(), HttpStatus.OK);
        }

        //Method to get the List that are continuable --> the list of the games
    //the current player has started but not finished
    @RequestMapping(path="/games/continue")
    public Map<String, Object> getContinueGamesList(Authentication authentication){
        //Find the player by username
        Player player = playerRepository.findByUserName(authentication.getName());
        //make a map of the games he started
        Map<String, Object> gamesDTO = new HashMap<>();
        List<Object> response = new ArrayList<>();

        player.getGamePlayers().forEach(gamePlayer-> {
            Game game = gamePlayer.getGame();

            if(!game.isFinished()) {
                response.add(makeGameDTO(game));
            }
        });

        //insert the list of reenterable games in the dto!
        gamesDTO.put("games", response);

        return gamesDTO;
    }



    @RequestMapping("/leaderboard")
    public Map<String, Object> makeLeaderBoardDTO() {
        Map<String, Object> leaderBoard = new LinkedHashMap<>();
        leaderBoard.put("scores", makeScoreDTO());

        return leaderBoard;
    }


    //This method is for handling a signup --> the creation of a new player
    //Request params are the inputs that are required to create a new user
    //It checks in the api/player and saves in the api/player class
    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<String> createUser(@RequestParam String username, @RequestParam
                                                          String password) {
        //This checks what was entered in the fields
        if (username.isEmpty()) {
            return new ResponseEntity<>("Error", HttpStatus.FORBIDDEN);
        }
        //If a user with the inputs already exists
        else if(playerRepository.findByUserName(username) != null) {
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        }

        //If inputs are correct and no user with the inputs already exists
        playerRepository.save(new Player(username, password));
        return new ResponseEntity<>("Name added" , HttpStatus.CREATED);
    }





    //This is task three which starts a new api(browser address to display game_view)
    @RequestMapping("/game_view/{id}")
    //@PathVariable shows/injects the id in the url --> game_view/1 for gamePLayer 1
    public Map<String, Object> getGameViewById(@PathVariable Long id) {

        //Here we specify that we want to find the game_view for one of the gamePlayers
        // (selected by id) --> findOne(id);
        GamePlayer gamePlayer = gamePlayerRepository.findOne(id);
        // each gamePlayer has a set of ship, consisting of three. So we get the ships from the gamePlayer
        Set<Ship> ships = gamePlayer.getShips();
        //And we make a list out of this set
        List<Object> shipDTO = ships.stream().map(ship -> makeShipDTO(ship)).collect(Collectors.toList());

        //So here we get the map for game_view consisting of "game" which we had in the task before and add
        //the ships
        Map<String, Object> gameViewDTO = new LinkedHashMap<String, Object>();

        //We call the method makeGameDTO and shipDTO and define them further down
        gameViewDTO.put("game", makeGameDTO(gamePlayer.getGame()));
        gameViewDTO.put("ships", shipDTO);
        gameViewDTO.put("salvoes", makeSalvoesDTO(gamePlayer.getGame()));



        //We need to return this Map
        return gameViewDTO;

    }


    //Here we define the method makeGameDTO which was called above
    //Because now the gameMap in turn contains the gamePlayerMap which in turn contains the playerMap
    private Map<String, Object> makeGameDTO(Game game) {

        Map gameDTO = new LinkedHashMap<>();

        //In the gameMap we call the getGamePlayerList cause game contains the gamePlayers
        gameDTO.put("id", game.getId());
        gameDTO.put("created", game.getCreationDate());
        gameDTO.put("gamePlayers", getGamePlayerList(game));

        gameDTO.put("gameOver", game.isFinished());


        return gameDTO;

    }


    private List<Map> getGamePlayerList(Game game) {
        Set<GamePlayer> gamePlayers = game.getGamePlayers();

        //Here we have a list of gameplayers and for each of the elements we create a map
        //the method to do that we call here in the foreach loop
        List<Map> gamePlayersList = new ArrayList<>();

        gamePlayers.forEach(gamePlayer -> {
            gamePlayersList.add(makeGamePlayerDTO(gamePlayer));
        });
        return gamePlayersList;

    }

    //Here we make the maps for each game player that get inserted above
    private Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer) {
        Map<String, Object> gamePlayerDTO = new LinkedHashMap<>();
        gamePlayerDTO.put("id", gamePlayer.getId());

        gamePlayerDTO.put("player", makePlayerDTO(gamePlayer.getPlayer()));

        return gamePlayerDTO;
    }

    //Again we make  map for each player in the respective gameplayer
    private Map<String, Object> makePlayerDTO(Player player) {
        Map<String, Object> playerDTO = new LinkedHashMap<>();
        playerDTO.put("id", player.getId());
        playerDTO.put("username", player.getUserName());


        return playerDTO;
    }

    //Here's the shipDTO we called all in the beginning!
    private Map<String, Object> makeShipDTO(Ship ship) {
        //Here we specify which attributes we want to show in the map. Hash Map brings the
        // keyValue pairs together
        Map shipDTO = new LinkedHashMap<>();
        shipDTO.put("type", ship.getType());
        shipDTO.put("location", ship.getLocations());

        return shipDTO;

    }
    //Return List of Salvoes DTOs
    private List<Object> makeSalvoesDTO(Game game) {
        //initialize an empty String to collect SalvoesDTO
        List<Object> salvoesDTO = new ArrayList<>();

        //Getting the Set of Gameplayers from the game
        Set<GamePlayer> gamePlayers = game.getGamePlayers();

        //Loop through each of the game players
        gamePlayers.forEach(gamePlayer -> {
            //Initialize an empty map to collect the Id of the gameplayer as key
            //and the relative salvoes as DTOs
            Map<String, Object> gamePlayerSalvoDTO = new LinkedHashMap<>();


            //Convert the gameplayer id as a string
            String gamePlayerID = String.valueOf(gamePlayer.getId());


            //Initialize an empty String for the Turns of the game
            List<Object> playerTurnList = new ArrayList<>();
            gamePlayerSalvoDTO.put("gamePlayerID", gamePlayerID);



            //Get the ET of Salvoes from the gameplayer
            Set<Salvo> gamePlayerSalvoes = gamePlayer.getSalvoes();


            //Loop through each gps's salvoes
            gamePlayerSalvoes.forEach(salvo -> {


                //Init empty map to collect the turn number as Key and the array
                //as locations value
                Map<String, Object> turnLocationDTO = new LinkedHashMap<>();


                //Convert turn number to string
                String turnNumberStr = String.valueOf(salvo.getTurn());
                turnLocationDTO.put(turnNumberStr, salvo.getLocations());


                //Add to the list of game turns the turnlocation
                playerTurnList.add(turnLocationDTO);
            });

            gamePlayerSalvoDTO.put("turns", playerTurnList);

            //Finally add to the list the dto of salvoes relative to the gamePlayer
            salvoesDTO.add(gamePlayerSalvoDTO);
        });
        return salvoesDTO;
    }
    public List<Object> makeScoreDTO(){
        List<Object> scoreListDTO = new ArrayList<>();
        getAllPlayers().forEach(player -> {
            scoreListDTO.add(calculateScoreDetails(player));
        });
        return scoreListDTO;
    }
    public Map<String, Object>  calculateScoreDetails(Player player){
        Set<Score> scores = player.getScores();
        Map<String, Object> scoreDetails = new LinkedHashMap<>();
        double total = 0;
        int won = 0;
        int lost = 0;
        int tied = 0;
        for (Score score: scores){
            total += score.getScore();
            player = score.getPlayer();
            if (score.getScore() == 1){
                won ++;
            }
            else if (score.getScore() == 0){
                lost ++;
            }
            else {
                tied ++;
            }
        };
        scoreDetails.put("userName", player.getUserName());
        scoreDetails.put("total", total);
        scoreDetails.put("won", won);
        scoreDetails.put("lost", lost);
        scoreDetails.put("tied", tied);
        return scoreDetails;
    }

    /*@RequestMapping(path="/games/continue/{gameId}")
    public ResponseEntity<Object> continueGame(Authentication authentication, @PathVariable Long gameId){

        Object response = new Object();
        Game game = gameRepository.getOne(gameId);
        Set<GamePlayer> thisGameGamePlayers = game.getGamePlayers();
        Player player = playerRepository.findByUserName(authentication.getName());

        for (GamePlayer gp : thisGameGamePlayers) {
            if( gp.getPlayer() == player){
                response = gp.getId();
            }
        }
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED );
    }*/


}


