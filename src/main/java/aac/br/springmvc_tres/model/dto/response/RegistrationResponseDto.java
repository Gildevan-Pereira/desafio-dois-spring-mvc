package aac.br.springmvc_tres.model.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponseDto {


    private Integer id;

    private Integer userId;

    private String name;

    private LocalDate birthDate;

    private String postalCode;

    private String city;

    private String street;

    private String neighborhood;

    private String fu;

    private String course;
}
