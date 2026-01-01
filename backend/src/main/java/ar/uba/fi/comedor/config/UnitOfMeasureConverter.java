package ar.uba.fi.comedor.config;

import ar.uba.fi.comedor.model.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureConverter implements Converter<String, UnitOfMeasure> {
    @Override
    public UnitOfMeasure convert(String source) {
        if(source.isEmpty()) {
            return null;
        }
        return UnitOfMeasure.fromValue(source);
    }
}

