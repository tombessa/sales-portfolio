package github.com.tombessa.salesportfolio.controller;

import github.com.tombessa.salesportfolio.config.SecurityConfiguration;
import github.com.tombessa.salesportfolio.enums.UserAccessStatusEnum;
import github.com.tombessa.salesportfolio.model.UserAccess;
import github.com.tombessa.salesportfolio.model.dto.UserAccessDto;
import github.com.tombessa.salesportfolio.repository.UserAccessRepository;
import github.com.tombessa.salesportfolio.service.UserAccessService;
import github.com.tombessa.salesportfolio.util.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/userAccess")
@Tag(name = "userAccessController")
@SecurityRequirement(name = "javainuseapi")
public class UserAccessController {

    @Autowired
    private Messages messages;

    private final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private final MapperFacade mapperFacade;
    private final UserAccessRepository userAccessRepository;
    private final UserAccessService userAccessService;
    private SecurityConfiguration securityConfiguration;

    public UserAccessController(UserAccessRepository userAccessRepository, UserAccessService userAccessService, SecurityConfiguration securityConfiguration) {
        this.userAccessRepository = userAccessRepository;
        this.userAccessService = userAccessService;
        this.securityConfiguration = securityConfiguration;
        mapperFactory.classMap(UserAccess.class, UserAccessDto.class);
        this.mapperFacade = mapperFactory.getMapperFacade();
    }

    @GetMapping("/me")
    public UserAccessDto whoIs(Authentication authentication) {
        String user = ((User) authentication.getPrincipal()).getUsername();
        return  this.mapperFacade.map(
                this.userAccessRepository
                    .findByLoginAndStatus(user, UserAccessStatusEnum.ACTIVE)
                    .orElseThrow(() -> new ResourceNotFoundException(messages.get(Constants.EXCEPTION_USER_NOT_AUTHORIZED))),
                UserAccessDto.class);
    }

    @PutMapping
    public UserAccessDto addUserAccess(@RequestBody @Valid UserAccessDto userAccessDto){
        UserAccess userAccess = this.userAccessService.create(userAccessDto);
        try{
            this.securityConfiguration.changePassword(userAccessDto.getLogin(),
                    userAccessDto.getPassword(),
                    userAccessDto.getRole().getName());
        }catch (Exception e) { new ResourceNotFoundException(this.messages.get(Constants.EXCEPTION_USERACESS_NOT_UPDATEDPASSWORD));}
        userAccess = this.userAccessRepository.save(userAccess);
        return this.mapperFacade.map(userAccess, UserAccessDto.class);
    }

    @PostMapping
    public UserAccessDto updateUserAccess(@RequestBody @Valid UserAccessDto userAccessDto){
        UserAccess userAccess = this.userAccessService.persist(userAccessDto);
        try{
            this.securityConfiguration.changePassword(userAccessDto.getLogin(),
                    userAccessDto.getPassword(),
                    userAccessDto.getRole().getName());
        }catch (Exception e) { new ResourceNotFoundException(this.messages.get(Constants.EXCEPTION_USERACESS_NOT_UPDATEDPASSWORD));}
        return this.mapperFacade.map(userAccess, UserAccessDto.class);
    }

}
