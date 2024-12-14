package aac.br.springmvc_tres.model.service;

import aac.br.springmvc_tres.exception.BusinessException;
import aac.br.springmvc_tres.model.dto.request.UserRequestDto;
import aac.br.springmvc_tres.model.entity.UserEntity;
import aac.br.springmvc_tres.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void createUser(UserRequestDto requestDto) {

        Optional<UserEntity> user = userRepository.findByName(requestDto.getUsername());

        if (user.isPresent()) {
            throw new BusinessException("User already registered!", "001",HttpStatus.CONFLICT);
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setName(requestDto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        userRepository.save(userEntity);
    }

    public UserEntity findByUsername(String username) {
        UserEntity user = userRepository.findByName(username)
                .orElseThrow(() -> new
                        BusinessException("User not found with username: " + username, "050", HttpStatus.NOT_FOUND));

        return user;
    }

    public UserEntity findById(Integer id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new
                        BusinessException("User not found to id: " + id, "051", HttpStatus.NOT_FOUND));

        return user;
    }
}
