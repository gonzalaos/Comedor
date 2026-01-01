package ar.uba.fi.comedor.dto;

import java.math.BigDecimal;

import ar.uba.fi.comedor.model.Ingredient;
import ar.uba.fi.comedor.model.UnitOfMeasure;

import jakarta.validation.constraints.*;

public record IngredientRequest(
    @NotBlank(message = "Ingredient name is required")
    @Size(max = 100, message = "Ingredient name must not exceed 100 characters")
    String name,
        
    @NotNull(message = "Current stock is required")
    @PositiveOrZero(message = "Quantity must be positive")
    BigDecimal quantity,

    @NotNull(message = "Minimum stock is required")
    @PositiveOrZero(message = "Minimum quantity cannot be negative")
    BigDecimal minQuantity,

    @NotNull(message = "Unit of measure is required")
    UnitOfMeasure unitOfMeasure
) {
    public Ingredient toEntity() {
        return Ingredient.builder()
                .name(name)
                .quantity(quantity)
                .minQuantity(minQuantity)
                .unitOfMeasure(unitOfMeasure)
                .build();
    }
}
