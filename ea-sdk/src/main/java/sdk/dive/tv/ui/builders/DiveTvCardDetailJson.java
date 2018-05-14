package sdk.dive.tv.ui.builders;

import android.content.Context;

import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import sdk.dive.tv.ui.data.ModuleStyle;
import sdk.dive.tv.ui.managers.DiveTVTvCardDetailManager;

/**
 * Created by Tagsonomy S.L. on 19/07/2017.
 */

public class DiveTvCardDetailJson extends CardDetailJson {
    /**
     * Default constructor
     *
     * @param context
     * @param customCardDetailManager
     */
    public DiveTvCardDetailJson(Context context, String customCardDetailManager) {
        super(context, customCardDetailManager);
    }

    public CardDetailJson loadStyleConfig(JSONArray styleConfig) {
     /*   ModuleStyle[] styles;
        ModuleStyle idStyle = null;
        if (styleConfig == null) {
            return null;
        } else {
            styles = new GsonBuilder().create().fromJson(styleConfig.toString(), ModuleStyle[].class); //TODO: test!!!
            if (styles == null) {
                return null;
            } else {
                if (styles.length > 0) {
                    for (ModuleStyle style : styles) {
                        if (style.getModuleName().equals("carddetail")) {
                            idStyle = style;
                        }
                    }
                }
            }
            return idStyle;
        }*/
        if (styleConfig == null) {
            requestDataStyle();
        } else {
            ModuleStyle[] styles = new GsonBuilder().create().fromJson(styleConfig.toString(), ModuleStyle[].class); //TODO: test!!!
            if (styles == null) {
                requestDataStyle();
            } else {
                if (styles.length > 0) {
                    for (ModuleStyle style : styles) {
                        if (style.getModuleName().equals("carddetail")) {
                            idStyle.put(style.getModuleName(), style);
                        }
                    }
                }
            }
        }

        return this;
    }

    @Override
    protected void composeCardDetail() {

        //First off all get the main section from the dictionary.
        if (idSection != null) {
            ConfigSection main = idSection.get(mainSectionKey);
            if (main == null) {
                return;
            }

            validator.validate(card, idSection, isCarousel);

            new DiveTVTvCardDetailManager(context, card, idSection, "main", mManager, mLayout, mUpperLayout, mToolBar, idStyle, cardDetailActivity, isCarousel);
        }

    }

    public boolean isCardLiked() {
        return card.getUser().getIsLiked();
    }
}
