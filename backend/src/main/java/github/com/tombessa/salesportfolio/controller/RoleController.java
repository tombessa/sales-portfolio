package github.com.tombessa.salesportfolio.controller;

import github.com.tombessa.salesportfolio.enums.RoleStatusEnum;
import github.com.tombessa.salesportfolio.model.Role;
import github.com.tombessa.salesportfolio.model.dto.RoleDto;
import github.com.tombessa.salesportfolio.repository.RoleRepository;
import github.com.tombessa.salesportfolio.service.RoleService;
import github.com.tombessa.salesportfolio.util.Constants;
import github.com.tombessa.salesportfolio.util.Messages;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/role")
@Tag(name = "roleController")
@SecurityRequirement(name = "javainuseapi")
public class RoleController {
    @Autowired
    private Messages messages;

    private final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private final MapperFacade mapperFacade;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private List<RoleStatusEnum> validRole = new ArrayList<>();

    public RoleController(RoleRepository roleRepository, RoleService roleService) {
        this.roleService = roleService;
        mapperFactory.classMap(Role.class, RoleDto.class);
        mapperFactory.classMap(RoleDto.class, Role.class);
        this.mapperFacade = mapperFactory.getMapperFacade();
        this.roleRepository = roleRepository;
        validRole.add(RoleStatusEnum.ACTIVE);
        validRole.add(RoleStatusEnum.PENDING);
    }

    @GetMapping("/{id}")
    public RoleDto getRole(@PathVariable("id") @NotNull  String id){
        return this.mapperFacade.map(this.roleRepository.findByIdAndStatusIn(Integer.parseInt(id), validRole)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get(Constants.EXCEPTION_ROLE_NOT_FOUND))),
                RoleDto.class);
    }

    @GetMapping("/")
    public List<RoleDto> listAllRoles(){
        return this.mapperFacade.mapAsList(this.roleRepository.findByStatusIn(validRole), RoleDto.class);
    }

    @PutMapping
    public RoleDto addRole(@RequestBody @Valid RoleDto roleDto){
        return this.mapperFacade.map(
                this.roleRepository.save(this.mapperFacade.map(roleDto, Role.class)),
                RoleDto.class);
    }

    @PutMapping("/list")
    public List<RoleDto> addRoleList(@RequestBody @Valid List<RoleDto> roleDtoList){
        return this.mapperFacade.mapAsList(
                this.roleRepository.saveAll(this.mapperFacade.mapAsList(roleDtoList, Role.class)),
                RoleDto.class);
    }

    @PatchMapping
    public RoleDto updateRole(@RequestBody @Valid RoleDto roleDto){
        return this.mapperFacade.map(
                this.roleService.persist(roleDto),
                RoleDto.class);
    }

}
