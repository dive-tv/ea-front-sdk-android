package sdk.dive.tv.ui.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.CardContainer;

import java.util.HashMap;

import sdk.dive.tv.ui.modules.viewholders.AwardsHolder;

import static com.touchvie.sdk.model.CardContainer.TypeEnum.AWARDS;

/**
 * Created by Tagsonomy S.L. on 04/10/2016.
 */

public class Awards extends VerticalListModule {

    public Awards() {
        super();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(this.layout, group, false);
        AwardsHolder holder = new AwardsHolder(viewGroup, getClass().getSimpleName());
        return holder;
    }

    @Override
    public boolean validate(Card cardData, boolean isCarousel) {
        CardContainer container = getContainer(cardData, AWARDS.getValue());
        if (container == null || ((com.touchvie.sdk.model.Awards) container).getData().size() == 0) {
            return false;
        }
        return true;
    }


}
