package salvo.salvo;


//javax.persistence is a package. Entity has to be imported similar to angular
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.DoubleStream;

import static java.util.stream.Collectors.toList;

//@Entity: Annotation to define Player as a class and puts the data into a table in a database which
//can later be retrieved
@Entity
public class Player {


    //The Id is created automatically by spring
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String userName;
    private String password;


    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    //In order to relate to the manyToMany relationship with Player and GamePlayer where GamePlayer
    // is the owner of this relationship
    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    public void addGamePlayer(GamePlayer gameplayer) {
        gameplayer.setPlayer(this);
        gamePlayers.add(gameplayer);
    }

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    Set<Score> scores;

    public void addScore(Score score) {
        score.setPlayer(this);
        scores.add(score);
    }


    // for spring to know how to read the database like p = new Player --> p.setUserName("jack").... (as on blackboard)
    public Player() { }

    //attribute(s) of a Player
    public Player(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    //Every attribute needs a getter and a setter method!
    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }


    public long getId() {
        return id;
    }

    public Set<Score> getScores() {
        return scores;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
