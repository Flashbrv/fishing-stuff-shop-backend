package com.example.fishingstuffshopbackend.service;

import com.example.fishingstuffshopbackend.domain.Category;
import com.example.fishingstuffshopbackend.exception.BadParameterException;
import com.example.fishingstuffshopbackend.exception.CategoryNotFoundException;
import com.example.fishingstuffshopbackend.exception.SuchCategoryExistException;
import com.example.fishingstuffshopbackend.repository.CategoryRepository;
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

import static com.example.fishingstuffshopbackend.TestUtils.setId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    private CategoryService service;

    private Category hook;

    CategoryServiceTest() {
        hook = Category.builder()
                .title("Hook")
                .build();
        setId(hook, 1L);
    }

    @BeforeEach
    void initService() {
        service = new CategoryServiceImpl(repository);
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

            List<Category> categories = service.findAll();

            assertThat(categories.size())
                    .withFailMessage(() -> "Should be empty, but size was " + categories.size())
                    .isEqualTo(0);
        }

        @Test
        @DisplayName("should return two entries")
        void shouldReturnListOfTwoEntries() {
            Category pole = Category.builder()
                    .title("Pole")
                    .build();

            when(repository.findAll()).thenReturn(
                    List.of(hook, pole)
            );

            List<Category> categories = service.findAll();

            assertThat(categories.size())
                    .withFailMessage(() -> "Should contains two entries, but was " + categories.size())
                    .isEqualTo(2);
        }

        @Test
        @DisplayName("check returned category")
        void checkReturnedCategory() {
            when(repository.findAll()).thenReturn(
                    List.of(hook)
            );

            List<Category> categories = service.findAll();

            assertThat(categories.size())
                    .withFailMessage(() -> "Should contains one entry, but was " + categories.size())
                    .isEqualTo(1);

            Category category = categories.get(0);
            assertThat(category.getId()).isEqualTo(1L);
            assertThat(category.getTitle()).isEqualTo("Hook");
        }
    }

    @Nested
    @DisplayName("findById")
    class FindByIdTests {
        @Test
        @DisplayName("should return one category")
        void shouldReturnOneCategory() {
            when(repository.findById(1L)).thenReturn(Optional.of(hook));

            Category category = service.findById(1L);

            assertThat(category.getId()).isEqualTo(1L);
            assertThat(category.getTitle()).isEqualTo("Hook");
        }

        @Test
        @DisplayName("should throw CategoryNotFoundException when entity not founded")
        void shouldThrowCategoryNotFoundException_WhenCategoryNotFounded() {
            when(repository.findById(1L)).thenReturn(Optional.empty());

            CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class,
                    () -> service.findById(1L));
            assertThat(exception.getMessage()).isEqualTo("Category with id=1 not founded");
        }
    }

    @Nested
    @DisplayName("update")
    class UpdateTests {
        @Test
        @DisplayName("should return updated category")
        void shouldReturnUpdatedCategory() {
            when(repository.findById(1L)).thenReturn(Optional.of(hook));
            when(repository.save(any(Category.class))).then(invocation -> invocation.getArgument(0));

            Category toUpdate = Category.builder()
                    .title("Gak")
                    .build();

            Category category = service.update(1L, toUpdate);

            assertThat(category.getTitle()).isEqualTo("Gak");
            verify(repository, times(1)).findById(1L);
            verify(repository, times(1)).save(hook);
        }

        @Test
        @DisplayName("should throws CategoryNotFoundException when category does not exist")
        void shouldThrowCategoryNotFoundException_WhenCategoryNotFounded() {
            when(repository.findById(1L)).thenReturn(Optional.empty());
            Category toUpdate = Category.builder()
                    .title("Gak")
                    .build();

            assertThrows(CategoryNotFoundException.class, () -> service.update(1L, toUpdate));

            verify(repository, times(1)).findById(1L);
            verify(repository, never()).save(any());
        }

        @Test
        @DisplayName("should throws BadParameterException when id is null")
        void shouldThrowBadParameterException_WhenIdIsNull() {
            Category toUpdate = Category.builder()
                    .title("Gak")
                    .build();
            assertThrows(BadParameterException.class, () -> service.update(0, toUpdate));
        }

        @Test
        @DisplayName("should throws BadParameterException when category to update is null")
        void shouldThrowBadParameterException_WhenCategoryToUpdateIsNull() {
            when(repository.findById(1L)).thenReturn(Optional.of(hook));

            assertThrows(BadParameterException.class, () -> service.update(1L, null));
            verify(repository, times(1)).findById(1L);
        }

        @Test
        @DisplayName("should throws BadParameterException when new title is null")
        void shouldThrowBadParameterException_WhenNewTitleIsNull() {
            when(repository.findById(1L)).thenReturn(Optional.of(hook));

            assertThrows(BadParameterException.class, () -> service.update(1L, null));
            verify(repository, times(1)).findById(1L);
        }

        @Test
        @DisplayName("should throws BadParameterException when new title is blank")
        void shouldThrowBadParameterException_WhenNewNameIsBlank() {
            when(repository.findById(1L)).thenReturn(Optional.of(hook));

            Category toUpdate = Category.builder()
                    .title("")
                    .build();

            assertThrows(BadParameterException.class, () -> service.update(1L, toUpdate));
            verify(repository, times(1)).findById(1L);
        }
    }

    @Nested
    @DisplayName("create")
    class CreateTests {
        @Test
        @DisplayName("should return saved new category")
        void shouldReturnSavedNewCategory() {
            when(repository.save(any(Category.class))).thenReturn(hook);

            Category category = service.create(hook);

            assertThat(category.getTitle()).isEqualTo(hook.getTitle());
            assertThat(category.getId()).isEqualTo(hook.getId());
            verify(repository, times(1)).save(any());
        }

        @Test
        @DisplayName("should throws BadParameterException when category toCreate is null")
        void shouldThrowBadParameterException_whenCategoryToCreateIsNull() {
            assertThrows(BadParameterException.class, () -> service.create(null));
        }

        @Test
        @DisplayName("should throws BadParameterException when title is null")
        void shouldThrowBadParameterException_whenTitleIsNull() {
            Category toCreate = Category.builder().build();
            assertThrows(BadParameterException.class, () -> service.create(toCreate));
        }

        @Test
        @DisplayName("should throws BadParameterException when title is blank")
        void shouldThrowBadParameterException_whenTitleIsBlank() {
            Category toCreate = Category.builder()
                    .title("")
                    .build();
            assertThrows(BadParameterException.class, () -> service.create(toCreate));
        }

        @Test
        @DisplayName("should throws SuchCategoryExistException")
        void shouldProductTypeEntityWithSuchNameExist() {
            when(repository.save(any(Category.class)))
                    .thenThrow(new SuchCategoryExistException(hook.getTitle()));

            assertThrows(SuchCategoryExistException.class, () -> service.create(hook));
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
        @DisplayName("should throw BadParameterException when id is zero or less")
        void shouldThrowBadParameterException_whenIdIsZeroOrLess() {
            assertThrows(BadParameterException.class, () -> service.delete(0L));
        }
    }


}