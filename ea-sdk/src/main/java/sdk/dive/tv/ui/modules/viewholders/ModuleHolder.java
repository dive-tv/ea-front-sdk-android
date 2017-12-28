package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;

import sdk.dive.tv.ui.listeners.CardDetailListener;
import sdk.dive.tv.ui.listeners.SectionListener;

/**
 * Created by Tagsonomy S.L. on 05/10/2016.
 */

public abstract class ModuleHolder extends RecyclerView.ViewHolder {

    public String moduleName;

    /**
     * Default constructor
     *
     * @param itemView
     */
    public ModuleHolder(View itemView) {
        super(itemView);
    }

    public abstract void configure(Card cardData, Picasso picasso, Context context, CardDetailListener cardDetailListener, SectionListener sectionListener);
}
