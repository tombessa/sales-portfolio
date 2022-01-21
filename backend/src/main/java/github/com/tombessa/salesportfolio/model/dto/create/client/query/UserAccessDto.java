package github.com.tombessa.salesportfolio.model.dto.create.client.query;

import github.com.tombessa.salesportfolio.enums.UserAccessStatusEnum;
import github.com.tombessa.salesportfolio.model.dto.BaseEntityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {})
@SuperBuilder
public class UserAccessDto extends BaseEntityDto {

    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserAccessStatusEnum status;

    private RoleDto role;
}
