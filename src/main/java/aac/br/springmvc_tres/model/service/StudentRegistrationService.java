package aac.br.springmvc_tres.model.service;

import aac.br.springmvc_tres.model.dto.request.RegistrationRequestDto;
import aac.br.springmvc_tres.model.dto.response.RegistrationResponseDto;
import aac.br.springmvc_tres.model.entity.RegistrationEntity;
import aac.br.springmvc_tres.model.mapper.Mapper;
import aac.br.springmvc_tres.model.repository.StudentRegistrationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentRegistrationService {

    private final StudentRegistrationRepository repository;

    public RegistrationResponseDto registerStudentCourse(RegistrationRequestDto requestDto) {

        RegistrationEntity entity = Mapper.fromRequestMapper(requestDto);

        var entitySaved = repository.save(entity);
        log.info("StudentRegistrationService.registerStudentCourse: Registered in course successful | data: {}", requestDto);

        return Mapper.fromEntityMapper(entitySaved);
    }

    public void deleteSubscriptionById(Integer id) {

        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("ID " + id + " não encontrado.");
        }
        repository.deleteById(id);
        log.info("StudentRegistrationService.deleteSubscriptionById: Subscription deleted to id | {}", id);

    }

    public List<RegistrationResponseDto> findAllSubscriptionByUserId(Integer userId) {

        Optional<List<RegistrationEntity>> subscriptions = repository.findAllByUserId(userId);

        return subscriptions.map(registrationEntities -> registrationEntities.stream()
                .map(Mapper::fromEntityMapper)
                .collect(Collectors.toList())).orElseGet(List::of);
    }
}
