package github.com.tombessa.salesportfolio.controller;

import github.com.tombessa.salesportfolio.config.SecurityConfiguration;
import github.com.tombessa.salesportfolio.enums.UserAccessStatusEnum;
import github.com.tombessa.salesportfolio.exception.BusinessException;
import github.com.tombessa.salesportfolio.model.Person;
import github.com.tombessa.salesportfolio.model.UserAccess;
import github.com.tombessa.salesportfolio.model.dto.PersonDto;
import github.com.tombessa.salesportfolio.repository.PersonRepository;
import github.com.tombessa.salesportfolio.service.PersonService;
import github.com.tombessa.salesportfolio.util.Constants;
import github.com.tombessa.salesportfolio.util.Messages;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/person")
@Tag(name = "personController")
@SecurityRequirement(name = "javainuseapi")
public class PersonController {

    private final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private final MapperFacade mapperFacade;
    private final PersonService personService;
    private final PersonRepository personRepository;
    private SecurityConfiguration securityConfiguration;
    private Messages messages;

    public PersonController(PersonService personService, PersonRepository personRepository, SecurityConfiguration securityConfiguration, Messages messages) {
        this.personRepository = personRepository;
        this.securityConfiguration = securityConfiguration;
        this.messages = messages;
        mapperFactory.classMap(Person.class, PersonDto.class);
        mapperFactory.classMap(PersonDto.class, Person.class);
        mapperFactory.classMap(github.com.tombessa.salesportfolio.model.dto.create.client.query.PersonDto.class, Person.class);
        mapperFactory.classMap(Person.class, github.com.tombessa.salesportfolio.model.dto.create.client.query.PersonDto.class);
        this.mapperFacade = mapperFactory.getMapperFacade();
        this.personService = personService;
    }

    @GetMapping("/")
    @Tag(name="This endpoint is responsible to get a list of persons at system")
    public List<github.com.tombessa.salesportfolio.model.dto.create.client.query.PersonDto> listAll() {
        return mapperFacade.mapAsList(personService.findAll(),
                github.com.tombessa.salesportfolio.model.dto.create.client.query.PersonDto.class);
    }

    public github.com.tombessa.salesportfolio.model.dto.create.client.query.PersonDto changeAccess(Person person){
        Optional<UserAccess> userAccess = person.getUserAccessList().stream()
                .filter(t -> (t.getStatus().equals(UserAccessStatusEnum.PENDING)||t.getStatus().equals(UserAccessStatusEnum.ACTIVE))).findAny();

        if(!userAccess.isPresent()){
            throw new BusinessException(this.messages.get(Constants.EXCEPTION_USERACCESS_REQUIRED));
        }
        try{
            this.securityConfiguration.changePassword(userAccess.get().getLogin(),
                    userAccess.get().getPassword());
        }catch (Exception e) { new ResourceNotFoundException(this.messages.get(Constants.EXCEPTION_USERACCESS_REQUIRED));}

        return this.mapperFacade.map(person, github.com.tombessa.salesportfolio.model.dto.create.client.query.PersonDto.class);
    }

    @PutMapping
    @Tag(name="This endpoint is responsible to add a person at system")
    public github.com.tombessa.salesportfolio.model.dto.create.client.query.PersonDto createClientAccess(@RequestBody @Valid PersonDto personDto){
        Person person = this.mapperFacade.map(personDto, Person.class);
        person = this.personService.create(person);
        return this.changeAccess(person);
    }

    @PatchMapping
    @Tag(name="This endpoint is responsible to update a person at system")
    public github.com.tombessa.salesportfolio.model.dto.create.client.query.PersonDto keepClientAccess(@RequestBody @Valid PersonDto personDto){
        Person person = this.mapperFacade.map(personDto, Person.class);
        person = this.personService.update(person);
        return this.changeAccess(person);
    }
}
