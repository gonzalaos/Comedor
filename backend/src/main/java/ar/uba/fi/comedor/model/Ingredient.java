package ar.uba.fi.comedor.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import ar.uba.fi.comedor.exception.InsufficientStockException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long ingredientId;

    @Size(max = 100)
    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;

    @NotNull
    @Column(name = "minimum_quantity", nullable = false, precision = 10, scale = 2)
    private BigDecimal minQuantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unit_of_measure", nullable = false)
    private UnitOfMeasure unitOfMeasure;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private boolean hasSufficientStock(BigDecimal requiredAmount) {
        return quantity.compareTo(requiredAmount) >= 0;
    }

    private void amountNegativeCheck(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The amount to be consumed must be positive.");
        }
    }

    public boolean isLowStock() {
        return quantity.compareTo(minQuantity) <= 0;
    }

    public void consumeStock(BigDecimal amount, UnitOfMeasure unitOfMeasure) {
        amountNegativeCheck(amount);

        BigDecimal amountInStoredUnit = unitOfMeasure.convertTo(amount, this.unitOfMeasure);

        if(!hasSufficientStock(amountInStoredUnit)) {
            throw new InsufficientStockException("Insufficient stock for: " + this.name + 
            ". Required: " + amountInStoredUnit + " " + this.unitOfMeasure);
        }

        this.quantity = this.quantity.subtract(amountInStoredUnit);
    }

    public void addStock(BigDecimal amount, UnitOfMeasure unitOfMeasure) {
        amountNegativeCheck(amount);

        BigDecimal amountInStoredUnit = unitOfMeasure.convertTo(amount, this.unitOfMeasure);

        this.quantity = this.quantity.add(amountInStoredUnit);
    }
}
