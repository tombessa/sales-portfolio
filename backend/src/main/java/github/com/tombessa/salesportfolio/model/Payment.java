package github.com.tombessa.salesportfolio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {})
@SuperBuilder
@Entity
@Table(name = "payment", schema="sales")
public class Payment extends BaseEntity{

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @JoinColumn(name = "order_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "payment_order_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Order order;

    @Column(name = "RECEIVED_BY", length = 15)
    private String receivedBy;

}