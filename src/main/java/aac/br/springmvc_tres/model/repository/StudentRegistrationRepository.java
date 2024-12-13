package aac.br.springmvc_tres.model.repository;

import aac.br.springmvc_tres.model.entity.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRegistrationRepository extends JpaRepository<RegistrationEntity, Integer> {
}
