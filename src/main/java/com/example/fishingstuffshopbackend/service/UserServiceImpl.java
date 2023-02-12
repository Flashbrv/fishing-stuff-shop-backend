package com.example.fishingstuffshopbackend.service;

import com.example.fishingstuffshopbackend.domain.Role;
import com.example.fishingstuffshopbackend.domain.User;
import com.example.fishingstuffshopbackend.exception.RoleNotFoundException;
import com.example.fishingstuffshopbackend.exception.UserNotFoundException;
import com.example.fishingstuffshopbackend.repository.RoleRepository;
import com.example.fishingstuffshopbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.fishingstuffshopbackend.utils.CheckParameterUtils.setIfNotNullOrBlank;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<User> findAll() {
        log.info("Getting all users");
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        log.info("Looking for user with id={}", id);
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User create(User toCreate) {
        log.info("Saving new user {} to database", toCreate.getEmail());
        return userRepository.save(toCreate);
    }

    @Override
    public User update(long id, User toUpdate) {
        log.info("Looking for user with id={} for update", id);
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        log.info("Update user {}", user.getEmail());
        setIfNotNullOrBlank(user::setFirstName, toUpdate.getFirstName());
        setIfNotNullOrBlank(user::setLastName, toUpdate.getLastName());
        setIfNotNullOrBlank(user::setEmail, toUpdate.getEmail());
        setIfNotNullOrBlank(user::setPhoneNumber, toUpdate.getPhoneNumber());

        log.info("Saving updated user {} to ths database", user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        log.info("Deleting user with id={}", id);
        userRepository.deleteById(id);
    }

    @Override
    public Role save(Role role) {
        log.info("Saving role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("Set role {} to user {} ", roleName, email);
        User user = userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        Role role = roleRepository.findFirstByName(roleName)
                .orElseThrow(() -> new RoleNotFoundException(roleName));
        user.getRoles().add(role);
    }

    @Override
    public void removeRoleFromUser(String email, String roleName) {
        log.info("Remove role {} from user {} ", roleName, email);
        User user = userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        Role role = roleRepository.findFirstByName(roleName)
                .orElseThrow(() -> new RoleNotFoundException(roleName));
        user.getRoles().remove(role);
    }
}
