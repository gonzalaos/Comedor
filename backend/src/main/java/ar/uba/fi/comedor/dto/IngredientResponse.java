package ar.uba.fi.comedor.dto;

import java.math.BigDecimal;

import ar.uba.fi.comedor.model.Ingredient;
import ar.uba.fi.comedor.model.UnitOfMeasure;

public record IngredientResponse(
    String name,
    BigDecimal quantity,
    BigDecimal minQuantity,
    UnitOfMeasure unitOfMeasure
) {
    public static IngredientResponse fromEntity(Ingredient ingredient) {
        return new IngredientResponse(
            ingredient.getName(),
            ingredient.getQuantity(),
            ingredient.getMinQuantity(),
            ingredient.getUnitOfMeasure()
        );
    }
}
