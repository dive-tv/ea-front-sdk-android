package sdk.dive.tv.ui.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.touchvie.sdk.model.Card;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.modules.viewholders.HeaderHolder;
import sdk.dive.tv.ui.views.Module;

/**
 * Created by Tagsonomy S.L. on 04/10/2016.
 */

public class Header extends Module {
    /**
     * Default constructor
     */
    public Header() {
        super();
        setLayout(R.layout.tv_module_header);
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(this.layout, group, false);
        HeaderHolder holder = new HeaderHolder(viewGroup, getClass().getSimpleName());
        return holder;
    }

    @Override
    public boolean validate(Card cardData, boolean isCarousel) {
        if (cardData.getTitle() == null) {
            return false;
        }
        return true;
    }
}
