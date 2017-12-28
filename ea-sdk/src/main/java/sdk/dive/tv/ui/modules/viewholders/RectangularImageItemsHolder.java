package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;

import java.util.ArrayList;

import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.adapters.RectangularImageItemsAdapter;
import sdk.dive.tv.ui.modules.data.RectangularImageRowData;

/**
 * Created by Tagsonomy S.L. on 14/10/2016.
 */

public abstract class RectangularImageItemsHolder extends HorizontalListHolder {

    protected RectangularImageItemsAdapter adapter;
    protected ArrayList<RectangularImageRowData> data;

    public RectangularImageItemsHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, TvCardDetailListener tvCardDetailListener, SectionListener sectionListener) {

        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);
        data = new ArrayList<>();
        adapter = new RectangularImageItemsAdapter(context, data, cardData.getCardId());
        super.setAdapter(adapter, data.size());
    }

    protected void setData(ArrayList<RectangularImageRowData> data) {
        this.data.addAll(data);
        adapter.notifyDataSetChanged();
    }

    protected void setImageTextSpace(boolean space) {

        adapter.setImageTextSpace(space);
    }

}

