package github.com.tombessa.salesportfolio.service.impl;

import github.com.tombessa.salesportfolio.model.*;
import github.com.tombessa.salesportfolio.repository.PersonRepository;
import github.com.tombessa.salesportfolio.service.PersonService;
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

    private Person keepRelationShip(Person person){
        for(UserAccess userAccess: person.getUserAccessList()){
            userAccess.setPeople(person);
        }
        for(Client client: person.getClientList()){
            client.setPeople(person);
        }
        for(EntityAddress entityAddress: person.getEntityAddressList()){
            entityAddress.setPeople(person);
        }
        for(Supplier supplier: person.getSupplierList()){
            supplier.setPeople(person);
        }
        for(EntityDocument entityDocument: person.getEntityDocumentList()){
            entityDocument.setPeople(person);
        }
        return person;
    }

    @Override
    public Person create(Person person) {
        person = keepRelationShip(person);
        return this.personRepository.save(person);
    }

    @Override
    public Person update(Person person) {
        person = keepRelationShip(person);
        return this.personRepository.save(person);
    }
}
