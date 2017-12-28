package sdk.dive.tv.ui.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.RelationModule;
import com.touchvie.sdk.model.Single;

import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.modules.viewholders.FashionHolder;

import static com.touchvie.sdk.model.Single.ContentTypeEnum.FASHION_SET;

/**
 * Created by Tagsonomy S.L. on 18/11/2016.
 */

public class Fashion extends HorizontalListModule {

    public Fashion() {
        super();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(this.layout, group, false);
        FashionHolder holder = new FashionHolder(viewGroup, getClass().getSimpleName());
        return holder;
    }

    @Override
    public boolean validate(Card cardData, boolean isCarousel) {

        RelationModule rel = Utils.getRelation(cardData, FASHION_SET.getValue());
        if (rel == null) {
            return false;
        }

        if (((Single) rel).getData().size() <= 0) {
            return false;
        }
        return true;
    }
}
