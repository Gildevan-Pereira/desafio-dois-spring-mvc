package aac.br.springmvc_tres.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponseDto {


    private Integer id;

    private String name;

    private LocalDate birthDate;

    private String postalCode;

    private String city;

    private String street;

    private String neighborhood;

    private String fu;

    private String course;
}
