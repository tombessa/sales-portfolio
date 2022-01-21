package github.com.tombessa.salesportfolio.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import github.com.tombessa.salesportfolio.enums.PersonStatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {})
@SuperBuilder
public class PersonDto extends BaseEntityDto {
    @Enumerated(EnumType.STRING)
    @NotNull
    private PersonStatusEnum status;
    @NotNull
    private String name;
    private String cellPhone;
    private String otherPhone;
    private String email;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date birthdate;

    @Builder.Default
    private List<UserAccessDto> userAccessList = new ArrayList<>();
    @Builder.Default
    private List<ClientDto> clientList  = new ArrayList<>();
    @Builder.Default
    private List<EntityAddressDto> entityAddressList = new ArrayList<>();
    @Builder.Default
    private List<SupplierDto> supplierList = new ArrayList<>();
    @Builder.Default
    private List<EntityDocumentDto> entityDocumentList = new ArrayList<>();
}
