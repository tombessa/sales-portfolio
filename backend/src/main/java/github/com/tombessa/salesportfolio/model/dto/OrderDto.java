package github.com.tombessa.salesportfolio.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
@EqualsAndHashCode(of = {"id"})
public class OrderDto extends BaseEntityDto implements Serializable {
    private String status;
    private BigDecimal amount;
    private BigDecimal amountPaid;
    private BigDecimal discountRate;
    private List<OrderItemDto> orderItemList;
}
