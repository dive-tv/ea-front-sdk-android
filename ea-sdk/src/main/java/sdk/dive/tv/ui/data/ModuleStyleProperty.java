package sdk.dive.tv.ui.data;

/**
 * Created by Noemi on 20/11/2016.
 */

public enum ModuleStyleProperty {

    MAIN_STYLE("MainStyle");

    private final String name;

    ModuleStyleProperty(String s) {
        name = s;
    }

    public String getName() {
        return name;
    }

}
