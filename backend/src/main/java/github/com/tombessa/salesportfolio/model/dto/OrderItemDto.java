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
public class OrderItemDto extends BaseEntityDto implements Serializable {
    private String status;
    private Integer qtd;
    private BigDecimal unitaryValue;
    private BigDecimal totalValue;
    private BigDecimal discountRate;
}
