package salvo.salvo;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    Integer score;
    Date finishDate;

    //@Json Ignore To prevent the endless loop!
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    //@Json Ignore To prevent the endless loop!
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Score(){}

    public Score(Player player, Game game, Integer score, Date finishDate) {
        this.player = player;
        this.game = game;
        this.score = score;
        this.finishDate = finishDate;
    }


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


}
