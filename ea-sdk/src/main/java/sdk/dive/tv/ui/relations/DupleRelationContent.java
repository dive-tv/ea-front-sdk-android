package sdk.dive.tv.ui.relations;

import com.touchvie.sdk.model.DupleData;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public enum DupleRelationContent {

    FROM("getFrom"),
    TO("getTo"),
    BOTH("getFrom|getTo");

    private final String methods;

    DupleRelationContent(String methods) {
        this.methods = methods;
    }

    public List<Method> getMethodToInvoke(){
        String[] methodsToInvoke = methods.split("|");
        List<Method> result = new ArrayList<>(methodsToInvoke.length);
        for(String methodToInvoke : methodsToInvoke) {
            try {
                result.add(DupleData.class.getMethod(methodToInvoke));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
