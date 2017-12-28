package sdk.dive.tv.ui.listeners;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.LinearLayout;

import java.util.HashMap;

import sdk.dive.tv.ui.data.ModuleStyleData;
import sdk.dive.tv.ui.fragments.Section;


/**
 * Created by Tagsonomy S.L. on 20/09/2016.
 */
public interface CardDetailListener extends Parcelable {

    /**
     * Interface to go to a determined section through navigation modules
     *
     * @param sectionName
     */
    void goToSection(String sectionName);

    /**
     * Got to Upper fragment
     */
    void loadUpperFragment(Fragment fragment);

    /**
     * Request a new Section to Card Detail, to put it in our view pager
     *
     * @param sectionName
     * @return the newly created Section
     */
    Section requestSectionForTab(String sectionName);

    /**
     * Request the title of the Section, used in the Tab Module
     *
     * @param sectionName
     * @return the title of the Sectoin
     */
    String requestSectionTitleForTab(String sectionName);

    /**
     * Request the fragment manager, used inside viewpager's adapter.
     *
     * @return the fragment manager
     */
    FragmentManager requestFragmentManager();

    LinearLayout requestContainerLayout();

    HashMap<String, ModuleStyleData> getModuleStyles(String moduleName);

    @Override
    public void writeToParcel(Parcel parcel, int i);

    public static final Creator CREATOR = new Creator() {
        @Override
        public CardDetailListener createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public CardDetailListener[] newArray(int i) {
            return new CardDetailListener[0];
        }

    };
}
