package home.app.utils.calculator.information;

import lombok.Getter;

@Getter
public enum MultipleByteUnits {

    BYTE("байт", "Б", Math.pow(2, 0)),
    KILOBYTE("килобайт", "Кбайт", Math.pow(2, 10)),
    MEGABYTE("мегабайт", "Мбайт", Math.pow(2, 20)),
    GIGABYTE("гигабайт", "Гбайт", Math.pow(2, 30)),
    TERABYTE("терабайт", "Тбайт", Math.pow(2, 40));

    private final String NAME;
    private final String METRIC;
    private final double SIZE;

    MultipleByteUnits(String NAME, String METRIC, double SIZE) {
        this.NAME = NAME;
        this.METRIC = METRIC;
        this.SIZE = SIZE;
    }
}
