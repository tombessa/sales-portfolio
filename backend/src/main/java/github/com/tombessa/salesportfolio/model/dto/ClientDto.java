package github.com.tombessa.salesportfolio.model.dto;

import github.com.tombessa.salesportfolio.enums.ClientStatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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
    private PersonDto people;
    @Builder.Default
    private List<OrderDto> orderList = new ArrayList<>();
    @Builder.Default
    private List<EntityAddressDto> listEntityAddress = new ArrayList<>();
    @Builder.Default
    private List<EntityDocumentDto> listEntityDocument = new ArrayList<>();
}
