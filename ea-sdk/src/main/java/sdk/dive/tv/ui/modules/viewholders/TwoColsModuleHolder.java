package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;

import java.util.ArrayList;

import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.adapters.TwoColsModuleAdapter;
import sdk.dive.tv.ui.modules.data.TwoTextRowData;

/**
 * Created by Tagsonomy S.L. on 19/10/2016.
 */

public abstract class TwoColsModuleHolder extends VerticalListHolder {

    protected TwoColsModuleAdapter adapter;
    protected ArrayList<TwoTextRowData> data;

    /**
     * Default constructor
     *
     * @param itemView
     */
    public TwoColsModuleHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, TvCardDetailListener tvCardDetailListener, SectionListener sectionListener) {
        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);

//        mTitle.setVisibility(GONE);
//        mContainer.setPadding(0, 0, 0, 0);
        data = new ArrayList<>();
        adapter = new TwoColsModuleAdapter(context, data, tvCardDetailListener);
        super.setAdapter(adapter, false, null);
    }

    protected void setData(ArrayList<TwoTextRowData> data, Context context, String moduleName, String moduleTitle, TvCardDetailListener tvCardDetailListener) {
        adapter.setModuleStyle(moduleName);
//        adapter.setModuleTitle(moduleTitle);

        mTitle.setText(moduleTitle);
        this.data.addAll(data);

        super.notifyDataSetChanged(false, null, context);
    }

}
