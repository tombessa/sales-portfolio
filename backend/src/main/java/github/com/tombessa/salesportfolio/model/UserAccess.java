package github.com.tombessa.salesportfolio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {})
@SuperBuilder
@Entity
@Table(name = "user_access", schema = "sales", indexes = {
        @Index(name = "user_access_idx", columnList = "login"),
        @Index(name = "user_access_idxv1", columnList = "role_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "user_access_un", columnNames = {"login"})
})
public class UserAccess extends BaseEntity{

    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "login", length = 15)
    private String login;

    @Column(name = "password")
    private String password;


    @JoinColumn(name = "people_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "user_access_people_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Person people;

    @JoinColumn(name = "role_id", referencedColumnName="id", foreignKey = @ForeignKey(name = "user_access_role_fk"))
    @ManyToOne(fetch=FetchType.LAZY)
    private Role role;

}