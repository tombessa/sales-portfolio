package github.com.tombessa.salesportfolio.controller;

import github.com.tombessa.salesportfolio.model.dto.ResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;

@RestController
@Tag(name = "acquisitionGateway")
public class AcquisitionGateway {

    @GetMapping("/list")
    public ResponseDTO list(@RequestParam("id") @Size(min = 4, max = 6) final String id) {
        return ResponseDTO.builder().message("OK-"+id).build();
    }

}
