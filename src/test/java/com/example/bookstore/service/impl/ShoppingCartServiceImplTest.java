package com.example.bookstore.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bookstore.dto.cart.item.CartItemDto;
import com.example.bookstore.dto.cart.item.CartItemDtoWithoutShoppingCartId;
import com.example.bookstore.dto.cart.item.CreateCartItemRequestDto;
import com.example.bookstore.dto.cart.item.UpdateCartItemRequestDto;
import com.example.bookstore.dto.shopping.cart.ShoppingCartDto;
import com.example.bookstore.exceptions.EntityNotFoundException;
import com.example.bookstore.mapper.CartItemMapper;
import com.example.bookstore.mapper.ShoppingCartMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.service.UserService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {
    private static final Long ID_ONE = 1L;
    private static final Long ID_TWO = 2L;
    private static final Long ID_THREE = 3L;
    private static final String BOOK_NOT_FOUND_MESSAGE = "Can't find book by id: ";
    private static final String CART_ITEM_NOT_FOUND_MESSAGE = "Can't find cartItem by id: ";

    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserService userService;
    @Mock
    private ShoppingCartMapper shoppingCartMapper;
    @Mock
    private CartItemMapper cartItemMapper;
    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Test
    public void addCartItem_withExistingBook_shouldReturnCartItemDto() {
        CreateCartItemRequestDto requestDto = getCreateCartItemRequestDto(ID_ONE, 2);
        ShoppingCart shoppingCart = createMockShoppingCart();
        Book book = createMockBook(ID_ONE);
        CartItem cartItem = createMockCartItem(ID_ONE);
        CartItemDto cartItemDto = createCartItemDto(ID_ONE);

        when(bookRepository.findById(ID_ONE)).thenReturn(Optional.of(book));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);
        when(cartItemMapper.toDto(cartItem)).thenReturn(cartItemDto);

        CartItemDto actual = shoppingCartService.addCartItem(requestDto);

        assertEquals(cartItemDto, actual);
        verify(bookRepository).findById(ID_ONE);
        verify(cartItemRepository).save(any(CartItem.class));
        verify(cartItemMapper).toDto(cartItem);
    }

    @Test
    public void addCartItem_withNonExistingBook_shouldThrowEntityNotFoundException() {
        CreateCartItemRequestDto requestDto = getCreateCartItemRequestDto(ID_ONE, 2);
        ShoppingCart shoppingCart = createMockShoppingCart();

        when(bookRepository.findById(ID_ONE)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> shoppingCartService.addCartItem(requestDto));
        assertEquals(BOOK_NOT_FOUND_MESSAGE + requestDto.getBookId(), exception.getMessage());

        verify(bookRepository).findById(ID_ONE);
        verify(cartItemRepository, never()).save(any(CartItem.class));
        verify(cartItemMapper, never()).toDto(any(CartItem.class));
    }

    @Test
    public void get_ShouldReturnShoppingCartDto() {
        ShoppingCart shoppingCart = createMockShoppingCart(ID_ONE);
        ShoppingCartDto expectedDto = createMockShoppingCartDto();

        CartItem firstCartItem = createMockCartItem(ID_TWO);
        CartItem secondCartItem = createMockCartItem(ID_THREE);
        Set<CartItem> cartItems = new HashSet<>(Arrays.asList(firstCartItem, secondCartItem));
        shoppingCart.setCartItems(cartItems);

        CartItemDto firstCartItemDto = createCartItemDto(ID_TWO);
        CartItemDto secondCartItemDto = createCartItemDto(ID_THREE);
        Set<CartItemDtoWithoutShoppingCartId> cartItemDtos = new HashSet<>(Arrays.asList(
                getCartItemDtoWithoutShoppingCartId(firstCartItemDto),
                getCartItemDtoWithoutShoppingCartId(secondCartItemDto)));
        expectedDto.setCartItems(cartItemDtos);

        when(userService.getShoppingCartForCurrentUser()).thenReturn(shoppingCart);
        when(shoppingCartMapper.toDto(shoppingCart)).thenReturn(expectedDto);
        when(cartItemMapper.toCartItemDtoWithoutShoppingCartId(firstCartItem))
                .thenReturn(getCartItemDtoWithoutShoppingCartId(firstCartItemDto));
        when(cartItemMapper.toCartItemDtoWithoutShoppingCartId(secondCartItem))
                .thenReturn(getCartItemDtoWithoutShoppingCartId(secondCartItemDto));

        ShoppingCartDto result = shoppingCartService.get();

        assertEquals(expectedDto.getId(), result.getId());
        assertEquals(expectedDto.getCartItems(), result.getCartItems());
    }

    @Test
    public void updateCartItem_ShouldReturnUpdatedCartItemDto() {
        Long cartItemId = ID_ONE;
        UpdateCartItemRequestDto requestDto = createUpdateCartItemRequestDto(5);
        CartItem cartItem = createMockCartItem(ID_ONE, 2);
        CartItemDto updatedCartItemDto = createCartItemDto(ID_ONE, 5);

        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);
        when(cartItemMapper.toDto(cartItem)).thenReturn(updatedCartItemDto);

        CartItemDto actual = shoppingCartService.update(cartItemId, requestDto);

        assertEquals(updatedCartItemDto.getId(), actual.getId());
        assertEquals(updatedCartItemDto.getQuantity(), actual.getQuantity());
    }

    @Test
    public void updateCartItem_WhenCartItemNotFound_ShouldThrowEntityNotFoundException() {
        Long cartItemId = ID_ONE;
        UpdateCartItemRequestDto requestDto = new UpdateCartItemRequestDto();

        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> shoppingCartService.update(cartItemId, requestDto));

        assertEquals(CART_ITEM_NOT_FOUND_MESSAGE + cartItemId, exception.getMessage());
        verify(cartItemRepository).findById(cartItemId);
        verify(cartItemRepository, never()).save(any(CartItem.class));
        verify(cartItemMapper, never()).toDto(any(CartItem.class));
    }

    @Test
    public void deleteCartItem_ShouldDeleteCartItem() {
        Long cartItemId = 1L;

        shoppingCartService.delete(cartItemId);
        
        verify(cartItemRepository).deleteById(cartItemId);
    }

    private ShoppingCart createMockShoppingCart() {
        return new ShoppingCart();
    }

    private ShoppingCart createMockShoppingCart(Long id) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(id);
        return shoppingCart;
    }

    private Book createMockBook(Long bookId) {
        Book book = new Book();
        book.setId(bookId);
        return book;
    }

    private CartItem createMockCartItem(Long id) {
        CartItem cartItem = new CartItem();
        cartItem.setId(id);
        return cartItem;
    }

    private CartItem createMockCartItem(Long cartItemId, Integer quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemId);
        cartItem.setQuantity(quantity);
        return cartItem;
    }

    private CartItemDto createCartItemDto(Long cartItemId, Integer quantity) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(cartItemId);
        cartItemDto.setQuantity(quantity);
        return cartItemDto;
    }

    private CartItemDto createCartItemDto(Long id) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(id);
        return cartItemDto;
    }

    private ShoppingCartDto createMockShoppingCartDto() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setId(ID_ONE);
        return shoppingCartDto;
    }

    private UpdateCartItemRequestDto createUpdateCartItemRequestDto(Integer quantity) {
        UpdateCartItemRequestDto updateCartItemRequestDto = new UpdateCartItemRequestDto();
        updateCartItemRequestDto.setQuantity(quantity);
        return updateCartItemRequestDto;
    }

    private CreateCartItemRequestDto getCreateCartItemRequestDto(Long bookId, Integer quantity) {
        CreateCartItemRequestDto createCartItemRequestDto = new CreateCartItemRequestDto();
        createCartItemRequestDto.setBookId(bookId);
        createCartItemRequestDto.setQuantity(quantity);
        return createCartItemRequestDto;
    }

    private CartItemDtoWithoutShoppingCartId getCartItemDtoWithoutShoppingCartId(
            CartItemDto cartItemDto) {
        CartItemDtoWithoutShoppingCartId cartItemDtoWithoutShoppingCartId
                = new CartItemDtoWithoutShoppingCartId();
        cartItemDtoWithoutShoppingCartId.setId(cartItemDto.getId());
        return cartItemDtoWithoutShoppingCartId;
    }
}
