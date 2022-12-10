package home.app.utils.calculator.base;

import static com.google.common.base.Strings.isNullOrEmpty;

public interface Calculator {

    default void checkValueConsistency(String value) {
        boolean valueIsNotANumber = isNullOrEmpty(value) || !value.matches("\\d");
        if (valueIsNotANumber) {
            throw new ArithmeticException("value " + value + " is not a number");
        }
    }
}
