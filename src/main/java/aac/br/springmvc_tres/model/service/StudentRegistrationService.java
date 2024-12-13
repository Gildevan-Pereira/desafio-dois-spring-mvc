package aac.br.springmvc_tres.model.service;

import aac.br.springmvc_tres.model.dto.request.RegistrationRequestDto;
import aac.br.springmvc_tres.model.dto.response.RegistrationResponseDto;
import aac.br.springmvc_tres.model.entity.RegistrationEntity;
import aac.br.springmvc_tres.model.repository.StudentRegistrationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentRegistrationService {

    private final StudentRegistrationRepository repository;
    private final ModelMapper modelMapper;

    public RegistrationResponseDto registerStudentCourse(RegistrationRequestDto requestDto) {

        RegistrationEntity entity = modelMapper.map(requestDto, RegistrationEntity.class);

        var entitySaved = repository.save(entity);
        log.info("StudentRegistrationService.registerStudentCourse: Registered in course successful | data: {}", requestDto);

        return modelMapper.map(entitySaved, RegistrationResponseDto.class);
    }

    public void deleteStudentById(Integer id) {

        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("ID " + id + " não encontrado.");
        }
        repository.deleteById(id);
    }
}
