package sdk.dive.tv.ui.builders;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 */
public class ConfigSection implements Serializable {


    /**
     * List of the configurations for the modules in this Section
     */
    protected ArrayList<ConfigModule> modules = new ArrayList<>();

    /**
     * The title of this section
     */
    protected String title;

    /**
     * Whether this section is the main one of a card detail.
     */
    protected boolean main;

    protected boolean isValidated;

    protected boolean isEmpty;
    /**
     * Default constructor
     */
    public ConfigSection() {
    }

    /**
     * Sets the config of the modules for the section to be built from this configuration.
     * @param modules
     */

    public void setModules(ArrayList<ConfigModule> modules) {
        this.modules = modules;
    }


    /**
     * Adds a new module to the section
     *
     * @param moduleType type of the module
     * @return
     */
    public void addModule(String moduleType) {
        ConfigModule newModule = new ConfigModule();
        newModule.setType(moduleType);
        modules.add(newModule);
    }

    /**
     * Add a new Navigation Module to another Section
     *
     * @param target Target Section
     */
    public void addNavigation(Target target) {
        ConfigModule newModule = new ConfigModule();
        newModule.setType("navigation");
        Target[] targetArray = new Target[1];
        targetArray[0] = target;
        newModule.setTargets(targetArray);
        modules.add(newModule);
    }

    /**
     * Add a new Navigation Module to another Section
     *
     * @param targets Array of Target Sections
     */
    public void addTabs(Target[] targets) {
        ConfigModule newModule = new ConfigModule();
        newModule.setType("tabs");
        Target[] targetArray = new Target[targets.length];
        System.arraycopy(targets, 0, targetArray, 0, targetArray.length);
        newModule.setTargets(targetArray);
        modules.add(newModule);
    }


    /**
     * Gets all the configurations for the modules of this section.
     * @return
     */
    public ArrayList<ConfigModule> getConfigModules() {
        return modules;
    }

    /**
     * Gets the title of this section.
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of this section.
     * @param title The title to be set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Checks whether this section is the main one of a card detail.
     * @return
     */
    public boolean isMain() {
        return main;
    }

    /**
     * Sets whether this section is the main one of a card detail.
     * @param main true if is the main section, false otherwise.
     */
    public void setMain(boolean main) {
        this.main = main;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validate) {
        isValidated = validate;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}