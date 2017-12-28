package sdk.dive.tv.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.touchvie.sdk.model.Card;

import java.util.ArrayList;
import java.util.HashMap;

import sdk.dive.tv.ui.builders.ConfigModule;
import sdk.dive.tv.ui.listeners.CardDetailListener;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.viewholders.TvModuleHolder;

/**
 * Created by Tagsonomy S.L. on 20/07/2017.
 */

public class TvModulesAdapter extends ModulesAdapter {
    /**
     * Constructor
     *
     * @param context
     * @param cardData
     * @param configModules
     * @param isCarousel
     * @param mListener
     * @param sectionListener
     */
    public TvModulesAdapter(Context context, Card cardData, ArrayList<ConfigModule> configModules, boolean isCarousel, CardDetailListener mListener, SectionListener sectionListener) {
        super(context, cardData, configModules, isCarousel, mListener, sectionListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof TvModuleHolder) {
            ((TvModuleHolder) viewHolder).configure(cardData, mPicasso, context, (TvCardDetailListener) mListener, sectionListener);
        }
    }
}
