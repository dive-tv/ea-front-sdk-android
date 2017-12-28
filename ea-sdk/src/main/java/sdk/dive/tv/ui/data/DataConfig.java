package sdk.dive.tv.ui.data;


import java.io.Serializable;

import sdk.dive.tv.ui.builders.ConfigSection;

/**
 * Created by Tagsonomy S.L. on 16/09/2016.
 */
public class DataConfig implements Serializable {
    ConfigSection[] sections;

    public ConfigSection[] getSections() {
        return sections;
    }

    public void setSections(ConfigSection[] sections) {
        this.sections = sections;
    }

}
