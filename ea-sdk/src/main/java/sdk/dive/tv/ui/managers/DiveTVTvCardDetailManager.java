package sdk.dive.tv.ui.managers;

import android.app.Activity;
import android.content.Context;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;

import com.touchvie.sdk.model.Card;

import java.util.HashMap;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.builders.ConfigSection;
import sdk.dive.tv.ui.data.ModuleStyle;
import sdk.dive.tv.ui.data.ModuleStyleData;
import sdk.dive.tv.ui.fragments.CardDetail;
import sdk.dive.tv.ui.fragments.DiveTVSection;
import sdk.dive.tv.ui.fragments.Section;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;

/**
 * Created by Tagsonomy S.L. on 12/07/2017.
 */

public class DiveTVTvCardDetailManager extends CardDetailManager implements TvCardDetailListener {

    /**
     * Default constructor
     *
     * @param context
     * @param data
     * @param idSection
     * @param mainKey
     * @param manager            :   fragment manager
     * @param container          : Linear layout
     * @param upperContainer
     * @param mToolBar
     * @param idStyle
     * @param cardDetailActivity
     * @param isCarousel
     */
    public DiveTVTvCardDetailManager(Context context, Card data, HashMap<String, ConfigSection> idSection, String mainKey, FragmentManager manager, LinearLayout container, LinearLayout upperContainer, Toolbar mToolBar, HashMap<String, ModuleStyle> idStyle, Activity cardDetailActivity, boolean isCarousel) {
        super(context, data, idSection, mainKey, manager, container, upperContainer, mToolBar, idStyle, cardDetailActivity, isCarousel);
    }

    @Override
    protected void createAndAddSection(){

        DiveTVSection newSection = DiveTVSection.newInstance(this.data, configSectionsDict.get(mainSection), DiveTVSection.SectionType.recycler_view, isCarousel, instance);
        mFragmentManager.beginTransaction()
                .add(this.containerLinear.getId(), newSection)
                .commitAllowingStateLoss();
    }

    @Override
    public void goToSection(String sectionName) {

    }

    @Override
    public void loadUpperFragment(Fragment fragment) {

    }

    @Override
    public Section requestSectionForTab(String sectionName) {
        return null;
    }

    @Override
    public String requestSectionTitleForTab(String sectionName) {
        return null;
    }

    @Override
    public FragmentManager requestFragmentManager() {
        return null;
    }

    @Override
    public LinearLayout requestContainerLayout() {
        return null;
    }

    @Override
    public HashMap<String, ModuleStyleData> getModuleStyles(String moduleName) {
        return null;
    }

    @Override
    public void onCallCardDetail(String cardId, String versionId, Card.TypeEnum cardType, FragmentManager manager) {
        sdk.dive.tv.ui.fragments.CardDetail cardDetail  = sdk.dive.tv.ui.fragments.CardDetail.newInstance(cardId, versionId, cardType, null, isCarousel, manager);

        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_bottom_overlay, cardDetail, Utils.FragmentNames.CARD_DETAIL.name())
                .addToBackStack(Utils.FragmentNames.CARD_DETAIL.name())
                .commit();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
