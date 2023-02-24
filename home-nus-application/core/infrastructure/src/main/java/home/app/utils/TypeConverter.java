package home.app.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class TypeConverter {

    @Autowired
    private ConversionService conversionService;

    public <T> T convert(Object value, Class<T> destination) {
        return conversionService.convert(value, destination);
    }
}
