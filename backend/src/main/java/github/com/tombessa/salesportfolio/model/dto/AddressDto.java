package github.com.tombessa.salesportfolio.model.dto;

import github.com.tombessa.salesportfolio.enums.AddressStatusEnum;
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
public class AddressDto extends BaseEntityDto{

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
