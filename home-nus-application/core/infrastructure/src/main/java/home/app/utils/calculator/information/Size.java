package home.app.utils.calculator.information;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
public class Size {
    private Double size;
    private String unit;
}
