package sdk.dive.tv.ui.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.RelationModule;
import com.touchvie.sdk.model.Single;

import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.modules.viewholders.CuriositiesHolder;

import static com.touchvie.sdk.model.Single.ContentTypeEnum.TRIVIAS;

/**
 * Created by Tagsonomy S.L. on 04/10/2016.
 */

public class Curiosities extends HorizontalListModule {

    public Curiosities() {
        super();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(this.layout, group, false);
        CuriositiesHolder holder = new CuriositiesHolder(viewGroup, getClass().getSimpleName());
        return holder;
    }

    @Override
    public boolean validate(Card cardData, boolean isCarousel) {

        RelationModule relation = Utils.getRelation(cardData, TRIVIAS.getValue());
        if (relation == null || ((Single) relation).getData() == null || ((Single) relation).getData().size() < 1) {
            return false;
        }
        return true;
    }
}
