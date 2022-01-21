package github.com.tombessa.salesportfolio.model.dto.create.client.query;

import github.com.tombessa.salesportfolio.enums.ClientStatusEnum;
import github.com.tombessa.salesportfolio.model.dto.BaseEntityDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {})
@SuperBuilder
public class ClientDto extends BaseEntityDto {
    @Enumerated(EnumType.STRING)
    @NotNull
    private ClientStatusEnum status;
    @NotNull
    private String name;
    private String observation;
}
