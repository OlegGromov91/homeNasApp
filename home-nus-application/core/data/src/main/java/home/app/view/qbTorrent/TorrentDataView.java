package home.app.view.qbTorrent;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class TorrentDataView {

    private Long id;
    private String hash;
    private String name;

}
