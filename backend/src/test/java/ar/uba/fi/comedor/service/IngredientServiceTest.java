package ar.uba.fi.comedor.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.uba.fi.comedor.dto.IngredientRequest;
import ar.uba.fi.comedor.exception.ResourceAlreadyExistsException;
import ar.uba.fi.comedor.model.Ingredient;
import ar.uba.fi.comedor.model.UnitOfMeasure;
import ar.uba.fi.comedor.repository.IngredientRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceTest {
    @Mock
    private IngredientRepository repository;

    @InjectMocks
    private IngredientService service;

    @Test
    public void createIngredientShouldWorkCorrectly() {
        IngredientRequest request = new IngredientRequest("Sal", BigDecimal.TEN, BigDecimal.ONE, UnitOfMeasure.G);
        Ingredient ingredient = request.toEntity();
        when(repository.existsByName("Sal")).thenReturn(false);
        when(repository.save(any(Ingredient.class))).thenReturn(ingredient);

        Ingredient ingredientCreated = service.createIngredient(request);

        assertEquals("Sal", ingredientCreated.getName());
        assertEquals(new BigDecimal("10"), ingredientCreated.getQuantity());
        assertEquals(new BigDecimal("1"), ingredientCreated.getMinQuantity());
        assertEquals(UnitOfMeasure.G, ingredientCreated.getUnitOfMeasure());
    }

    @Test
    public void createIngredientShouldThrowExceptionWhenCreatingDuplicateIngredient() {
        IngredientRequest request = new IngredientRequest("Sal", BigDecimal.TEN, BigDecimal.ONE, UnitOfMeasure.G);
        when(repository.existsByName("Sal")).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> service.createIngredient(request));
    }

    @Test
    public void consumeStockShouldWorkCorrectly() {
        Ingredient ingredient = Ingredient.builder()
            .name("Harina")
            .quantity(new BigDecimal("1000"))
            .unitOfMeasure(UnitOfMeasure.G)
            .build();
        
        when(repository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(repository.save(any(Ingredient.class))).thenReturn(ingredient);

        Ingredient result = service.consumeStock(1L, new BigDecimal("0.5"), UnitOfMeasure.KG);

        assertEquals(new BigDecimal("500.00"), result.getQuantity());
        verify(repository).save(any());
    }

    @Test
    public void addStockShouldWorkCorrectly() {
        Ingredient ingredient = Ingredient.builder()
            .name("Harina")
            .quantity(new BigDecimal("1000"))
            .unitOfMeasure(UnitOfMeasure.G)
            .build();
        
        when(repository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(repository.save(any(Ingredient.class))).thenReturn(ingredient);

        Ingredient result = service.addStock(1L, new BigDecimal("0.5"), UnitOfMeasure.KG);

        assertEquals(new BigDecimal("1500.00"), result.getQuantity());
        verify(repository).save(any());
    }
}
