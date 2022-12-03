package home.app.utils.calculator.information;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.google.common.base.Strings.isNullOrEmpty;
import static home.app.utils.calculator.information.MultipleByteUnits.*;

@Component
@Slf4j
public class InformationConverter {

    public double convertByteToMegabyte(String value) {
        checkValueBeforeConvert(value);
        return Double.parseDouble(value) / MEGABYTE.getSIZE();
    }

    public double convertByteToGigabyte(String value) {
        checkValueBeforeConvert(value);
        return Double.parseDouble(value) / GIGABYTE.getSIZE();
    }

    public double convertByteToTerabyte(String value) {
        checkValueBeforeConvert(value);
        return Double.parseDouble(value) / TERABYTE.getSIZE();
    }

    private void checkValueBeforeConvert(String value) {
        boolean valueIsNotANumber = isNullOrEmpty(value) || !value.matches("\\d");
        if (valueIsNotANumber) {
            throw new ArithmeticException("value " + value + " is not a number");
        }
    }
}
