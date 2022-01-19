package github.com.tombessa.salesportfolio.repository;

import github.com.tombessa.salesportfolio.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {

}
