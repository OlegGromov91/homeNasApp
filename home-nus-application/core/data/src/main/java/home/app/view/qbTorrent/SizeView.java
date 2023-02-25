package home.app.view.qbTorrent;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SizeView {
    private Double size;
    private String unit;
}