package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;

import java.util.ArrayList;

import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.adapters.SquareImageItemsAdapter;
import sdk.dive.tv.ui.modules.data.SquareImageRowData;

/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public abstract class SquareImageItemsHolder extends HorizontalListHolder {

    protected SquareImageItemsAdapter adapter;
    protected ArrayList<SquareImageRowData> data;

    public SquareImageItemsHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, TvCardDetailListener tvCardDetailListener, SectionListener sectionListener) {

        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);

        data = new ArrayList<>();
        adapter = new SquareImageItemsAdapter(context, data, tvCardDetailListener, cardData.getCardId());
        super.setAdapter(adapter, data.size());
    }

    protected void setData(ArrayList<SquareImageRowData> data, String moduleName) {
        adapter.setModuleStyle(moduleName);
        this.data.addAll(data);
        adapter.notifyDataSetChanged();
    }

}
