package sdk.dive.tv.ui.data;

import java.io.Serializable;

/**
 * Created by Noemi on 20/11/2016.
 */

public class ModuleStyleData implements Serializable {

    String property;
    String value;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
