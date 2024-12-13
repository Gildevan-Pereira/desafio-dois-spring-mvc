package aac.br.springmvc_tres.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "registration")
public class RegistrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
