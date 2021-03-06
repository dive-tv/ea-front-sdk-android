package sdk.dive.tv.ui.modules.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.touchvie.sdk.model.Card;

import java.util.ArrayList;
import java.util.HashMap;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.data.ModuleStyleData;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.data.TextRowData;

/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public class CuriositiesAdapter extends TVScrollAdapter {

    private final TvCardDetailListener tvCardDetailListener;
    private Typeface latoSemibold;
    private String parentId;
    private String parentType;
    private HashMap<String, ModuleStyleData> genericStyles;

    public CuriositiesAdapter(Context context, ArrayList<TextRowData> allData, String cardId, TvCardDetailListener tvCardDetailListener) {
        super();
        this.allData.addAll(allData);
        this.parentId = cardId;
        this.tvCardDetailListener = tvCardDetailListener;
        latoSemibold = Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD);
        setData();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_curiosity_row, parent, false);
        return new CuriositiesItemHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TextRowData row = (TextRowData) rowItems[holder.getAdapterPosition()];
        ((CuriositiesItemHolder) holder).text.setText(row.getText());
        ((CuriositiesItemHolder) holder).text.setTypeface(latoSemibold);

        ((CuriositiesItemHolder) holder).container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCardDetailListener.onCallCardDetail(row.getCardId(), row.getCardVersion(), Card.TypeEnum.fromValue(row.getCardType()));
//            OpenCard openCard = new OpenCard(EventBusIds.OPEN_CARD.getName(), row.getCardId(), row.getCardType());
//            EventBusManager.getInstance().post(openCard);
            }
        });
        if (tvCardDetailListener != null && tvCardDetailListener.getGenericStyles() != null) {
            genericStyles = tvCardDetailListener.getGenericStyles();
            ((CuriositiesItemHolder) holder).borderLayout.setBackground(Utils.makeSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()), Color.parseColor(genericStyles.get("unselectedColor").getValue())));
        }

    }

    private static class CuriositiesItemHolder extends RecyclerView.ViewHolder {

        private RelativeLayout container;
        private TextView text;
        private FrameLayout borderLayout;

        CuriositiesItemHolder(View v) {
            super(v);
            borderLayout = (FrameLayout) v.findViewById(R.id.simageitems_container);
            container = (RelativeLayout) v.findViewById(R.id.tv_module_horizontal_item_container);
            text = (TextView) v.findViewById(R.id.curiosity_text);
        }
    }
}
