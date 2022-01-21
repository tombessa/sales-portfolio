package github.com.tombessa.salesportfolio.model.dto;

import github.com.tombessa.salesportfolio.enums.AddressStatusEnum;
import github.com.tombessa.salesportfolio.enums.EntityAddressStatusEnum;
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
public class EntityAddressDto extends BaseEntityDto {

    @Enumerated(EnumType.STRING)
    @NotNull
    private EntityAddressStatusEnum status;

    private SupplierDto supplier;
    @NotNull
    private AddressDto address;
    private PersonDto people;
    private ClientDto client;
}
