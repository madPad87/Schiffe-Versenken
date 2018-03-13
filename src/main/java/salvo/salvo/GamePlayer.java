package salvo.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    Date joinDate;

    //The two colums ManyToOne und JoinColumn indicate the GamePlayer class to be the owner of this
    //ManyToMany relationship with the Player and Game Class
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    private Set<Salvo> salvoes;


    public Set<Ship> getShips() {
        return ships;
    }

    public void setShips(Set<Ship> ships) {
        this.ships = ships;
    }
    //Again an One to Many Relation to ships: One Gameplayer has many ships!
    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER)
    private Set<Ship> ships = new HashSet<Ship>();

    public void addShip(Ship ship) {
        ship.setGamePlayer(this);
        ships.add(ship);
    }


    public GamePlayer() { }

    //Here the different attributes for the class GamePlayer are determined
    //These are all to be found in the Command line runner when instantiating new gameplayers
    public GamePlayer(Date joinDate, Game game, Player player) {
        this.joinDate = joinDate;
        this.game = game;
        this.player = player;
        this.ships = ships;
    }
    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }


    //@Json Ignore To prevent the endless loop!
    @JsonIgnore
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


    public long getId() {
        return id;
    }


    public Set<Salvo> getSalvoes() {
        return salvoes;
    }
}