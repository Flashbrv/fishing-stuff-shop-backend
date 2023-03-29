package com.example.fishingstuffshopbackend.service;

import com.example.fishingstuffshopbackend.TestUtils;
import com.example.fishingstuffshopbackend.domain.Category;
import com.example.fishingstuffshopbackend.domain.User;
import com.example.fishingstuffshopbackend.exception.*;
import com.example.fishingstuffshopbackend.repository.RoleRepository;
import com.example.fishingstuffshopbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private UserService service;

    private User willSmith;

    UserServiceTest() {
        willSmith = User.builder()
                .email("will@email.ua")
                .firstName("Will")
                .lastName("Smith")
                .phoneNumber("380971111111")
                .roles(new HashSet<>())
                .password("123")
                .build();
        TestUtils.setId(willSmith, 1L);
    }

    @BeforeEach
    void initService() {
        service = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
    }

    @Nested
    @DisplayName("findAll")
    class FindAllTests {
        @Test
        @DisplayName("should call repository.findAll() one time")
        void shouldCallRepositoryFindAllOneTime() {
            when(userRepository.findAll()).thenReturn(
                    Collections.emptyList()
            );

            service.findAll();

            verify(userRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("should return empty list")
        void shouldReturnEmptyList() {
            when(userRepository.findAll()).thenReturn(
                    Collections.emptyList()
            );

            List<User> users = service.findAll();

            assertThat(users.size())
                    .withFailMessage(() -> "Should be empty, but size was " + users.size())
                    .isEqualTo(0);
        }

        @Test
        @DisplayName("should return two entries")
        void shouldReturnListOfTwoEntries() {
            User johnDoe = User.builder()
                    .email("user@email.ua")
                    .firstName("John")
                    .lastName("Doe")
                    .phoneNumber("380971001010")
                    .roles(new HashSet<>())
                    .password("123")
                    .build();

            when(userRepository.findAll()).thenReturn(
                    List.of(willSmith, johnDoe)
            );

            List<User> users = service.findAll();

            assertThat(users.size())
                    .withFailMessage(() -> "Should contains two entries, but was " + users.size())
                    .isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("findById")
    class FindByIdTests {
        @Test
        @DisplayName("should return one user by id")
        void shouldReturnOneUserById() {
            when(userRepository.findById(1L)).thenReturn(Optional.of(willSmith));

            User user = service.findById(1L);

            assertThat(user.getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("should throw UserNotFoundException when user not founded")
        void shouldThrowUserNotFoundException_WhenUserNotFounded() {
            when(userRepository.findById(1L)).thenReturn(Optional.empty());

            UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                    () -> service.findById(1L));
            assertThat(exception.getMessage()).isEqualTo("User with id=1 not founded");
        }
    }

    @Nested
    @DisplayName("findByEmail")
    class FindByEmailTests {
        @Test
        @DisplayName("should return one user by email")
        void shouldReturnOneUserByEmail() {
            when(userRepository.findByEmail("will@email.ua")).thenReturn(Optional.of(willSmith));

            User user = service.findByEmail("will@email.ua");

            assertThat(user.getEmail()).isEqualTo("will@email.ua");
            verify(userRepository, atMostOnce()).findByEmail(anyString());
        }

        @Test
        @DisplayName("should throw UserNotFoundException when user not founded")
        void shouldThrowUserNotFoundException_WhenUserNotFounded() {
            when(userRepository.findByEmail("will@email.ua")).thenReturn(Optional.empty());

            UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                    () -> service.findByEmail("will@email.ua"));
            assertThat(exception.getMessage()).isEqualTo("User with email:\"will@email.ua\" not founded");
            verify(userRepository, atMostOnce()).findByEmail(anyString());
        }
    }

    @Nested
    @DisplayName("create")
    class CreateTests {
        @Test
        @DisplayName("should return saved new user")
        void shouldReturnSavedNewUser() {
            when(userRepository.save(any(User.class))).thenReturn(willSmith);

            User user = service.create(willSmith);

            assertThat(user.getEmail()).isEqualTo(willSmith.getEmail());
            assertThat(user.getId()).isEqualTo(willSmith.getId());
            verify(userRepository, times(1)).save(any());
        }

        @Test
        @DisplayName("should throws BadParameterException when user toCreate is null")
        void shouldThrowBadParameterException_whenUserToCreateIsNull() {
            assertThrows(BadParameterException.class, () -> service.create(null));
        }

        @Test
        @DisplayName("should throws BadParameterException when email is null")
        void shouldThrowBadParameterException_whenEmailIsNull() {
            User toCreate = User.builder().build();
            assertThrows(BadParameterException.class, () -> service.create(toCreate));
        }

        @Test
        @DisplayName("should throws BadParameterException when email is blank")
        void shouldThrowBadParameterException_whenEmailIsBlank() {
            User toCreate = User.builder()
                    .email("")
                    .build();
            assertThrows(BadParameterException.class, () -> service.create(toCreate));
        }

        @Test
        @DisplayName("should throws BadParameterException when firstName is null")
        void shouldThrowBadParameterException_whenFirstNameIsNull() {
            User toCreate = User.builder().build();
            assertThrows(BadParameterException.class, () -> service.create(toCreate));
        }

        @Test
        @DisplayName("should throws BadParameterException when firstName is blank")
        void shouldThrowBadParameterException_whenFirstNameIsBlank() {
            User toCreate = User.builder()
                    .firstName("")
                    .build();
            assertThrows(BadParameterException.class, () -> service.create(toCreate));
        }

        @Test
        @DisplayName("should throws BadParameterException when phoneNumber is null")
        void shouldThrowBadParameterException_whenPhoneNumberIsNull() {
            User toCreate = User.builder().build();
            assertThrows(BadParameterException.class, () -> service.create(toCreate));
        }

        @Test
        @DisplayName("should throws BadParameterException when phoneNumber is not valid")
        void shouldThrowBadParameterException_whenPhoneNumberIsNotValid() {
            User toCreate = User.builder()
                    .phoneNumber("22345")
                    .build();
            assertThrows(BadParameterException.class, () -> service.create(toCreate));
        }

        @Test
        @DisplayName("should throws SuchUserExistException")
        void shouldThrowSuchUserExistException() {
            when(userRepository.findByEmail(anyString()))
                    .thenReturn(Optional.of(willSmith));

            assertThrows(SuchUserExistException.class, () -> service.create(willSmith));
        }
    }

    @Nested
    @DisplayName("update")
    class UpdateTests {
        @Test
        @DisplayName("should return updated user")
        void shouldReturnUpdatedUser() {
            when(userRepository.findById(1L)).thenReturn(Optional.of(willSmith));
            when(userRepository.save(any(User.class))).then(invocation -> invocation.getArgument(0));

            User toUpdate = User.builder()
                    .email("will@email.ua")     // isn't updatable
                    .firstName("William")
                    .lastName("Smitt")
                    .phoneNumber("380972222222")
                    .roles(new HashSet<>())
                    .password("123")            // updatable through separated endpoint
                    .build();

            User updatedUser = service.update(1L, toUpdate);

            assertThat(updatedUser.getEmail()).isEqualTo("will@email.ua");
            verify(userRepository, times(1)).findById(1L);
            verify(userRepository, times(1)).save(willSmith);
        }
    }
}