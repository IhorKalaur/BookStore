package com.example.bookstore.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bookstore.dto.category.CategoryDto;
import com.example.bookstore.dto.category.CreateCategoryRequestDto;
import com.example.bookstore.exceptions.EntityNotFoundException;
import com.example.bookstore.mapper.CategoryMapper;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    private static final Long ID_ONE = 1L;
    private static final Long ID_TWO = 2L;
    private static final String FIRST_CATEGORY_NAME = "FirstCategory";
    private static final String UPDATED_FIRST_CATEGORY_NAME = "FirstCategoryUpdated";
    private static final String SECOND_CATEGORY_NAME = "SecondCategory";
    private static final String CATEGORY_NOT_FOUND_MESSAGE = "Can't find category by id: ";

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void findAll_withCategoriesInRepository_shouldReturnCategoryDtoList() {
        Pageable pageable = Pageable.unpaged();
        Category firstCategory = getCategory(ID_ONE, FIRST_CATEGORY_NAME);
        Category secondCategory = getCategory(ID_TWO, SECOND_CATEGORY_NAME);
        CategoryDto firstCategoryDto = getCategoryDto(ID_ONE, FIRST_CATEGORY_NAME);
        CategoryDto secondCategoryDto = getCategoryDto(ID_TWO, SECOND_CATEGORY_NAME);

        when(categoryRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(firstCategory,secondCategory)));
        when(categoryMapper.toDto(firstCategory)).thenReturn(firstCategoryDto);
        when(categoryMapper.toDto(secondCategory)).thenReturn(secondCategoryDto);

        List<CategoryDto> actual = categoryService.findAll(pageable);

        assertEquals(2, actual.size());
        assertEquals(firstCategoryDto, actual.get(0));
        assertEquals(secondCategoryDto, actual.get(1));
    }

    @Test
    public void findAll_withEmptyRepository_shouldReturnEmptyList() {
        Pageable pageable = Pageable.unpaged();

        when(categoryRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of()));

        List<CategoryDto> actual = categoryService.findAll(pageable);

        assertEquals(0, actual.size());
    }

    @Test
    public void getById_withExistingCategory_shouldReturnCategoryDto() {
        Long categoryId = ID_ONE;
        Category category = new Category();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        CategoryDto categoryDto = new CategoryDto();
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto result = categoryService.getById(categoryId);

        assertEquals(categoryDto, result);
    }

    @Test
    public void getById_withNonExistingCategory_shouldThrowEntityNotFoundException() {
        Long categoryId = ID_ONE;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> categoryService.getById(categoryId));
        assertEquals(CATEGORY_NOT_FOUND_MESSAGE + categoryId, exception.getMessage());
    }

    @Test
    public void save_withValidRequestDto_shouldReturnCategoryDto() {
        CreateCategoryRequestDto requestDto = getCreateCategoryRequestDto(FIRST_CATEGORY_NAME);

        Category categoryEntity = getCategory(ID_ONE, FIRST_CATEGORY_NAME);
        CategoryDto categoryDto = getCategoryDto(categoryEntity.getId(), categoryEntity.getName());

        when(categoryMapper.toEntity(requestDto)).thenReturn(categoryEntity);
        when(categoryRepository.save(any(Category.class))).thenReturn(categoryEntity);
        when(categoryMapper.toDto(categoryEntity)).thenReturn(categoryDto);

        CategoryDto result = categoryService.save(requestDto);

        assertEquals(requestDto.getName(), result.getName());
    }

    @Test
    public void update_withExistingCategory_shouldReturnUpdatedCategoryDto() {
        Long categoryId = ID_ONE;
        CreateCategoryRequestDto requestDto
                = getCreateCategoryRequestDto(UPDATED_FIRST_CATEGORY_NAME);
        Category existingCategory = getCategory(categoryId, FIRST_CATEGORY_NAME);
        CategoryDto categoryDto = getCategoryDto(categoryId, requestDto.getName());

        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        when(categoryMapper.toEntity(requestDto)).thenReturn(existingCategory);
        when(categoryRepository.save(any(Category.class))).thenReturn(existingCategory);
        when(categoryMapper.toDto(existingCategory)).thenReturn(categoryDto);

        CategoryDto actual = categoryService.update(categoryId, requestDto);

        assertEquals(requestDto.getName(), actual.getName());
    }

    @Test
    public void update_withNonExistingCategory_shouldThrowEntityNotFoundException() {
        Long categoryId = ID_ONE;
        CreateCategoryRequestDto requestDto
                = getCreateCategoryRequestDto(UPDATED_FIRST_CATEGORY_NAME);

        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> categoryService.update(categoryId, requestDto));
        assertEquals(CATEGORY_NOT_FOUND_MESSAGE + categoryId, exception.getMessage());
    }

    @Test
    public void deleteById_withExistingCategory_shouldDeleteCategory() {
        Long categoryId = ID_ONE;

        categoryService.deleteById(categoryId);

        verify(categoryRepository).deleteById(categoryId);
    }

    private Category getCategory(Long id, String name) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }

    private CategoryDto getCategoryDto(Long id, String name) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(id);
        categoryDto.setName(name);
        return categoryDto;
    }

    private CreateCategoryRequestDto getCreateCategoryRequestDto(String name) {
        CreateCategoryRequestDto categoryRequestDto = new CreateCategoryRequestDto();
        categoryRequestDto.setName(name);
        return categoryRequestDto;
    }
}
