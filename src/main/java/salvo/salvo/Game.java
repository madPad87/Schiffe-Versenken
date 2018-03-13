package salvo.salvo;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    Date creationDate;


    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;


    public void addGamePlayer(GamePlayer gameplayer) {
        gameplayer.setGame(this);
        gamePlayers.add(gameplayer);
    }



    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    Set<Score> scores;


    public void addScore(Score score) {
        score.setGame(this);
        scores.add(score);
    }


    public Game() { }

    public Game(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Game(String creationdate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }


    public long getId() {
        return id;
    }


    public Set<Score> getScore() {
        return scores;
    }

    public Player getSinglePlayer() {
        return this.getGamePlayers().iterator().next().getPlayer();
    }

    public boolean isFinished() {
        return (this.getScore().size() > 0);
    }
}


