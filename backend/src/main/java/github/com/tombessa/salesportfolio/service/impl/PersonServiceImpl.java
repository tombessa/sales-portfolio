package github.com.tombessa.salesportfolio.service.impl;

import github.com.tombessa.salesportfolio.model.Person;
import github.com.tombessa.salesportfolio.repository.PersonRepository;
import github.com.tombessa.salesportfolio.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> findAll() {
        return this.personRepository.findAll();
    }
}
