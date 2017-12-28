package sdk.dive.tv.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.touchvie.sdk.model.Card;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.adapters.TvModulesAdapter;
import sdk.dive.tv.ui.builders.ConfigSection;
import sdk.dive.tv.ui.listeners.CardDetailListener;
import sdk.dive.tv.ui.views.ItemHorizontalOffsetDecoration;

/**
 * Created by Tagsonomy S.L. on 12/07/2017.
 */

public class DiveTVSection extends Section {

    public static DiveTVSection newInstance(Card data, ConfigSection configSection, SectionType sectionType, boolean isCarousel, CardDetailListener listener) {

        DiveTVSection fragment = new DiveTVSection();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        createModulesadapter();
        view = inflater.inflate(R.layout.tv_section_base_recycler, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        recyclerView.addItemDecoration(new ItemHorizontalOffsetDecoration(getContext().getResources().getDimensionPixelOffset(R.dimen.card_detail_separator)));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(modulesAdapter);
        recyclerView.requestFocus();

        return view;
    }

    @Override
    protected void createModulesadapter() {
        if (!isAdded())
            return;

        modulesAdapter = new TvModulesAdapter(getContext(), cardData, configSection.getConfigModules(), isCarousel, mListener, instance);
    }
}
