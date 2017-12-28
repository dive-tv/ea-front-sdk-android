package sdk.dive.tv.validators;

import com.touchvie.sdk.model.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import sdk.dive.tv.ui.builders.ConfigModule;
import sdk.dive.tv.ui.builders.ConfigSection;
import sdk.dive.tv.ui.views.Module;

import static sdk.dive.tv.ui.adapters.ModulesAdapter.DEFAULT_MODULE_PACKAGE;

/**
 * Created by Tagsonomy S.L. on 16/09/2016.
 */
public class ModuleValidator {

    public ModuleValidator() {

    }

    public void validate(Card cardDetail, HashMap<String, ConfigSection> idSection, boolean isCarousel) {

        for (String id : idSection.keySet()) {
            if (!idSection.get(id).isValidated())
                validateSection(cardDetail, idSection, id, isCarousel);
        }

    }

    private boolean validateSection(Card cardDetail, HashMap<String, ConfigSection> idSection, String id, boolean isCarousel) {
        ArrayList<ConfigModule> configModules = idSection.get(id).getConfigModules();
        boolean hasBanner = false;
        String bannerSize = "";
        int bannerPosition = 0;


        for (int i = 0; i < configModules.size(); i++) {
            String moduleName = configModules.get(i).getType();
        }

        Iterator<ConfigModule> iter = configModules.iterator();
        boolean sectionResult = false;
        while (iter.hasNext()) {
            ConfigModule configModule = iter.next();

            String moduleName = configModule.getType();

            if (!moduleName.contains(".")) {
                moduleName = DEFAULT_MODULE_PACKAGE + moduleName;
            }
            try {
                boolean result;
                if (isCarousel && "MovieHeader".equals(moduleName)) {
                    moduleName = "MovieHeaderSmall";
                }

                if (configModule.getTargets() != null && configModule.getTargets().length > 0 && configModule.getType() != null) {
                    if (validateSection(cardDetail, idSection, configModule.getTargets()[0].getSectionId(), isCarousel)) {
                        result = true;
                        sectionResult = true;
                    } else {
                        result = ((Module) (Class.forName(moduleName).newInstance())).validate(cardDetail, isCarousel);
                        if (result)
                            sectionResult = true;
                    }
                    idSection.get(configModule.getTargets()[0].getSectionId()).setValidated(true);
                    if (!sectionResult)
                        iter.remove();
                } else {
                    result = ((Module) (Class.forName(moduleName).newInstance())).validate(cardDetail, isCarousel);
                    if (result)
                        sectionResult = true;
                }

                if (!result) {
                    iter.remove();
                }
                continue;
            } catch (ClassNotFoundException e) {
                iter.remove();
                continue;
            } catch (InstantiationException e) {
                iter.remove();
                continue;
            } catch (IllegalAccessException e) {
                iter.remove();
                continue;
            }

        }
        idSection.get(id).setEmpty(!sectionResult);
        return sectionResult;
    }
}
