package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;

import java.util.ArrayList;

import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.adapters.CastLocationAdapter;
import sdk.dive.tv.ui.modules.data.CastLocationRowData;

/**
 * Created by Tagsonomy S.L. on 24/11/2016.
 */

public class CastLocationsHolder extends HorizontalListHolder {

    protected CastLocationAdapter adapter;
    protected ArrayList<CastLocationRowData> data;

    public CastLocationsHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, TvCardDetailListener tvCardDetailListener, SectionListener sectionListener) {

        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);

        data = new ArrayList<>();
        adapter = new CastLocationAdapter(context, data, tvCardDetailListener, cardData.getCardId());
        super.setAdapter(adapter, data.size());
    }

    protected void setData(ArrayList<CastLocationRowData> data, String moduleName) {
        adapter.setModuleStyle(moduleName);
        this.data.addAll(data);
        adapter.notifyDataSetChanged();
    }


}
