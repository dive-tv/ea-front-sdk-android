package sdk.dive.tv.ui.builders;

import java.io.Serializable;

/**
 * Created by Tagsonomy S.L. on 15/09/2016.
 */
public class ConfigModule implements Serializable{

    /**
     * The type of the module to be built from this configuration.
     */
    protected String type;

    /**
     * The targets of the module to be built from this configuration.
     */
    protected Target[] targets;

    /**
     * Gets the module type.
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the module type.
     * @param type The type to be set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the targets of this module.
     * @return
     */
    public Target[] getTargets() {
        return targets;
    }

    /**
     * Sets the targets of this module.
     * @param targets The targets to be set.
     */
    public void setTargets(Target[] targets) {
        this.targets = targets;
    }

}
