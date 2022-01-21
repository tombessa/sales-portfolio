package github.com.tombessa.salesportfolio.model.dto.update.useraccess;

import github.com.tombessa.salesportfolio.enums.AddressStatusEnum;
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
public class AddressDto extends BaseEntityDto {

    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String city;
    private String state;
    @NotNull
    private String zipcode;
    @NotNull
    private String country;
    @NotNull
    @Enumerated(EnumType.STRING)
    private AddressStatusEnum status;
}
