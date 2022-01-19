package github.com.tombessa.salesportfolio.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
@EqualsAndHashCode(of = {"id"})
public class PriceDto extends BaseEntityDto implements Serializable {
    private String status;
    private BigDecimal value;
    private BigDecimal profitMargin;
    private ProductDto product;
}
