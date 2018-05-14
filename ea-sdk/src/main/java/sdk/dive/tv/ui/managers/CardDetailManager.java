package sdk.dive.tv.ui.managers;


import android.app.Activity;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.touchvie.sdk.model.Card;

import java.util.HashMap;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.builders.ConfigSection;
import sdk.dive.tv.ui.data.ModuleStyle;
import sdk.dive.tv.ui.data.ModuleStyleData;
import sdk.dive.tv.ui.fragments.Section;
import sdk.dive.tv.ui.listeners.CardDetailListener;

/**
 *
 */
public class CardDetailManager implements CardDetailListener {

    protected final CardDetailManager instance;
    private final Context context;
    private final Activity cardDetailActivity;
    protected boolean isCarousel;
    /**
     * Card Type
     */
    public int type;

    /**
     * Dictionary of Sections in the Card
     * Key: Section name
     * Value: Section
     */
    protected HashMap<String, ConfigSection> configSectionsDict = new HashMap<>();

    protected final FragmentManager mFragmentManager;
    protected final LinearLayout containerLinear;
    private final LinearLayout upperContainerLinear;
    private final Toolbar mToolBar;
    protected String mainSection;
    protected Card data;
    private HashMap<String, ModuleStyle> idStyle;

    /**
     * Default constructor
     *
     * @param context
     * @param data
     * @param idSection
     * @param mainKey
     * @param manager            :   fragment manager
     * @param container          : Linear layout
     * @param cardDetailActivity
     */
    public CardDetailManager(Context context, Card data, HashMap<String, ConfigSection> idSection, String mainKey, FragmentManager manager,
                             LinearLayout container, LinearLayout upperContainer, Toolbar mToolBar, HashMap<String, ModuleStyle> idStyle, Activity cardDetailActivity, boolean isCarousel) {
        this.mFragmentManager = manager;
        this.context = context;
        this.containerLinear = container;
        this.upperContainerLinear = upperContainer;
        this.mToolBar = mToolBar;
        this.data = data;
        this.cardDetailActivity = cardDetailActivity;
        this.isCarousel = isCarousel;
        instance = this;

        for (String id : idSection.keySet()) {
            ConfigSection section = idSection.get(id);
            setConfigSection(section);
            if (section.isMain()) {
                mainSection = id;
            }
        }

        this.idStyle = idStyle;


        if (cardDetailActivity == null || cardDetailActivity.isDestroyed())
            return;

        createAndAddSection();

    }

    protected void createAndAddSection(){

        Section newSection = Section.newInstance(this.data, configSectionsDict.get(mainSection), Section.SectionType.recycler_view, isCarousel, instance);
        mFragmentManager.beginTransaction()
                .replace(this.containerLinear.getId(), newSection)
//                .addToBackStack(mainSection)
                .commitAllowingStateLoss();
    }


    /**
     * Set the Card Type
     *
     * @param type
     * @return
     */
    public void setType(int type) {
        // TODO implement here
    }

    /**
     * Get the Card Type
     *
     * @return
     */
    public int getType() {
        // TODO implement here
        return type;
    }

    /**
     * Create new Section
     *
     * @param section
     * @return
     */
    public void setConfigSection(ConfigSection section) {
        configSectionsDict.put(section.getTitle(), section);
    }

    /**
     * Set multiple sections at once
     *
     * @param configSection
     * @return
     */
    public void setConfigSections(HashMap<String, ConfigSection> configSection) {
        // TODO implement here
    }

    /**
     * Get the sections
     *
     * @return sections
     */
    public HashMap<String, ConfigSection> getSections() {
        return configSectionsDict;
    }

    @Override
    public void goToSection(String sectionName) {
        if (configSectionsDict.containsKey(sectionName)) {
            if (cardDetailActivity == null || cardDetailActivity.isDestroyed())
                return;
            Section newSection = Section.newInstance(data, configSectionsDict.get(sectionName), Section.SectionType.recycler_view, isCarousel, instance);
            mFragmentManager.beginTransaction().replace(this.containerLinear.getId(), newSection).addToBackStack(mainSection).commitAllowingStateLoss();
        }
    }

    @Override
    public void loadUpperFragment(Fragment fragment) {
        this.upperContainerLinear.setVisibility(View.VISIBLE);

        if (cardDetailActivity == null || cardDetailActivity.isDestroyed())
            return;

        if (mToolBar!=null)
            mToolBar.setBackgroundColor(Utils.getColor(context, R.color.black_menu_carddetail));

        mFragmentManager.beginTransaction().replace(this.upperContainerLinear.getId(), fragment).addToBackStack(null).commitAllowingStateLoss();
    }

    @Override
    public Section requestSectionForTab(String sectionName) {
        if (configSectionsDict.containsKey(sectionName)) {
            Section newSection = Section.newInstance(data, configSectionsDict.get(sectionName), Section.SectionType.linear_layout, isCarousel, instance);
            return newSection;
        }
        return null;
    }

    @Override
    public String requestSectionTitleForTab(String sectionName) {
        if (configSectionsDict.containsKey(sectionName)) {
            return configSectionsDict.get(sectionName).getTitle();
        }
        return null;
    }

    @Override
    public FragmentManager requestFragmentManager() {
        return mFragmentManager;
    }

    @Override
    public LinearLayout requestContainerLayout() {
        return this.containerLinear;
    }

    @Override
    public HashMap<String, ModuleStyleData> getModuleStyles(String moduleName) {
        return idStyle.get(moduleName) != null ? idStyle.get(moduleName).getIdModuleStyleData() : null;
    }

    @Override
    public HashMap<String, ModuleStyleData> getGenericStyles() {
        if (idStyle!=null && idStyle.get("carddetail")!=null) {
            Log.e("Styles:", idStyle.get("carddetail").getIdModuleStyleData().keySet().toString());
            Log.e("Styles:", idStyle.get("carddetail").getIdModuleStyleData().get("backgroundColor").getValue());
            Log.e("Styles:", idStyle.get("carddetail").getIdModuleStyleData().get("selectedColor").getValue());
            Log.e("Styles:", idStyle.get("carddetail").getIdModuleStyleData().get("unselectedColor").getValue());
            return idStyle.get("carddetail") != null ? idStyle.get("carddetail").getIdModuleStyleData() : null;
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public CardDetailManager createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public CardDetailManager[] newArray(int i) {
            return new CardDetailManager[0];
        }

    };
}