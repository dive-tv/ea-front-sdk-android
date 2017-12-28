package sdk.dive.tv.ui.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.RelationModule;
import com.touchvie.sdk.model.Single;

import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.modules.viewholders.AppearsInHolder;

import static com.touchvie.sdk.model.Single.ContentTypeEnum.APPEARS_IN;

/**
 * Created by Tagsonomy S.L. on 18/10/2016.
 */

public class AppearsIn extends HorizontalListModule {

    public AppearsIn() {
        super();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(this.layout, group, false);
        AppearsInHolder holder = new AppearsInHolder(viewGroup, getClass().getSimpleName());
        return holder;
    }

    @Override
    public boolean validate(Card cardData, boolean isCarousel) {
        RelationModule rel = Utils.getRelation(cardData, APPEARS_IN.getValue());
        if (rel == null || ((Single) rel).getData() == null || ((Single) rel).getData().size() < 2) {
            return false;
        }
        return true;
    }
}
