package github.com.tombessa.salesportfolio.service;

import github.com.tombessa.salesportfolio.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> findAll();
}
