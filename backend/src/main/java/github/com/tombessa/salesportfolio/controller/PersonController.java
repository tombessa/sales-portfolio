package github.com.tombessa.salesportfolio.controller;

import github.com.tombessa.salesportfolio.model.Person;
import github.com.tombessa.salesportfolio.model.dto.PersonDto;
import github.com.tombessa.salesportfolio.service.PersonService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/person")
@Tag(name = "personController")
@SecurityRequirement(name = "javainuseapi")
public class PersonController {

    private final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private final MapperFacade mapperFacade;
    private final PersonService personService;

    public PersonController(PersonService personService) {
        mapperFactory.classMap(Person.class, PersonDto.class);
        this.mapperFacade = mapperFactory.getMapperFacade();
        this.personService = personService;
    }

    @GetMapping("/")
    public List<PersonDto> listAll() {
        return mapperFacade.mapAsList(personService.findAll(), PersonDto.class);
    }

}
