package github.com.tombessa.salesportfolio.init;

import github.com.tombessa.salesportfolio.enums.AddressStatusEnum;
import github.com.tombessa.salesportfolio.enums.PersonStatusEnum;
import github.com.tombessa.salesportfolio.enums.RoleStatusEnum;
import github.com.tombessa.salesportfolio.enums.UserAccessStatusEnum;
import github.com.tombessa.salesportfolio.model.*;
import github.com.tombessa.salesportfolio.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
public class DbInitializer implements CommandLineRunner {
    private RoleRepository roleRepository;
    private PersonRepository personRepository;

    SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");

    public DbInitializer(RoleRepository roleRepository, PersonRepository personRepository) {
        this.roleRepository = roleRepository;
        this.personRepository = personRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        generateRole();
        generatePerson();
    }

    private void generatePerson() throws ParseException {
        Role administration = this.roleRepository.findByNameAndStatus("Administration", RoleStatusEnum.ACTIVE).get();
        Role salesRep = this.roleRepository.findByNameAndStatus("Sales Representation", RoleStatusEnum.ACTIVE).get();
        Role AcquisitionRep = this.roleRepository.findByNameAndStatus("Acquisition Representation", RoleStatusEnum.ACTIVE).get();
        List<Person> personList = new ArrayList<>();
        List<UserAccess> userAccessList = new ArrayList<>();
        List<EntityAddress> entityAddressList = new ArrayList<>();
        userAccessList.add(UserAccess.builder()
                .login("sales")
                .password("sales")
                .status(UserAccessStatusEnum.ACTIVE)
                .role(administration)
                .build());
        entityAddressList.add(EntityAddress.builder()
                .address(Address.builder()
                        .addressLine1("Line1")
                        .addressLine2("Line2")
                        .addressLine3("Line3")
                        .addressLine4("Line4")
                        .city("Miami")
                        .state("FL")
                        .zipcode("55432")
                        .country("USA")
                        .status(AddressStatusEnum.ACTIVE)
                        .build())
                .build());
        personList.add(Person.builder()
                .name("Admin Name")
                .birthdate(df.parse("16/02/1987"))
                .cellPhone("+5521981234455")
                .otherPhone("+12239875566")
                .email("teste@gmail.com")
                .status(PersonStatusEnum.ACTIVE)
                .userAccessList(userAccessList)
                .entityAddressList(entityAddressList)
                .build()
        );
        entityAddressList.forEach(t -> t.setPeople(personList.get(0)));
        userAccessList.forEach(t -> t.setPeople(personList.get(0)));
        this.personRepository.saveAll(personList);
    }

    private void generateRole() {
        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.builder().name("Administration").status(RoleStatusEnum.ACTIVE).build());
        roleList.add(Role.builder().name("Sales Representation").status(RoleStatusEnum.ACTIVE).build());
        roleList.add(Role.builder().name("Acquisition Representation").status(RoleStatusEnum.ACTIVE).build());
        this.roleRepository.saveAll(roleList);
    }
}
