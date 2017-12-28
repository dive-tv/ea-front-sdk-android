package sdk.dive.tv.ui.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.CardContainer;
import com.touchvie.sdk.model.Listing;

import sdk.dive.tv.ui.modules.viewholders.SpecsHolder;

/**
 * Created by Tagsonomy S.L. on 04/10/2016.
 */

public class Specifications extends TwoColsModule {
    /**
     * Default constructor
     */
    public Specifications() {
        super();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(this.layout, group, false);
        SpecsHolder holder = new SpecsHolder(viewGroup, getClass().getSimpleName());
        return holder;
    }

    @Override
    public boolean validate(Card cardData, boolean isCarousel) {
        CardContainer container = getContainer(cardData, Listing.ContentTypeEnum.SPECS.getValue());
        if (container == null) {
            return false;
        }

        return true;
    }


}
