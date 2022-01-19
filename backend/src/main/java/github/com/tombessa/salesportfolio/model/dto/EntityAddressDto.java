package github.com.tombessa.salesportfolio.model.dto;

import github.com.tombessa.salesportfolio.model.dto.BaseEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Data
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
@EqualsAndHashCode(of = {"id"})
public class EntityAddressDto extends BaseEntityDto implements Serializable {
    private String status;
}
