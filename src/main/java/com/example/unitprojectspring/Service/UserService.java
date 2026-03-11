package com.example.unitprojectspring.Service;
import com.example.unitprojectspring.DTO.UserDTO;
import com.example.unitprojectspring.DTO.UserRegistrationDTO;
import com.example.unitprojectspring.Entities.User;
import com.example.unitprojectspring.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO registerUser(UserRegistrationDTO registrationDTO) {

         if (userRepository.existsByEmail(registrationDTO.getEmail())) {
             throw new RuntimeException("Email already in use");
         }

        // 2. Instantiate a new User entity
        User newUser = new User();

        // 3. Map the clean data from the DTO to the Entity
        newUser.setUsername(registrationDTO.getUsername());
        newUser.setEmail(registrationDTO.getEmail());

        // 4. THE CRITICAL STEP: Hash the password before setting it
        String hashedPassword = passwordEncoder.encode(registrationDTO.getPassword());
        newUser.setPassword(hashedPassword);

        // 5. Save the user to the database
        userRepository.save(newUser);

        return convertToDto(newUser);
    }

    //ADD CRUD METHODS HERE CREATE ALREADY DONE

    public UserDTO convertToDto(User userEntity) {
        UserDTO dto = new UserDTO();
        dto.setUsername(userEntity.getUsername());
        dto.setEmail(userEntity.getEmail());
        dto.setCreatedAt(userEntity.getCreatedAt());
        return dto;
    }

    public UserRegistrationDTO convertToRegistrationDto(User userEntity) {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setUsername(userEntity.getUsername());
        dto.setEmail(userEntity.getEmail());
        dto.setPassword(userEntity.getPassword());
        return dto;
    }
}
