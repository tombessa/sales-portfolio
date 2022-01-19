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
public class PaymentDto extends BaseEntityDto implements Serializable {
    private String status;
    private String type;
    private BigDecimal amount;
    private OrderDto order;
    private String receivedBy;
}
