package sdk.dive.tv.ui.builders;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import com.touchvie.sdk.ApiCallback;
import com.touchvie.sdk.ApiException;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.RelationModule;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sdk.client.dive.tv.SdkClient;
import sdk.client.dive.tv.rest.callbacks.ClientCallback;
import sdk.client.dive.tv.rest.enums.RestAPIError;
import sdk.dive.tv.R;
import sdk.dive.tv.ui.data.ModuleStyle;
import sdk.dive.tv.ui.managers.CardDetailManager;
import sdk.dive.tv.validators.ModuleValidator;

/**
 * Class to inherit the card detail builders common methods.
 */
public abstract class BaseCardDetailBuilder<T extends BaseCardDetailBuilder<T>> {


    protected FragmentManager mManager;
    protected LinearLayout mLayout;
    protected LinearLayout mUpperLayout;
    protected Toolbar mToolBar;
    protected boolean cardWithRels = false;
    protected boolean isCarousel = false;
    protected Activity cardDetailActivity;
    protected ArrayList<RelationModule> relations;

    protected String customCardDetailManager;

    /**
     * Method to get the object of a class derived from BaseCardDetailBuilder.
     *
     * @return
     */
    protected abstract T getThis();

    /**
     * JSON with the style configuration: colors, fonts...
     */
    protected JSONObject styleConfig;


    /**
     * Dictionary with the config sections created for the card detail without beeing validated.
     * Key: Name of the section
     * Value: section
     */
    protected HashMap<String, ConfigSection> idSection;


    protected HashMap<String, ModuleStyle> idStyle; //Module name as key

    /**
     * Data received from the server.
     */
    protected Card card;


    /**
     * Key to the main section of the card detail.
     */
    protected String mainSectionKey = "main";


    /**
     * Sets wheter the card detail must show all the modules by default or not.
     */
    protected boolean buildDefault = false;


    /**
     * Object to validate the modules to be added.
     */
    protected ModuleValidator validator = null;

    protected Context context = null;


    /**
     * Default constructor
     */
    public BaseCardDetailBuilder(Context ctx) {
        context = ctx;
        validator = new ModuleValidator();
        idStyle = new HashMap<>();
    }

    /**
     * Requests the server the modules for the given card.
     *
     * @param cardID    the card identifier.
     * @param versionId the card version.
     * @param manager   fragment manager.
     * @param container LinearLayout where will show cardDetail.
     * @return
     */
    public void build(final String cardID, String versionId, FragmentManager manager, LinearLayout container, LinearLayout upperContainer, boolean cardWithRels, boolean isCarousel, Activity activity) {

        buildDefault = false;
        this.mManager = manager;
        this.mLayout = container;
        this.mUpperLayout = upperContainer;
        this.cardWithRels = cardWithRels;
        this.isCarousel = isCarousel;
        this.cardDetailActivity = activity;

        ClientCallback<Card> callback = new ClientCallback<Card>() {
            @Override
            public void onFailure(RestAPIError restAPIError) {

            }

            @Override
            public void onSuccess(Card response) {
                card = response;
                if (relations != null && relations.size() > 0) {
                    if (response != null && response.getRelations() != null && response.getRelations().size() > 0) {
                        relations.addAll(response.getRelations());
                    }
                    RelationModule[] relationModuleList = relations.toArray(new RelationModule[relations.size()]);

                    card.setRelations(Arrays.asList(relationModuleList));

                }

                composeCardDetail();
            }
        };
        if (versionId!=null)
            SdkClient.getInstance().getCardVersion(cardID, versionId, "es-ES", callback);
        else
            SdkClient.getInstance().getCard(cardID, "es-ES", callback);
    }

    /**
     * Requests the server the modules for the given card.
     *
     * @param cardID    the card identifier.
     * @param versionId the card version.
     * @param manager   fragment manager.
     * @param container LinearLayout where will show cardDetail.
     * @return
     */
    public void buildAll(String cardID, String versionId, ArrayList<RelationModule> rels, FragmentManager manager, LinearLayout container, LinearLayout upperContainer, Toolbar toolbar, boolean cardWithRels,
                         boolean isCarousel, Activity activity) {

        buildDefault = true;
        this.mManager = manager;
        this.mLayout = container;
        this.mUpperLayout = upperContainer;
        this.mToolBar = toolbar;
        this.cardWithRels = cardWithRels;
        this.relations = rels;
        this.isCarousel = isCarousel;
        this.cardDetailActivity = activity;

        ClientCallback<Card> callback = new ClientCallback<Card>() {
            @Override
            public void onFailure(RestAPIError restAPIError) {

            }

            @Override
            public void onSuccess(Card response) {
                card = response;
                if (relations != null && relations.size() > 0) {
                    if (response != null && response.getRelations() != null && response.getRelations().size() > 0) {
                        relations.addAll(response.getRelations());
                    }
                    RelationModule[] relationModuleList = relations.toArray(new RelationModule[relations.size()]);

                    card.setRelations(Arrays.asList(relationModuleList));

                }

                composeCardDetail();
            }
        };
        if (versionId!=null)
            SdkClient.getInstance().getCardVersion(cardID, versionId, "es-ES", callback);
        else
            SdkClient.getInstance().getCard(cardID, "es-ES", callback);
    }


    /**
     * Loads the style configuration from a JSON file
     *
     * @param styleConfig A JSON containing all the style information for the card to be built.
     * @return
     */
    public BaseCardDetailBuilder loadStyleConfig(JSONArray styleConfig) {

        if (styleConfig == null) {
            requestDataStyle();
        } else {
            ModuleStyle[] styles = new GsonBuilder().create().fromJson(styleConfig.toString(), ModuleStyle[].class); //TODO: test!!!
            if (styles == null) {
                requestDataStyle();
            } else {
                if (styles != null && styles.length > 0) {
                    for (ModuleStyle style : styles) {
                        idStyle.put(style.getModuleName(), style);
                    }
                }
            }
        }

        return getThis();
    }

    protected void requestDataStyle() {
        String jsonString = null;
        try {
            Resources res = context.getResources();
            InputStream in_s;
            in_s = res.openRawResource(R.raw.module_styles_default);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            jsonString = new String(b);
        } catch (Exception e) {
            return;
        }
        ModuleStyle[] styles = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            styles = mapper.readValue(jsonString, ModuleStyle[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (styles != null && styles.length > 0) {
            for (ModuleStyle style : styles) {
                idStyle.put(style.getModuleName(), style);
            }
        }
    }

    /**
     * Create the Card Detail
     */
    protected void composeCardDetail() {

        //First off all get the main section from the dictionary.
        ConfigSection main = idSection.get(mainSectionKey);
        if (main == null) {
            return;
        }

        validator.validate(card, idSection, isCarousel);

        if (this.customCardDetailManager != null) {
            try {
                Constructor c = Class.forName(customCardDetailManager).getConstructor(Context.class, Handler.class);
                CardDetailManager cardDetailManager = (CardDetailManager) c.newInstance(context, card, idSection, "main", mManager, mLayout, mUpperLayout, mToolBar, idStyle, cardDetailActivity, isCarousel);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                new CardDetailManager(context, card, idSection, "main", mManager, mLayout, mUpperLayout, mToolBar, idStyle, cardDetailActivity, isCarousel);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                new CardDetailManager(context, card, idSection, "main", mManager, mLayout, mUpperLayout, mToolBar, idStyle, cardDetailActivity, isCarousel);
            } catch (InstantiationException e) {
                e.printStackTrace();
                new CardDetailManager(context, card, idSection, "main", mManager, mLayout, mUpperLayout, mToolBar, idStyle, cardDetailActivity, isCarousel);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                new CardDetailManager(context, card, idSection, "main", mManager, mLayout, mUpperLayout, mToolBar, idStyle, cardDetailActivity, isCarousel);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                new CardDetailManager(context, card, idSection, "main", mManager, mLayout, mUpperLayout, mToolBar, idStyle, cardDetailActivity, isCarousel);
            }

        } else {
            new CardDetailManager(context, card, idSection, "main", mManager, mLayout, mUpperLayout, mToolBar, idStyle, cardDetailActivity, isCarousel);
        }


    }


    protected T addSection(String sectionId, ConfigSection section) {
        idSection.put(sectionId, section);
        return getThis();
    }

    public void setIsCardLiked(boolean isLiked) {
        if (card != null && card.getUser() != null) {
            card.getUser().setIsLiked(isLiked);
        }
    }

}