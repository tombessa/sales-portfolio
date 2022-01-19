package github.com.tombessa.salesportfolio.service.impl;

import github.com.tombessa.salesportfolio.enums.RoleStatusEnum;
import github.com.tombessa.salesportfolio.enums.UserAccessStatusEnum;
import github.com.tombessa.salesportfolio.model.Address;
import github.com.tombessa.salesportfolio.model.Person;
import github.com.tombessa.salesportfolio.model.UserAccess;
import github.com.tombessa.salesportfolio.model.dto.AddressDto;
import github.com.tombessa.salesportfolio.model.dto.PersonDto;
import github.com.tombessa.salesportfolio.model.dto.UserAccessDto;
import github.com.tombessa.salesportfolio.repository.RoleRepository;
import github.com.tombessa.salesportfolio.repository.UserAccessRepository;
import github.com.tombessa.salesportfolio.service.UserAccessService;
import github.com.tombessa.salesportfolio.util.Constants;
import github.com.tombessa.salesportfolio.util.Messages;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAccessImpl implements UserAccessService {
    private final UserAccessRepository userAccessRepository;
    private final RoleRepository roleRepository;
    private Messages messages;
    private List<UserAccessStatusEnum> validUserAccessToUpdate = new ArrayList<>();

    public UserAccessImpl(UserAccessRepository userAccessRepository, RoleRepository roleRepository, Messages messages) {
        this.userAccessRepository = userAccessRepository;
        this.roleRepository = roleRepository;
        this.messages = messages;
        validUserAccessToUpdate.add(UserAccessStatusEnum.ACTIVE);
        validUserAccessToUpdate.add(UserAccessStatusEnum.PENDING);
    }

    private UserAccess generate(UserAccess userAccess, UserAccessDto userAccessDto){
        //User Access
        userAccess.setStatus(userAccessDto.getStatus());
        //People
        if(userAccessDto.getPeople()!=null){
            PersonDto peopleDto = userAccessDto.getPeople();
            Person people = null;
            if(userAccess.getPeople()!=null) people = userAccess.getPeople();
            else people = Person.builder().build();
            people.setStatus(peopleDto.getStatus());
            people.setBirthdate(peopleDto.getBirthdate());
            people.setName(peopleDto.getName());
            people.setCellPhone(peopleDto.getCellPhone());
            people.setOtherPhone(peopleDto.getOtherPhone());
            people.setEmail(peopleDto.getEmail());

            //Address
            if(peopleDto.getAddress()!=null){
                AddressDto addressDto = peopleDto.getAddress();
                Address address = null;
                if(people.getAddress()!=null) address = people.getAddress();
                else address = Address.builder().build();
                address.setAddressLine1(addressDto.getAddressLine1());
                address.setAddressLine2(addressDto.getAddressLine2());
                address.setAddressLine3(addressDto.getAddressLine3());
                address.setAddressLine4(addressDto.getAddressLine4());
                address.setCity(addressDto.getCity());
                address.setState(addressDto.getState());
                address.setZipcode(addressDto.getZipcode());
                address.setCountry(addressDto.getCountry());
                address.setStatus(addressDto.getStatus());
            }
            //Role
            this.roleRepository
                    .findByIdAndStatus(userAccessDto.getRole().getId(), RoleStatusEnum.ACTIVE)
                    .ifPresent(role -> {
                userAccess.setRole(role);
            });
            userAccess.setPeople(people);
        }
        return userAccess;
    }
    @Override
    public UserAccess create(UserAccessDto userAccessDto){
        UserAccess userAccess = this.generate(UserAccess.builder().build(),userAccessDto);
        return this.userAccessRepository.save(userAccess);
    }


    @Override
    public UserAccess persist(UserAccessDto userAccessDto) {
        UserAccess userAccess = userAccessRepository.findByLoginAndStatusIn(userAccessDto.getLogin(), this.validUserAccessToUpdate)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get(Constants.EXCEPTION_USERACESS_NOT_FOUND)));
        userAccess = this.generate(userAccess,userAccessDto);
        return this.userAccessRepository.save(userAccess);
    }
}
