package team6.bw5_epic_energy_services.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team6.bw5_epic_energy_services.entities.Role;
import team6.bw5_epic_energy_services.entities.User;
import team6.bw5_epic_energy_services.exceptions.BadRequestException;
import team6.bw5_epic_energy_services.exceptions.NotFoundException;
import team6.bw5_epic_energy_services.payloads.UserRegistrationDTO;
import team6.bw5_epic_energy_services.payloads.UserRespDTO;
import team6.bw5_epic_energy_services.payloads.UserUpdateDTO;
import team6.bw5_epic_energy_services.repositories.RoleRepository;
import team6.bw5_epic_energy_services.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Cloudinary imgUploader;

    public Page<User> findAllUsers(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return userRepository.findAll(pageable);
    }

    public User findById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public UserRespDTO save(UserRegistrationDTO body) {
        userRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("The Email " + user.getEmail() + " is already in use!");
        });
        userRepository.findByUsername(body.username()).ifPresent(user -> {
            throw new BadRequestException("The username " + body.username() + " is already in use!");
        });

        //CREAZIONE USER
        User newUser = new User(
                body.username(),
                body.email(),
                passwordEncoder.encode(body.password()),
                body.name(),
                body.surname()
                //"https://avatars.com/" + body.name() + "+" + body.surname()
        );

        Role defaultRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("The role 'USER' was not found in the database."));
        newUser.setRoleList(Collections.singletonList(defaultRole));

        User savedUser = userRepository.save(newUser);
        return new UserRespDTO(savedUser.getId());
    }

    public UserRespDTO saveAdmin(UserRegistrationDTO body) {
        userRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("The Email " + user.getEmail() + " is already in use!");
        });
        userRepository.findByUsername(body.username()).ifPresent(user -> {
            throw new BadRequestException("The username " + user.getUsername() + " is already in use!");
        });

        User newUser = new User(
                body.username(),
                body.email(),
                passwordEncoder.encode(body.password()),
                body.name(),
                body.surname()
        );

        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("The role 'ADMIN' was not found in the database."));
        newUser.setRoleList(List.of(adminRole));

        User savedUser = userRepository.save(newUser);
        return new UserRespDTO(savedUser.getId());
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " not found!"));
    }

    public User findByIdAndUpdate(UUID userId, UserUpdateDTO body) {
        User found = this.findById(userId);

        found.setUsername(body.username());
        found.setName(body.name());
        found.setUsername(body.username());
        found.setAvatar(body.avatar());

        return userRepository.save(found);
    }

    public void findByIdAndDelete(UUID userId) {
        User found = this.findById(userId);
        userRepository.delete(found);
    }

    public void addRoleToUser(UUID userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoleList().add(role);
        userRepository.save(user);
    }

    public User tryFindByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User uploadAvatar(MultipartFile file, UUID userId) {
        try {
            User found = this.findById(userId);
            Map result = imgUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imgURL = (String) result.get("url");
            found.setAvatar(imgURL);
            return userRepository.save(found);

        } catch (Exception e) {
            throw new BadRequestException("Problems while saving file.");
        }
    }
}
