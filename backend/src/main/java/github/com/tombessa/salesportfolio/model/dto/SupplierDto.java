package github.com.tombessa.salesportfolio.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {})
@SuperBuilder
public class SupplierDto extends BaseEntityDto {
    private String status;
    private String name;
    private PersonDto people;
    private String observation;
    private List<ProductDto> productList;
}
