package home.app.util;

import lombok.Getter;
import org.json.JSONObject;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component()
@Getter
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RawTorrent {

    private JSONObject rawData;

    public void writeData(String rawData) {
        this.rawData = new JSONObject(rawData);
    }

}
