package aac.br.springmvc_tres.model.mapper;

import aac.br.springmvc_tres.model.dto.request.RegistrationRequestDto;
import aac.br.springmvc_tres.model.dto.response.RegistrationResponseDto;
import aac.br.springmvc_tres.model.entity.RegistrationEntity;

public class Mapper {

    public static RegistrationEntity fromRequestMapper(RegistrationRequestDto requestDto) {
        return RegistrationEntity.builder()
                .userId(requestDto.getUserId())
                .name(requestDto.getName())
                .birthDate(requestDto.getBirthDate())
                .postalCode(requestDto.getPostalCode())
                .city(requestDto.getCity())
                .street(requestDto.getStreet())
                .neighborhood(requestDto.getNeighborhood())
                .fu(requestDto.getFu())
                .course(requestDto.getCourse())
                .build();
    }

    public static RegistrationResponseDto fromEntityMapper(RegistrationEntity entity) {
        return RegistrationResponseDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .name(entity.getName())
                .birthDate(entity.getBirthDate())
                .postalCode(entity.getPostalCode())
                .city(entity.getCity())
                .street(entity.getStreet())
                .neighborhood(entity.getNeighborhood())
                .fu(entity.getFu())
                .course(entity.getCourse())
                .build();
    }

}
