package github.com.tombessa.salesportfolio.model.dto;

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
public class ProductDto extends BaseEntityDto implements Serializable {
    private String name;
    private String description;
    private byte[] picture;
    private String status;
}
