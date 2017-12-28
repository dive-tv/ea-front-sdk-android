package sdk.dive.tv.ui.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Noemi on 20/11/2016.
 */

public class ModuleStyle implements Serializable {

    String module_name;
    ArrayList<ModuleStyleData> styles;

    HashMap<String, ModuleStyleData> idModuleStyleData;

    public String getModuleName() {
        return module_name;
    }

    public void setModuleName(String moduleName) {
        this.module_name = moduleName;
    }

    public ArrayList<ModuleStyleData> getStyles() {
        return styles;
    }

    public void setStyles(ArrayList<ModuleStyleData> styles) {
        this.styles = styles;
    }

    public ModuleStyleData getModuleStyleDataFromId(String id){
        if(idModuleStyleData==null){
            idModuleStyleData= new HashMap<>();
            for(ModuleStyleData style: styles){
                idModuleStyleData.put(style.getProperty(),style);
            }
        }
        return idModuleStyleData.get(id);
    }

    public HashMap<String, ModuleStyleData> getIdModuleStyleData(){
        if(idModuleStyleData==null){
            idModuleStyleData= new HashMap<>();
            for(ModuleStyleData style: styles){
                idModuleStyleData.put(style.getProperty(),style);
            }
        }
        return idModuleStyleData;
    }
}
