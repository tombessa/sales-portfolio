package github.com.tombessa.salesportfolio.controller;

import github.com.tombessa.salesportfolio.config.SecurityConfiguration;
import github.com.tombessa.salesportfolio.enums.UserAccessStatusEnum;
import github.com.tombessa.salesportfolio.exception.BusinessException;
import github.com.tombessa.salesportfolio.model.UserAccess;
import github.com.tombessa.salesportfolio.model.dto.UserAccessDto;
import github.com.tombessa.salesportfolio.repository.UserAccessRepository;
import github.com.tombessa.salesportfolio.util.Constants;
import github.com.tombessa.salesportfolio.util.Messages;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userAccess")
@Tag(name = "userAccessController")
@SecurityRequirement(name = "javainuseapi")
public class UserAccessController {

    private Messages messages;
    private UserAccessRepository userAccessRepository;
    private final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private final MapperFacade mapperFacade;
    private SecurityConfiguration securityConfiguration;

    public UserAccessController(Messages messages, UserAccessRepository userAccessRepository) {
        this.messages = messages;
        this.userAccessRepository = userAccessRepository;
        this.mapperFactory.classMap(UserAccess.class, UserAccessDto.class);
        this.mapperFactory.classMap(UserAccessDto.class, UserAccess.class);
        this.mapperFactory.classMap(UserAccess.class, github.com.tombessa.salesportfolio.model.dto.create.client.query.UserAccessDto.class);
        this.mapperFactory.classMap(github.com.tombessa.salesportfolio.model.dto.create.client.query.UserAccessDto.class, UserAccess.class);
        this.mapperFactory.classMap(UserAccess.class, github.com.tombessa.salesportfolio.model.dto.update.useraccess.UserAccessDto.class);
        this.mapperFactory.classMap(github.com.tombessa.salesportfolio.model.dto.update.useraccess.UserAccessDto.class, UserAccess.class);

        this.mapperFacade = mapperFactory.getMapperFacade();
    }

    @PatchMapping("/me")
    @Tag(name="This endpoint is responsible to change UserAccess (password, status)")
    public github.com.tombessa.salesportfolio.model.dto.update.useraccess.UserAccessDto
                                        updateUserAccess(Authentication authentication,
                                        github.com.tombessa.salesportfolio.model.dto.update.useraccess.UserAccessDto userAccessDto){
        Object principal = authentication.getPrincipal();
        String userName = null;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal). getUsername();
        } else {
            userName = principal.toString();
        }
        if(userName==null) throw new BusinessException(this.messages.get(Constants.EXCEPTION_USER_NOT_AUTHORIZED));
        else{
            UserAccess userAccess = this.userAccessRepository.save(this.mapperFacade.map(userAccessDto, UserAccess.class));
            try{
                this.securityConfiguration.changePassword(userAccessDto.getLogin(),
                        userAccessDto.getPassword());
            }catch (Exception e) { new ResourceNotFoundException(this.messages.get(Constants.EXCEPTION_USER_NOT_AUTHORIZED));}

            return this.mapperFacade.map(userAccess, github.com.tombessa.salesportfolio.model.dto.update.useraccess.UserAccessDto.class);
        }
    }

    @GetMapping("/me")
    @Tag(name="This endpoint is responsible to check who is logged in application")
    public github.com.tombessa.salesportfolio.model.dto.create.client.query.UserAccessDto getUser(Authentication authentication){
        Object principal = authentication.getPrincipal();
        String userName = null;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal). getUsername();
        } else {
            userName = principal.toString();
        }
        if(userName==null) throw new BusinessException(this.messages.get(Constants.EXCEPTION_USER_NOT_AUTHORIZED));
        else{
            return this.mapperFacade.map(this.userAccessRepository
                    .findByLoginAndStatus(userName,UserAccessStatusEnum.ACTIVE)
                    .orElseThrow(() -> new ResourceNotFoundException(messages.get(Constants.EXCEPTION_USER_NOT_AUTHORIZED))),
                    github.com.tombessa.salesportfolio.model.dto.create.client.query.UserAccessDto.class);
        }
    }
}
