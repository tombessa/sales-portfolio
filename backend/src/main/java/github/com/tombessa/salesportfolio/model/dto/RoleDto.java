package github.com.tombessa.salesportfolio.model.dto;

import github.com.tombessa.salesportfolio.enums.RoleStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {})
@SuperBuilder
public class RoleDto extends BaseEntityDto{
    @NotNull
    private String name;
    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleStatusEnum status;
    private List<UserAccessDto> roleList;
}
