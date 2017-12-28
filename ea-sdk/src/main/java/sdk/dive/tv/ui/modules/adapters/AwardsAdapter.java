package sdk.dive.tv.ui.modules.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.touchvie.sdk.model.AwardsData;

import java.util.List;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;

/**
 * Created by Tagsonomy S.L. on 11/10/2016.
 */

public class AwardsAdapter extends BaseAdapter {


    private Context context;

    private List<AwardsData> awards;

    private TvCardDetailListener tvCardDetailListener;
    private Typeface latoRegular;
    private Typeface latoSemibold;
    private boolean isCarousel;

    private LayoutInflater mInflater;

    private boolean cropEntries = true;

    private boolean styleConfigRetrieved = false;
    private boolean darkStyle = false;


    /**
     * Instantiates a new awards adapter.
     *
     * @param context the context
     */
    public AwardsAdapter(Context context, List<AwardsData> items, boolean cropEntries, boolean isCarousel, TvCardDetailListener tvCardDetailListener) {
        this.context = context;
        this.awards = items;
        this.cropEntries = cropEntries;
        this.mInflater = LayoutInflater.from(context);
        this.isCarousel = isCarousel;
        this.tvCardDetailListener = tvCardDetailListener;
        latoRegular = Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR);
        latoSemibold = Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD);

    }


    /**
     * The number of items in the list.
     *
     * @returns {int} length. The number of items in the providers list.
     */
    @Override
    public int getCount() {
        if (cropEntries)
            return awards.size() > 5 ? 5 : awards.size();
        else
            return awards.size();
    }

    /**
     * Since the data comes from an array, just returning the index is sufficient
     * to get at the data. If we were using a more complex data structure, we
     * would return whatever object represents one row in the list.
     *
     * @param {int} position. The object's position in the array.
     * @returns {Object} position. The object's position in the array
     */
    @Override
    public Object getItem(int position) {

        return awards.get(position);
    }

    /**
     * Gets the item identifier. Uses the array index as a unique id.
     *
     * @param {int} position. The item's position in the array.
     * @returns {Object} position. The item's identifier.
     */
    @Override
    public long getItemId(int position) {

        return awards.indexOf(getItem(position));
    }

    /**
     * Makes a view to hold each row.
     *
     * @param {int}       position. The item's position in the array.
     * @param {View}      convertView. The provider's list view.
     * @param {ViewGroup} parent. The view group that holds the provider's view.
     * @returns {View} convertView. The view to hold each row.
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        sdk.dive.tv.ui.modules.adapters.AwardsAdapter.AwardsItemHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.tv_awards_row, null);
            holder = new sdk.dive.tv.ui.modules.adapters.AwardsAdapter.AwardsItemHolder();
            holder.title = (TextView) view.findViewById(R.id.txtv_vlist_title);
            holder.title.setTypeface(latoSemibold);
            holder.subtitle = (TextView) view.findViewById(R.id.txtv_vlist_subtitle);
            holder.subtitle.setTypeface(latoRegular);
            holder.container = (LinearLayout) view.findViewById(R.id.llay_container);
            view.setTag(holder);
        } else {
            holder = (sdk.dive.tv.ui.modules.adapters.AwardsAdapter.AwardsItemHolder) view.getTag();

        }

        final AwardsData data = awards.get(position);
        if (data.getTitle() != null) {
            holder.title.setText(data.getTitle());
        }
        else{
            holder.title.setText("");
        }

        StringBuilder subtitle = new StringBuilder();

        if (data.getWinner() != null && data.getWinner().size() > 0) {
            holder.subtitle.setVisibility(View.VISIBLE);
            if (data.getWinner().size() == 1) {
                subtitle.append(String.valueOf(1) + " " + context.getResources().getString(R.string.AWARD));
            } else {
                subtitle.append(String.valueOf(data.getWinner().size()) + " " + context.getResources().getString(R.string.CARD_MODULE_TITLE_AWARDS));
            }

            if (data.getNominee() != null && data.getNominee().size() > 0) {
                if (data.getNominee().size() == 1) {
                    subtitle.append(" / " + String.valueOf(1) + " " + context.getResources().getString(R.string.NOMINATION));
                } else {
                    subtitle.append(" / " + String.valueOf(data.getNominee().size()) + " " + context.getResources().getString(R.string.NOMINATIONS));
                }
            }
            holder.subtitle.setText(subtitle.toString());
        } else if (data.getNominee() != null && data.getNominee().size() > 0) {
            holder.subtitle.setVisibility(View.VISIBLE);

            if (data.getWinner().size() == 1) {
                subtitle.append(String.valueOf(1) + " " + context.getResources().getString(R.string.NOMINATION));
            } else {
                subtitle.append(String.valueOf(1) + " " + context.getResources().getString(R.string.NOMINATIONS));
            }
            holder.subtitle.setText(subtitle.toString());
        } else {
            holder.subtitle.setVisibility(View.GONE);
        }

        return view;
    }

    /**
     * The type Basic info holder.
     */
    private static class AwardsItemHolder {
        TextView title;
        TextView subtitle;
        LinearLayout container;
    }
}
