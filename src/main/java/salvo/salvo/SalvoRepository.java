package salvo.salvo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

//This makes the Repository a Restful API so the information can be transmitted in http format
@RepositoryRestResource
public interface SalvoRepository extends JpaRepository<Salvo, Long> {



}
