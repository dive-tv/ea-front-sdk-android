package sdk.dive.tv.ui.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.CardContainer;

import sdk.dive.tv.ui.modules.viewholders.BasicInfoHolder;

import static com.touchvie.sdk.model.Listing.ContentTypeEnum.BASIC_DATA;


/**
 * Created by Tagsonomy S.L. on 04/10/2016.
 */

public class BasicInfo extends TwoColsModule {

    /**
     * Default constructor
     */
    public BasicInfo() {
        super();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(this.layout, group, false);
        BasicInfoHolder holder = new BasicInfoHolder(viewGroup, getClass().getSimpleName());

        return holder;
    }

    @Override
    public boolean validate(Card cardData, boolean isCarousel) {
        CardContainer container = getContainer(cardData, BASIC_DATA.getValue());
        if (container == null) {
            return false;
        }

        return true;
    }
}
