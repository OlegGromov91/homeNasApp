package home.app.utils.calculator.information;

import home.app.model.qbTorrent.Size;
import home.app.utils.calculator.base.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

import static home.app.utils.calculator.information.MultipleByteUnits.*;

/**
 * TODO: переделать на побитовые операции
 */
@Slf4j
@Component
public class InformationConverter implements Converter {

    public double convertByteToKilobyte(@NotNull Number byteValue) {
        return doFormat(byteValue.doubleValue() / KILOBYTE.getSize());
    }

    public double convertByteToMegabyte(@NotNull Number byteValue) {
        return doFormat(byteValue.doubleValue() / MEGABYTE.getSize());
    }

    public double convertByteToGigabyte(@NotNull Number byteValue) {
        return doFormat(byteValue.doubleValue() / GIGABYTE.getSize());
    }

    public double convertByteToTerabyte(@NotNull Number byteValue) {
        return doFormat(byteValue.doubleValue() / TERABYTE.getSize());
    }

    public Size autoConvert(@NotNull Number byteValue) {
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
        return buildSize(BYTE, doFormat(byteValue.doubleValue()));
    }

    private final Size buildSize(MultipleByteUnits units, Double size) {
        return Size.builder()
                .size(size)
                .unit(units.getMetric())
                .build();
    }

}
