package salvo.salvo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//Don't forget to put the entity relation
@Entity
public class Salvo {

    //Same goes for the ID and GeneratedValue relations
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    Integer turn;


    //Element collection is used to create lists of embeddable objects.
    // An embeddable object is data intended for use only in the object containing it.
    //The locations are a list of three "coordinate-fields" like A1,A2,A3
    @ElementCollection
    @Column(name = "locations")
    private List<String> locations = new ArrayList<>();


    //So here the locations are again a list not of type e.g string
    public List<String> getLocations() {
        return locations;
    }

    //again, location is a list
    public void setLocations(List<String> locations) {
        this.locations = locations;
    }


    //This shows the Many to one relation btw Gameplayer and Ships
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }


    //From here on the "classic" way of defining the class
    public Salvo() {
    }


    public Salvo(GamePlayer gamePlayer, List<String> salvoes, Integer turn) {
        this.locations = salvoes;
        this.turn = turn;
        this.gamePlayer = gamePlayer;


    }


    public Integer getTurn() {
        return turn;
    }

    public void setTurn(Integer turn) {
        this.turn = turn;
    }
}


