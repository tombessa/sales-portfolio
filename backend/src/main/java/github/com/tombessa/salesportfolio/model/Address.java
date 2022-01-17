package github.com.tombessa.salesportfolio.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {})
@SuperBuilder
@Entity
@Table(name = "address", schema="sales")
public class Address extends BaseEntity{

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "address_line3")
    private String addressLine3;

    @Column(name = "address_line4")
    private String addressLine4;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zipcode", length = 10)
    private String zipcode;

    @Column(name = "country", length = 20)
    private String country;

    @Column(name = "status", length = 10)
    private String status;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "deliveryAddress")
    private List<Order> listDeliveryOrder = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "billingAddress")
    private List<Order> listBillingAddress = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "address")
    private List<EntityAddress> listEntityAddress = new ArrayList<>();

}