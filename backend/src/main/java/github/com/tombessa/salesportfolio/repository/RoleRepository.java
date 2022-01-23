package github.com.tombessa.salesportfolio.repository;

import github.com.tombessa.salesportfolio.enums.RoleStatusEnum;
import github.com.tombessa.salesportfolio.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByIdAndStatusIn(Integer id, List<RoleStatusEnum> status);
    List<Role> findByStatusIn(List<RoleStatusEnum> status);
    Optional<Role> findByIdAndStatus(Integer id, RoleStatusEnum status);
    Optional<Role> findByNameAndStatus(String name, RoleStatusEnum status);
}
