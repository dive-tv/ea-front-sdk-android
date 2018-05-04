package sdk.dive.tv.ui.modules.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.data.ModuleStyleData;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.data.TwoTextRowData;

/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public class MovieSoundtrackAdapter extends TVScrollAdapter {

    private Typeface latoHeavy;
    private Typeface latoRegular;
    private HashMap<String, ModuleStyleData> genericStyles;
    private Context context;

    public MovieSoundtrackAdapter(Context context, ArrayList<TwoTextRowData> allData, TvCardDetailListener cardDetailListener) {
        super();
        this.allData.addAll(allData);
        this.context=context;
        latoHeavy = Utils.getFont(context, Utils.TypeFaces.LATO_HEAVY);
        latoRegular = Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR);
        if (cardDetailListener != null && cardDetailListener.getGenericStyles() != null){
            genericStyles = cardDetailListener.getGenericStyles();
        }

        setData();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_module_soundtrack_item, parent, false);
        return new SoundtrackItemHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TwoTextRowData row = (TwoTextRowData) rowItems[holder.getAdapterPosition()];

        ((SoundtrackItemHolder) holder).title.setTypeface(latoHeavy);
        ((SoundtrackItemHolder) holder).author.setTypeface(latoRegular);

        if (row.getText() != null) {
            ((SoundtrackItemHolder) holder).title.setText(row.getText());
        } else {
            ((SoundtrackItemHolder) holder).title.setText("");
        }
        if (row.getSubtitle() != null) {
            ((SoundtrackItemHolder) holder).author.setText(row.getSubtitle());
        } else {
            ((SoundtrackItemHolder) holder).author.setText("");
        }

        if (genericStyles!=null){
            ((SoundtrackItemHolder) holder).border.setBackground(Utils.makeSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue())));
        }

    }

    private static class SoundtrackItemHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView author;
        private LinearLayout border;
        private RelativeLayout container;

        SoundtrackItemHolder(View v) {
            super(v);
            container = (RelativeLayout) v.findViewById(R.id.tv_module_soundtrack_item_container);
            border = (LinearLayout) v.findViewById(R.id.tv_module_container_border);
            title = (TextView) v.findViewById(R.id.tv_module_soundtrack_item_title);
            author = (TextView) v.findViewById(R.id.tv_module_soundtrack_item_subtitle);
        }
    }
}
