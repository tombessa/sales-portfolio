package github.com.tombessa.salesportfolio.repository;

import github.com.tombessa.salesportfolio.enums.UserAccessStatusEnum;
import github.com.tombessa.salesportfolio.model.UserAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAccessRepository extends JpaRepository<UserAccess, Integer> {
    Optional<UserAccess> findByLoginAndStatus(String login,
                                             UserAccessStatusEnum status);
    Optional<UserAccess> findByLoginAndStatusIn(String login,
                                              List<UserAccessStatusEnum> status);
}
