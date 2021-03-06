package salvo.salvo;


//Again we have to import the packages like in Angular
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


//This makes the Repository a Restful API so the information can be transmitted in http format
@RepositoryRestResource
public interface ScoreRepository extends JpaRepository<Score, Long> {


}
