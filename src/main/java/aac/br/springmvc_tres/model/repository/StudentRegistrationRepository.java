package aac.br.springmvc_tres.model.repository;

import aac.br.springmvc_tres.model.entity.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRegistrationRepository extends JpaRepository<RegistrationEntity, Integer> {

    Optional<List<RegistrationEntity>> findAllByUserId(Integer userId);
}
