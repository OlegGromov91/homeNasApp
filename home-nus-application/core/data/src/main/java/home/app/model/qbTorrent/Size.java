package home.app.model.qbTorrent;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Size {
    private Double size;
    private String unit;
}