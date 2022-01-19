package github.com.tombessa.salesportfolio.model.dto;

import com.fasterxml.jackson.databind.ser.Serializers;
import github.com.tombessa.salesportfolio.enums.UserAccessStatusEnum;
import github.com.tombessa.salesportfolio.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {})
@SuperBuilder
public class UserAccessDto extends BaseEntityDto{

    @NotNull
    private String login;
    @NotNull
    private String password;
    @NotNull
    private PersonDto people;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserAccessStatusEnum status;

    @NotNull
    private RoleDto role;
}
