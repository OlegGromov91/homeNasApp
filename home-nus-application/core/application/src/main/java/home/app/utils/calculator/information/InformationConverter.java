package home.app.utils.calculator.information;

import home.app.utils.calculator.base.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static home.app.utils.calculator.information.MultipleByteUnits.*;

/**
 * TODO: переделать на побитовые операции
 */
@Component
@Slf4j
public class InformationConverter implements Converter {

    public double convertByteToKilobyte(Number byteValue) {
        return byteValue.doubleValue() / KILOBYTE.getSize();
    }

    public double convertByteToMegabyte(Number byteValue) {
        return byteValue.doubleValue() / MEGABYTE.getSize();
    }

    public double convertByteToGigabyte(Number byteValue) {
        return byteValue.doubleValue() / GIGABYTE.getSize();
    }

    public double convertByteToTerabyte(Number byteValue) {
        return byteValue.doubleValue() / TERABYTE.getSize();
    }

    public Size autoConvert(Number byteValue) {
        if (byteValue.longValue() <= KILOBYTE_MAX_VALUE) {
            return buildSize(KILOBYTE, convertByteToKilobyte(byteValue));
        }
        if (byteValue.longValue() <= MEGABYTE_MAX_VALUE) {
            return buildSize(MEGABYTE, convertByteToMegabyte(byteValue));
        }
        if (byteValue.longValue() <= GIGABYTE_MAX_VALUE) {
            return buildSize(GIGABYTE, convertByteToGigabyte(byteValue));
        }
        if (byteValue.longValue() > GIGABYTE_MAX_VALUE) {
            return buildSize(TERABYTE, convertByteToTerabyte(byteValue));
        }
        return buildSize(BYTE, byteValue.doubleValue());
    }

    private final Size buildSize(MultipleByteUnits units, Double size) {
        return Size.builder()
                .size(size)
                .unit(units.getMetric())
                .build();
    }

}
