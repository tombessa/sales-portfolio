package github.com.tombessa.salesportfolio.service;

import github.com.tombessa.salesportfolio.model.Role;
import github.com.tombessa.salesportfolio.model.dto.RoleDto;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role persist(RoleDto roleDto);
}
