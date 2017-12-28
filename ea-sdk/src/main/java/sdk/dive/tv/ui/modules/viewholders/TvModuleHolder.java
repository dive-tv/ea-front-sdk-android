package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;

import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;

/**
 * Created by Tagsonomy S.L. on 05/10/2016.
 */

public abstract class TvModuleHolder extends RecyclerView.ViewHolder {

    public String moduleName;

    /**
     * Default constructor
     *
     * @param itemView
     */
    public TvModuleHolder(View itemView) {
        super(itemView);
    }

    public void configure(Card cardData, Picasso picasso, Context context, TvCardDetailListener tvCardDetailListener, SectionListener sectionListener) {

    }
}
