package sdk.dive.tv.ui.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Noemi on 20/11/2016.
 */

public class CategoriesData implements Serializable {

    String module_name;
    ArrayList<String> cats;

    public String getModuleName() {
        return module_name;
    }

    public void setModuleName(String moduleName) {
        this.module_name = moduleName;
    }

    public ArrayList<String> getCats() {
        return cats;
    }

    public void setCats(ArrayList<String> cats) {
        this.cats = cats;
    }


}
