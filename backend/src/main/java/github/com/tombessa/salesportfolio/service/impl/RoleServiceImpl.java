package github.com.tombessa.salesportfolio.service.impl;

import github.com.tombessa.salesportfolio.enums.RoleStatusEnum;
import github.com.tombessa.salesportfolio.model.Role;
import github.com.tombessa.salesportfolio.model.dto.RoleDto;
import github.com.tombessa.salesportfolio.repository.RoleRepository;
import github.com.tombessa.salesportfolio.service.RoleService;
import github.com.tombessa.salesportfolio.util.Constants;
import github.com.tombessa.salesportfolio.util.Messages;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private Messages messages;
    private final RoleRepository roleRepository;
    private List<RoleStatusEnum> validRole = new ArrayList<>();

    public RoleServiceImpl(Messages messages, RoleRepository roleRepository) {
        this.messages = messages;
        this.roleRepository = roleRepository;
        validRole.add(RoleStatusEnum.ACTIVE);
        validRole.add(RoleStatusEnum.PENDING);
    }

    @Override
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }

    @Override
    public Role persist(RoleDto roleDto) {
        Role role = this.roleRepository.findByIdAndStatusIn(roleDto.getId(), validRole)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get(Constants.EXCEPTION_ROLE_NOT_FOUND)));
        role.setName(roleDto.getName());
        role.setStatus(roleDto.getStatus());
        return this.roleRepository.save(role);

    }
}
