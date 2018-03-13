/*

package salvo.salvo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
//SPRING CONTROLLER RETURNS DATA (JSON)
@RestController
@CrossOrigin
@RequestMapping("/api")
public class RubensController {
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
    public Map<String, Object> getAllGamesDTO(){
        Map<String, Object> gameAndScoresDto = new HashMap<>();
        List<Object> games = new ArrayList<>();
        getAllGames().forEach(game-> {
            games.add(makeGameDTO(game));
        });
        gameAndScoresDto.put("games", games);
        gameAndScoresDto.put("scores", makeScoreDTO());
        return gameAndScoresDto;
    }
    @RequestMapping("/game_view/{id}")
    public Map<String, Object> getGameViewById(@PathVariable Long id){
        //GET THE GAMEPLAYER FROM THE ID OF THE URL
        GamePlayer gamePlayer = gamePlayerRepository.findOne(id);
        //INITIALIZE THE EMPTY MAP FOR THE GAMEVIEWDTO
        Map<String, Object> gameViewDTO = new LinkedHashMap<String, Object>();
        gameViewDTO.put("game",makeGameDTO(gamePlayer.getGame()));
        gameViewDTO.put("ships", makeShipsDTO(gamePlayer.getShips()));
        gameViewDTO.put("salvoes", makeSalvoesDTO(gamePlayer.getGame()));
        return gameViewDTO;
    }
    //FOR EVERY GAME RETURN A MAP WITH THE GAME ID AND CREATION DATE
    private Map<String, Object> makeGameDTO(Game game) {
        Map<String, Object> gameDTO = new LinkedHashMap<String, Object>();
        gameDTO.put("id", game.getId());
        gameDTO.put("created", game.getCreationDate());
        //FOLLOW UP THE NESTED LOOP FROM MAKEGAMEDTO --> getGamePlayersList --> makeGamePlayerDTO
        gameDTO.put("gamePlayers", getGamePlayerList(game));
        gameDTO.put("scores", game.getScore());
        gameDTO.put("gameOver", getGameStatus(game));
        return gameDTO;
    }
    public Boolean getGameStatus(Game game){
        Set<Score> scores = game.getScore();
        Boolean gameOver = false;
        if (scores.size() != 0){
            gameOver = true;
        }
        return gameOver;
    }
    //FOLLOW UP THE NESTED LOOP FROM MAKEGAMEDTO --> getGamePlayersList --> makeGamePlayerDTO
    //RETURN A MAP OF GAMEPLAYERS DTO FOR EVERY GAME
    private List<Map> getGamePlayerList(Game game){
        //GET THE LIST OF GAME PLAYERS FOR EACH GAME
        Set<GamePlayer> gamePlayers = game.getGamePlayers();
        //INITIALIZE A NEW ARRAY
        List<Map> gamePlayersList = new ArrayList<>();
        //FOR EVERY ITEM IF GAMEPLAYERS FILL THE GAMEPLAYERLIST WITH GAME PLAYERS DTO
        gamePlayers.forEach(gamePlayer -> {
            //FOLLOW UP THE NESTED LOOP FROM MAKEGAMEDTO --> getGamePlayersList --> makeGamePlayerDTO --> MAKEGAMEPLAYERDTO
            gamePlayersList.add(makeGamePlayerDTO(gamePlayer));
        });
        return gamePlayersList;
    }
    //TRANSFORM THE GAME PLAYER IN DTO
    private Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer) {
        Map<String, Object> gamePlayerDTO = new LinkedHashMap<String, Object>();
        gamePlayerDTO.put("id", gamePlayer.getId());
        //FOLLOW UP THE NESTED LOOP FROM MAKEGAMEDTO --> getGamePlayersList --> makeGamePlayerDTO --> MAKEGAMEPLAYERDTO --> MAKEPLAYERDTO
        gamePlayerDTO.put("player", makePlayerDTO(gamePlayer.getPlayer()));
        return gamePlayerDTO;
    }
    //FOLLOW UP THE NESTED LOOP FROM MAKEGAMEDTO --> getGamePlayersList --> makeGamePlayerDTO --> MAKEGAMEPLAYERDTO --> MAKEPLAYERDTO
    //TRANSFORM THE PLAYER IN DTO
    private Map<String, Object> makePlayerDTO(Player player){
        Map<String, Object> playerDTO = new LinkedHashMap<String, Object>();
        playerDTO.put("id", player.getId());
        playerDTO.put("userName", player.getUserName());
        return playerDTO;
    }
    //CREATE THE LIST FOR THE SHIPDTO OBJECTS
    private List<Object> makeShipsDTO(Set<Ship> ships){
        //INITIALIZE AN EMPTY LIST THAT WILL BE FILLED WITH THE DTO FOR EVERY SHIPS
        List<Object> shipsDTO = new ArrayList<>();
        //INITIALIZE AN EMPTY MAP THAT WILL BE NEEDED FOR A SHIP DTO
        //FOR EVERY SHIP IN THE SET COMING FROM THE ARGUMENTS
        //ADDS TO THE MAP OF THE DTO OF A SHIP A TYPE AND THE LOCATIONS ARRAY
        //FINALLY IS GONNA ADD TO THE LIST ONE OBJECT FOR SHIP
        ships.forEach(one -> {
            Map<String, Object> shipDTO = new LinkedHashMap<>();
            shipDTO.put("type", one.getType());
            shipDTO.put("location", one.getLocations());
            shipsDTO.add(shipDTO);
        });
        return shipsDTO;
    }
    //METHOD THAT RETURNS A LIST OF SALVOES DTO STARTING FOR A GAME
    private List<Object> makeSalvoesDTO(Game game) {
        //INITIALIZE AN EMPTY STRING TO COLLECT THE SALVOESDTO
        List<Object> salvoesDTO = new ArrayList<>();
        //GETTING THE SET OF GAMEPLAYERS FROM THE GAME
        Set<GamePlayer> gamePlayers = game.getGamePlayers();
        //LOOP THROUGH EACH OF THE GAMEPLAYERS
        gamePlayers.forEach(gamePlayer -> {
            //INITIALIZE AN EMPTY MAP TO COLLECT THE ID OF THE GP AS A KEY
            // AND THE RELATIVE SALVOES AS A DTO
            Map<String, Object> gamePlayerSalvoDTO = new LinkedHashMap<>();
            //CONVERT THE GAMEPLAYERID AS A STRING
            String gamePlayerID = String.valueOf(gamePlayer.getId());
            //INITIALIZE AN EMPTY STRING FOR THE TURNS OF THE GAME
            List<Object> playerTurnList = new ArrayList<>();
            gamePlayerSalvoDTO.put("gamePlayerID", gamePlayerID);
            gamePlayerSalvoDTO.put("turns", playerTurnList);
            //GET THE ET OF SALVOES FROM THE GAMEPLAYER
            Set<Salvo> gamePlayerSalvoes = gamePlayer.getSalvoes();
            //LOOP THROUGH EACH GP SALVOES
            gamePlayerSalvoes.forEach(salvo -> {
                //INITIALIZE EMPTY MAP TO COLLECT THE TURN NUMBER AS KEY
                //AND THE ARRAY OF LOCATIONS AS VALUE
                Map<String, Object> turnLocationDTO = new LinkedHashMap<>();
                //CONVERT THE TURN NUMBER AS A STRING
                String turnNumberStr = String.valueOf(salvo.getTurn());
                turnLocationDTO.put(turnNumberStr, salvo.getLocations());
                //ADD TO THE LIST OF GAME TURNS THE TURNLOCATION DTO
                playerTurnList.add(turnLocationDTO);
            });
            //FINALLY ADD TO THE LIST THE DTO OF SALVOES RELATIVE TO THE GAMEPLAYER
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
}

*/
