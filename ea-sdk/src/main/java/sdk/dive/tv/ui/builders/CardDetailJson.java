package sdk.dive.tv.ui.builders;

import android.content.Context;
import android.content.res.Resources;

import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.data.DataConfig;

/**
 * Class to build a card detail with a configuration json.
 */
public class CardDetailJson extends BaseCardDetailBuilder<CardDetailJson> {

    /**
     * Default constructor
     */
    public CardDetailJson(Context context, String customCardDetailManager) {
        super(context);
        this.customCardDetailManager = customCardDetailManager;
    }

    /**
     * JSON with formatted with the Section and Modules order
     */
    public JSONObject config;


    /**
     * Load Sections and Modules order directly from a JSON file
     *
     * @param dataConfig
     * @return
     */
    public CardDetailJson loadDataConfig(JSONObject dataConfig) {
        // TODO implement here
        if (dataConfig == null) {
            requestDataConfig();
        } else {
            DataConfig config = new GsonBuilder().create().fromJson(dataConfig.toString(), DataConfig.class); //TODO: test!!!
            if (config == null) {
                requestDataConfig();
            } else {
                idSection = new HashMap<>();
                if (config.getSections() != null && config.getSections().length > 0) {
                    for (int i = 0; i < config.getSections().length; i++) {
                        idSection.put((config.getSections()[i]).getTitle(), config.getSections()[i]);
                    }
                }
            }
        }

        return getThis();
    }

    private void requestDataConfig() {
        String jsonString = null;
        try {
            Resources res = context.getResources();
            InputStream in_s = res.openRawResource(R.raw.dataconfig2);

            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            jsonString = new String(b);
        } catch (Exception e) {
            return;
        }

        DataConfig dataConfig = new GsonBuilder().create().fromJson(jsonString, DataConfig.class);
        if (dataConfig == null) {
            dataConfig = new DataConfig();
        }

        idSection = new HashMap<>();
        if (dataConfig.getSections() != null && dataConfig.getSections().length > 0) {
            for (int i = 0; i < dataConfig.getSections().length; i++) {
                idSection.put((dataConfig.getSections()[i]).getTitle(), dataConfig.getSections()[i]);
            }
        }
    }

    @Override
    protected CardDetailJson getThis() {
        return this;
    }

}
