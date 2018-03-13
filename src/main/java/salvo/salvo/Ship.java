package salvo.salvo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//Don't forget to put the entity relation
@Entity
public class Ship {

    //Same goes for the ID and GeneratedValue relations
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;



    //Element collection is used to create lists of embeddable objects.
    // An embeddable object is data intended for use only in the object containing it.
    //The locations are a list of three "coordinate-fields" like A1,A2,A3
    @ElementCollection
    @Column(name="location")
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
    public Ship() {}


    //Ships are of "Type" and the location is a "List" of coordinates like A1,A2,A3
    public Ship(Type type, List locations) {
        this.type = type;
        this.locations = locations;

    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {

    }

    //An enum type is a special data type that enables for a variable to be a set of predefined constants.
    //In this case the 5 different types of ships --> Command Line Runner in Salvo Application
    public enum Type {
        Carrier,
        Battleship,
        Submarine,
        Destroyer,
        PatrolBoat,

    }

    //To determine "type" like 'private long id'
    private Type type;

}