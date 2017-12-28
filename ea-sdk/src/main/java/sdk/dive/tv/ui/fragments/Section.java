package sdk.dive.tv.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.touchvie.sdk.model.Card;

import java.util.ArrayList;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.adapters.ModulesAdapter;
import sdk.dive.tv.ui.builders.ConfigSection;
import sdk.dive.tv.ui.listeners.CardDetailListener;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.views.Module;
import sdk.dive.tv.ui.views.RecyclerListLayout;

/**
 *
 */
public class Section extends Fragment implements SectionListener {

    public enum SectionType {recycler_view, linear_layout}

    private SectionType sectionType;
    private FragmentManager mFragmentManager;
    private LinearLayout container;

    /**
     * The Section instance
     */
    protected Section instance = null;

    /**
     * List of Modules in this Section
     */
    protected ArrayList<Module> modules;

    /**
     * Adapter which will create the modules from the data
     */
    protected ModulesAdapter modulesAdapter;

    /**
     * Layout Manager used to control the RecyclerView
     */
    private RecyclerView.LayoutManager mLayoutManager;

    /**
     * MiniCard object, with all the data of the card
     */
    protected Card cardData;

    /**
     * Module configuration file
     */
    protected ConfigSection configSection;

    /**
     * Interface to communicate with Card Detail
     */
    protected CardDetailListener mListener;

    protected boolean isCarousel;

    /**
     * Set the Modules in this Section
     *
     * @param modules
     * @return
     */
    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;
    }

    /**
     * Get Modules from this Section
     *
     * @return
     */
    public ArrayList<Module> getModules() {
        return modules;
    }


    public ConfigSection getConfigSection() {
        return configSection;
    }

    /**
     * Set a new Module on this Section
     *
     * @param module
     * @return
     */
    public void setModule(Module module) {
        // TODO implement here
    }

    /**
     * Default constructor
     */
    public Section() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment Home.
     */
    public static Section newInstance(Card data, ConfigSection configSection, SectionType sectionType, boolean isCarousel, CardDetailListener listener) {

        Section fragment = new Section();
        Bundle extras = new Bundle();
        extras.putSerializable(Utils.CARD_DATA, data);
        extras.putSerializable(Utils.CONFIG_SECTION, configSection);
        extras.putSerializable(Utils.SECTION_TYPE, sectionType);
        extras.putParcelable(Utils.CARD_DETAIL_LISTENER, listener);
        extras.putBoolean(Utils.IS_CAROUSEL, isCarousel);
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

        Bundle extras = getArguments();
        if (extras != null) {
            cardData = (Card) extras.getSerializable(Utils.CARD_DATA);
            configSection = (ConfigSection) extras.getSerializable(Utils.CONFIG_SECTION);
            sectionType = (SectionType) extras.getSerializable(Utils.SECTION_TYPE);
            isCarousel = extras.getBoolean(Utils.IS_CAROUSEL);
            mListener = extras.getParcelable(Utils.CARD_DETAIL_LISTENER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        createModulesadapter();
        switch (sectionType) {
            case recycler_view:
                view = inflater.inflate(R.layout.section_base_recycler, container, false);
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
                if (isCarousel) {
                    recyclerView.setPadding(0, 0, 0, (int) getResources().getDimension(R.dimen.carddetail_from_menu_carousel_padding));
                    recyclerView.setClipToPadding(false);
                }

                mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(modulesAdapter);
                break;
            case linear_layout:
                view = inflater.inflate(R.layout.section_base_linear, container, false);
                RecyclerListLayout recyclerListLayout = (RecyclerListLayout) view.findViewById(R.id.recycler_list_layout);
                if (isCarousel) {
                    recyclerListLayout.setPadding(0, 0, 0, (int) getResources().getDimension(R.dimen.carddetail_from_menu_carousel_padding));
                    recyclerListLayout.setClipToPadding(false);
                }
                recyclerListLayout.setList(modulesAdapter, false);
                break;
            default:
                view = inflater.inflate(R.layout.section_base_recycler, container, false);
                RecyclerView defaultView = (RecyclerView) view.findViewById(R.id.recycler_view);
                if (isCarousel) {
                    defaultView.setPadding(0, 0, 0, (int) getResources().getDimension(R.dimen.carddetail_from_menu_carousel_padding));
                    defaultView.setClipToPadding(false);
                }
                mLayoutManager = new LinearLayoutManager(getContext());
                defaultView.setLayoutManager(mLayoutManager);
                defaultView.setAdapter(modulesAdapter);
                break;
        }

        return view;
    }

    public SectionType getSectionType() {
        return sectionType;
    }

    public void setSectionType(SectionType sectionType) {
        this.sectionType = sectionType;
    }

    @Override
    public FragmentActivity getParentactivity() {
        return getActivity();
    }

    @Override
    public boolean isCarousel() {
        return isCarousel;
    }

    @Override
    public void onResume(){
        super.onResume();
        modulesAdapter.notifyDataSetChanged();

    }

    protected void createModulesadapter(){
        modulesAdapter = new ModulesAdapter(getContext(), cardData, configSection.getConfigModules(), isCarousel, mListener, instance);
    }
}