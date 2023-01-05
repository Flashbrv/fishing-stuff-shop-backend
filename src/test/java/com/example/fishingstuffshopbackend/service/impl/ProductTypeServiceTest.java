package com.example.fishingstuffshopbackend.service.impl;

import com.example.fishingstuffshopbackend.dto.ProductTypeDto;
import com.example.fishingstuffshopbackend.exception.BadParameterException;
import com.example.fishingstuffshopbackend.exception.ProductTypeEntityWithSuchNameExist;
import com.example.fishingstuffshopbackend.exception.ProductTypeNotFoundException;
import com.example.fishingstuffshopbackend.mapper.ProductTypeMapper;
import com.example.fishingstuffshopbackend.mapper.ProductTypeMapperImpl;
import com.example.fishingstuffshopbackend.model.ProductTypeEntity;
import com.example.fishingstuffshopbackend.repository.ProductTypeRepository;
import com.example.fishingstuffshopbackend.service.ProductTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductTypeServiceTest {

    private ProductTypeMapper mapper;

    @Mock
    private ProductTypeRepository repository;

    private ProductTypeService service;

    private ProductTypeEntity hook;

    ProductTypeServiceTest() {
        mapper = new ProductTypeMapperImpl();

        hook = new ProductTypeEntity();
        hook.setId(1L);
        hook.setName("Hook");
    }

    @BeforeEach
    void initService() {
        service = new ProductTypeServiceImpl(repository, mapper);
    }

    @Nested
    @DisplayName("findAll")
    class FindAllTests {
        @Test
        @DisplayName("should call repository.findAll() one time")
        void shouldCallRepositoryFindAllOneTime() {
            when(repository.findAll()).thenReturn(
                    Collections.emptyList()
            );

            service.findAll();

            verify(repository, times(1)).findAll();
        }

        @Test
        @DisplayName("should return empty list")
        void shouldReturnEmptyList() {
            when(repository.findAll()).thenReturn(
                    Collections.emptyList()
            );

            List<ProductTypeDto> typeDtos = service.findAll();

            assertThat(typeDtos.size())
                    .withFailMessage(() -> "Should be empty, but size was " + typeDtos.size())
                    .isEqualTo(0);
        }

        @Test
        @DisplayName("should return two entries")
        void shouldReturnListOfTwoEntries() {
            ProductTypeEntity pole = new ProductTypeEntity();
            pole.setName("Pole");

            when(repository.findAll()).thenReturn(
                    List.of(hook, pole)
            );

            List<ProductTypeDto> typeDtos = service.findAll();

            assertThat(typeDtos.size())
                    .withFailMessage(() -> "Should contains two entries, but was " + typeDtos.size())
                    .isEqualTo(2);
        }

        @Test
        @DisplayName("check returned dto")
        void checkReturnedDto() {
            when(repository.findAll()).thenReturn(
                    List.of(hook)
            );

            List<ProductTypeDto> typeDtos = service.findAll();

            assertThat(typeDtos.size())
                    .withFailMessage(() -> "Should contains one entry, but was " + typeDtos.size())
                    .isEqualTo(1);

            ProductTypeDto dto = typeDtos.get(0);
            assertThat(dto.getId()).isEqualTo(1L);
            assertThat(dto.getName()).isEqualTo("Hook");
        }
    }

    @Nested
    @DisplayName("findById")
    class FindByIdTests {
        @Test
        @DisplayName("should return one dto")
        void shouldReturnOneDto() {
            when(repository.findById(1L)).thenReturn(Optional.of(hook));

            ProductTypeDto dto = service.findById(1L);

            assertThat(dto.getId()).isEqualTo(1L);
            assertThat(dto.getName()).isEqualTo("Hook");
        }

        @Test
        @DisplayName("should throw ProductTypeNotFoundException when no entity founded")
        void shouldThrowProductTypeNotFoundExceptionWhenProductTypeNotFounded() {
            when(repository.findById(1L)).thenReturn(Optional.empty());

            ProductTypeNotFoundException exception = assertThrows(ProductTypeNotFoundException.class,
                    () -> service.findById(1L));
            assertThat(exception.getMessage()).isEqualTo("Couldn't find product type with id=1");
        }
    }

    @Nested
    @DisplayName("update")
    class UpdateTests {
        @Test
        @DisplayName("should return dto from updated ProductTypeEntity")
        void shouldReturnUpdatedDto() {
            when(repository.findById(1L)).thenReturn(Optional.of(hook));
            when(repository.save(any(ProductTypeEntity.class))).then(invocation -> invocation.getArgument(0));

            ProductTypeDto dto = service.update(1L, "Gak");

            assertThat(dto.getName()).isEqualTo("Gak");
            verify(repository, times(1)).findById(1L);
            verify(repository, times(1)).save(hook);
        }

        @Test
        @DisplayName("should throws ProductTypeNotFoundException when entity does not exist")
        void shouldThrowProductTypeNotFoundException_WhenNoEntityFounded() {
            when(repository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(ProductTypeNotFoundException.class, () -> service.update(1L, "Gak"));

            verify(repository, times(1)).findById(1L);
            verify(repository, never()).save(any());
        }

        @Test
        @DisplayName("should throws BadParameterException when id is null")
        void shouldThrowBadParameterException_WhenIdIsNull() {
            assertThrows(BadParameterException.class, () -> service.update(null, "Gak"));
        }

        @Test
        @DisplayName("should throws BadParameterException when newName is null")
        void shouldThrowBadParameterException_WhenNewNameIsNull() {
            when(repository.findById(1L)).thenReturn(Optional.of(hook));

            assertThrows(BadParameterException.class, () -> service.update(1L, null));
            verify(repository, times(1)).findById(1L);
        }

        @Test
        @DisplayName("should throws BadParameterException when newName is blank")
        void shouldThrowBadParameterException_WhenNewNameIsBlank() {
            when(repository.findById(1L)).thenReturn(Optional.of(hook));

            assertThrows(BadParameterException.class, () -> service.update(1L, ""));
            verify(repository, times(1)).findById(1L);
        }
    }

    @Nested
    @DisplayName("create")
    class CreateTests {
        @Test
        @DisplayName("should return dto from created ProductTypeEntity")
        void shouldReturnNewDtoFromCreatedProductTypeEntity() {
            when(repository.save(any(ProductTypeEntity.class))).thenReturn(hook);

            ProductTypeDto dto = service.create(hook.getName());

            assertThat(dto.getName()).isEqualTo(hook.getName());
            assertThat(dto.getId()).isEqualTo(hook.getId());
            verify(repository, times(1)).save(any());
        }

        @Test
        @DisplayName("should throws BadParameterException when name is null")
        void shouldThrowBadParameterException_whenNameIsNull() {
            assertThrows(BadParameterException.class, () -> service.create(null));
        }

        @Test
        @DisplayName("should throws BadParameterException when name is blank")
        void shouldThrowBadParameterException_whenNameIsBlank() {
            assertThrows(BadParameterException.class, () -> service.create(""));
        }

        @Test
        @DisplayName("should throws ProductTypeEntityWithSuchNameExist")
        void shouldProductTypeEntityWithSuchNameExist() {
            when(repository.save(any(ProductTypeEntity.class)))
                    .thenThrow(new ProductTypeEntityWithSuchNameExist(hook.getName()));

            assertThrows(ProductTypeEntityWithSuchNameExist.class, () -> service.create(hook.getName()));
        }
    }

    @Nested
    @DisplayName("delete")
    class DeleteTests {
        @Test
        @DisplayName("should call repository.deleteById method one time")
        void shouldCallDeleteByIdMethodOneTime() {
            service.delete(1L);
            verify(repository, times(1)).deleteById(1L);
        }

        @Test
        @DisplayName("should throw BadParameterException when id is null")
        void shouldThrowBadParameterException_whenIdIsNull() {
            assertThrows(BadParameterException.class, () -> service.delete(null));
        }

        @Test
        @DisplayName("should throw BadParameterException when id is zero or less")
        void shouldThrowBadParameterException_whenIdIsZeroOrLess() {
            assertThrows(BadParameterException.class, () -> service.delete(0L));
        }
    }
}