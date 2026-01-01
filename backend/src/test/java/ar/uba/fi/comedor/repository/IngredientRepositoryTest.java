package ar.uba.fi.comedor.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ar.uba.fi.comedor.model.Ingredient;
import ar.uba.fi.comedor.model.UnitOfMeasure;

@DataJpaTest
public class IngredientRepositoryTest {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    public void saveAndFindByNameShouldWork() {
        Ingredient ingredient = Ingredient.builder()
            .name("Tomate")
            .quantity(BigDecimal.TEN)
            .minQuantity(BigDecimal.ONE)
            .unitOfMeasure(UnitOfMeasure.KG)
            .build();
        ingredientRepository.save(ingredient);

        assertTrue(ingredientRepository.existsByName("Tomate"));
        assertFalse(ingredientRepository.existsByName("Cebolla"));
    }
}
