package ar.uba.fi.comedor.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import ar.uba.fi.comedor.exception.IncompatibleUnitsException;
import ar.uba.fi.comedor.exception.InvalidUnitException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum UnitOfMeasure {
    G("Grams", MeasurementType.MASS, 1.0),
    KG("Kilograms", MeasurementType.MASS, 1000.0),
    L("Liters", MeasurementType.VOLUME, 1000.0),
    ML("Milliliters", MeasurementType.VOLUME, 1.0),
    UNIT("Unit", MeasurementType.DISCRETE, 1.0);

    private final String label;
    private final MeasurementType type;
    private final double factorToBase;

    UnitOfMeasure(String label, MeasurementType type, double factorToBase) {
        this.label = label;
        this.type = type;
        this.factorToBase = factorToBase;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    public MeasurementType getType() {
        return type;
    }
    
    public BigDecimal convertTo(BigDecimal amount, UnitOfMeasure targetUnit) {
        if(this.type != targetUnit.type) {
            throw new IncompatibleUnitsException("Incompatible units: " + this.label + " and " + targetUnit.label);
        }

        double factor = this.factorToBase / targetUnit.factorToBase;
        return amount.multiply(BigDecimal.valueOf(factor))
                     .setScale(2, RoundingMode.HALF_UP);
    }

    @JsonCreator
    public static UnitOfMeasure fromValue(String value) {
        for (UnitOfMeasure unit : UnitOfMeasure.values()) {
            if (unit.label.equalsIgnoreCase(value) || unit.name().equalsIgnoreCase(value)) {
                return unit;
            }
        }
        throw new InvalidUnitException("Invalid unit of measure: " + value);
    }

    public enum MeasurementType {
        MASS, VOLUME, DISCRETE
    }
}
