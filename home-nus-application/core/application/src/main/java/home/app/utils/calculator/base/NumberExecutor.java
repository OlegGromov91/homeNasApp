package home.app.utils.calculator.base;

import java.text.DecimalFormat;

import static com.google.common.base.Strings.isNullOrEmpty;

public interface NumberExecutor {
    DecimalFormat FORMATTER = new DecimalFormat("#.##");

    default void checkValueConsistency(String value) {
        boolean valueIsNotANumber = isNullOrEmpty(value) || !value.matches("\\d");
        if (valueIsNotANumber) {
            throw new ArithmeticException("value " + value + " is not a number");
        }
    }

    default Double doFormat(Double value) {
        String formatter = FORMATTER.format(value);
        return Double.valueOf(formatter.replaceAll(",", "."));
    }
}
