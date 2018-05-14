package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;

import java.util.ArrayList;
import java.util.HashMap;

import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.data.ModuleStyleData;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.adapters.NoPaddingListAdapter;
import sdk.dive.tv.ui.modules.data.ImageRowData;

/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public abstract class NoPaddingListHolder extends VerticalListHolder {

    protected NoPaddingListAdapter adapter;
    protected ArrayList<ImageRowData> data;
    private HashMap<String, ModuleStyleData> genericStyles;

    /**
     * Default constructor
     *
     * @param itemView
     */
    public NoPaddingListHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, TvCardDetailListener tvCardDetailListener, SectionListener sectionListener) {

        mContainer.setPadding(0, mContainer.getPaddingTop(), 0, mContainer.getPaddingBottom());

        data = new ArrayList<>();
        adapter = new NoPaddingListAdapter(data);
        super.setAdapter(adapter, false, null);
        if (tvCardDetailListener != null && tvCardDetailListener.getGenericStyles() != null){
            genericStyles = tvCardDetailListener.getGenericStyles();
            mContainer.setBackground(Utils.makeSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("backgroundModuleColor").getValue()), genericStyles.get("backgroundModuleColor").getValue()));
            btnDown.setBackground(Utils.makeButtonSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("selectedColor").getValue()));
            btnUp.setBackground(Utils.makeButtonSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("selectedColor").getValue()));
        }

    }

    protected void setData(ArrayList<ImageRowData> data, boolean withSeparator, View customView, Context context) {
        this.data.addAll(data);
        adapter.notifyDataSetChanged();
        super.notifyDataSetChanged(withSeparator, customView, context);
    }
}
