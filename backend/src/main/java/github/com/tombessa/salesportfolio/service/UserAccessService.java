package github.com.tombessa.salesportfolio.service;

import github.com.tombessa.salesportfolio.model.UserAccess;
import github.com.tombessa.salesportfolio.model.dto.UserAccessDto;

public interface UserAccessService {
    UserAccess create(UserAccessDto userAccessDto);
    UserAccess persist(UserAccessDto userAccessDto);
}
