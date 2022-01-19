package github.com.tombessa.salesportfolio.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@SuperBuilder
public abstract class BaseEntityDto {
    private Integer id;
    @JsonFormat(pattern="MM/dd/yyyy HH:mm:ss")
    private LocalDateTime created;
    @JsonFormat(pattern="MM/dd/yyyy HH:mm:ss")
    private LocalDateTime updated;
    private String createdBy;
    private String updatedBy;
    private Integer version;
}
