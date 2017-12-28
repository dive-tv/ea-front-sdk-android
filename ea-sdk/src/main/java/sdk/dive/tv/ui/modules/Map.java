package sdk.dive.tv.ui.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.CardContainer;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.modules.viewholders.MapHolder;
import sdk.dive.tv.ui.views.Module;

import static com.touchvie.sdk.model.Card.TypeEnum.LOCATION;

/**
 * Created by Tagsonomy S.L. on 18/10/2016.
 */

public class Map extends Module {

    public Map() {
        super();
        setLayout(R.layout.tv_module_map);
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(this.layout, group, false);
        MapHolder holder = new MapHolder(viewGroup, getClass().getSimpleName());
        return holder;
    }

    @Override
    public boolean validate(Card cardData, boolean isCarousel) {

        CardContainer container = getContainer(cardData, LOCATION.getValue());
        if (container == null) {
            return false;
        }
/*
        if (((com.touchvie.backend.carddetail.containers.Map) container).getData() != null && ((com.touchvie.backend.carddetail.containers.Map) container).getData().size() == 1) {
            if (((com.touchvie.backend.carddetail.containers.Map) container).getData().get(0).getLatitude() > 0 &&
                    ((com.touchvie.backend.carddetail.containers.Map) container).getData().get(0).getLongitude() > 0 &&
                    ((com.touchvie.backend.carddetail.containers.Map) container).getData().get(0).getZoom() > 0) {
                return true;
            }
        }
*/
        return true;
    }
}
