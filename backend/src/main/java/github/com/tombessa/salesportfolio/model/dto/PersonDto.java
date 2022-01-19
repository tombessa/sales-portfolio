package github.com.tombessa.salesportfolio.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import github.com.tombessa.salesportfolio.enums.PersonStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {})
@SuperBuilder
public class PersonDto extends BaseEntityDto {
    @NotNull
    private AddressDto address;
    @Enumerated(EnumType.STRING)
    @NotNull
    private PersonStatusEnum status;
    @NotNull
    private String name;
    private String cellPhone;
    private String otherPhone;
    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$")
    private String email;
    @JsonFormat(pattern="MM/dd/yyyy")
    private Instant birthdate;
}
