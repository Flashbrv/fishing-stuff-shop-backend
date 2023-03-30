package com.example.fishingstuffshopbackend.service;

import com.example.fishingstuffshopbackend.domain.ConfirmationToken;
import com.example.fishingstuffshopbackend.domain.Role;
import com.example.fishingstuffshopbackend.domain.User;
import com.example.fishingstuffshopbackend.exception.BadParameterException;
import com.example.fishingstuffshopbackend.exception.SuchUserExistException;
import com.example.fishingstuffshopbackend.exception.UserNotFoundException;
import com.example.fishingstuffshopbackend.repository.RoleRepository;
import com.example.fishingstuffshopbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.example.fishingstuffshopbackend.utils.CheckParameterUtils.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Fetching User by email {} from database", email);
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                user.isEnabled(), true, true, !user.isLocked(),
                authorities);
    }

    @Override
    public List<User> findAll() {
        log.info("Fetching all users");
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
    public User findByEmail(String email) {
        log.info("Fetching user {}", email);
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public User create(User toCreate) {
        // TODO: Move check logic to RegistrationRequestValidator
        requireNonNull("New user", toCreate);

        log.info("Check email");
        requireNonNullAndNonBlank("Email", toCreate.getEmail());
        Optional<User> user = userRepository
                .findByEmail(toCreate.getEmail());
        if (user.isPresent()) {
            log.info("User with email {} currently present in database", toCreate.getEmail());
            throw new SuchUserExistException(toCreate.getEmail());
        }

        log.info("Check firstName");
        requireNonNullAndNonBlank("FirstName", toCreate.getFirstName());

        log.info("Check phoneNumber");
        requireNonNullAndNonBlank("PhoneNumber", toCreate.getPhoneNumber());
        String phoneNumber = toCreate.getPhoneNumber().trim();
        if (phoneNumber.length() != 12 || !phoneNumber.matches("\\d{12}")) {
            throw new BadParameterException(String.format("PhoneNumber have invalid format or length"));
        }

        log.info("Check password");
        requireNonNullAndNonBlank("Password", toCreate.getPassword());


        log.info("Encode password");
        toCreate.setPassword(passwordEncoder.encode(toCreate.getPassword()));

        log.info("Saving new user {} to database", toCreate.getEmail());
        return userRepository.save(toCreate);
    }

    @Override
    public User update(long id, User toUpdate) {
        log.info("Fetching user with id={} from database", id);
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        log.info("Update user {}", user.getEmail());
        setIfNotNullOrBlank(user::setFirstName, toUpdate.getFirstName());
        setIfNotNullOrBlank(user::setLastName, toUpdate.getLastName());
        setIfNotNullOrBlank(user::setPhoneNumber, toUpdate.getPhoneNumber());

        log.info("Saving updated user {} to database", user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        log.info("Deleting user with id={}", id);
        userRepository.deleteById(id);
    }

    @Override
    public Role save(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("Set role {} to user {} ", roleName, email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        Role role = roleRepository.findByName(roleName);
                //.orElseThrow(() -> new RoleNotFoundException(roleName));
        user.addRole(role);
    }

    @Override
    public void removeRoleFromUser(String email, String roleName) {
        log.info("Remove role {} from user {} ", roleName, email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        Role role = roleRepository.findByName(roleName);
//                .orElseThrow(() -> new RoleNotFoundException(roleName));
        user.removeRole(role);
    }

    @Override
    public String signUpUser(User user) {
        Optional<User> userOptional = userRepository
                .findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            log.info("User with email {} currently present in database", user.getEmail());
            throw new SuchUserExistException(user.getEmail());
        }

        log.info("Encode password");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        log.info("Fetch role ROLE_USER for new user");
        Role role = roleRepository.findByName("ROLE_USER");
        // TODO: Check role for null and process it properly

        log.info("Add role:{} to user:{}", role, user.getEmail());
        user.addRole(role);

        log.info("Saving new user {} to database", user.getEmail());
        userRepository.save(user);

        String token = confirmationTokenService.createTokenFor(user);

        return token;
    }

    @Override
    public String confirmEmail(String token) {
        log.info("Confirm token: {}", token);
        Optional<ConfirmationToken> tokenOptional = confirmationTokenService.confirmToken(token);
        if (!tokenOptional.isPresent()) {
            log.info("Token {} is not confirmed", token);
            return "Can't confirm email. Token expired or is not valid.";
        }

        User user = tokenOptional.get().getUser();
        user.setEnabled(true);
        userRepository.save(user);
        log.info("User {} was enabled", user.getEmail());

        return "Email confirmed.";
    }


}
