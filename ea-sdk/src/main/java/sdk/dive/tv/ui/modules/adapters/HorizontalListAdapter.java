package sdk.dive.tv.ui.modules.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sdk.dive.tv.R;
import sdk.dive.tv.eventbus.EventBusManager;
import sdk.dive.tv.eventbus.EventBusIds;
import sdk.dive.tv.eventbus.OpenCard;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.data.HorizontalItemData;

/**
 * Created by Tagsonomy S.L. on 10/10/2016.
 */

public class HorizontalListAdapter extends TVScrollAdapter {

    private final TvCardDetailListener tvCardDetailListener;
    Context context;
    Picasso mPicasso;

    /**
     * Instantiates a new Cards gallery grid adapter.
     *
     * @param context              the context
     * @param tvCardDetailListener
     */
    public HorizontalListAdapter(Context context, ArrayList<HorizontalItemData> allData, TvCardDetailListener tvCardDetailListener) {
        super();
        this.context = context;
        this.allData.addAll(allData);
        this.tvCardDetailListener = tvCardDetailListener;
        this.mPicasso = Picasso.with(context);
        setData();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_module_horizontal_item, parent, false);
        return new HorizontalItemHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final HorizontalItemData row = (HorizontalItemData) rowItems[holder.getAdapterPosition()];

        if (row != null && row.getText() != null) {
            ((HorizontalItemHolder) holder).title.setVisibility(View.VISIBLE);
            ((HorizontalItemHolder) holder).title.setText(row.getText());
            ViewGroup.LayoutParams layoutParams = ((HorizontalItemHolder) holder).frame.getLayoutParams();
            layoutParams.height = (int) Utils.getDimension(context, R.dimen.module_horizontal_list_item_image_height);
            ((HorizontalItemHolder) holder).frame.setLayoutParams(layoutParams);
            ViewGroup.LayoutParams imageLayoutParams = ((HorizontalItemHolder) holder).image.getLayoutParams();
            imageLayoutParams.height = (int) Utils.getDimension(context, R.dimen.module_horizontal_list_item_image_height);
            ((HorizontalItemHolder) holder).image.setLayoutParams(imageLayoutParams);
        } else {
            ((HorizontalItemHolder) holder).title.setVisibility(View.GONE);
            ViewGroup.LayoutParams layoutParams = ((HorizontalItemHolder) holder).frame.getLayoutParams();
            layoutParams.height = (int) Utils.getDimension(context, R.dimen.cardetail_module_gallery_height);
            ((HorizontalItemHolder) holder).frame.setLayoutParams(layoutParams);
            ViewGroup.LayoutParams imageLayoutParams = ((HorizontalItemHolder) holder).image.getLayoutParams();
            imageLayoutParams.height = (int) Utils.getDimension(context, R.dimen.cardetail_module_gallery_height);
            ((HorizontalItemHolder) holder).image.setLayoutParams(imageLayoutParams);
        }

        if (row != null && row.getImage() != null) {
            mPicasso.load(row.getImage().getThumb())
                    .into(((HorizontalItemHolder) holder).image);
            ((HorizontalItemHolder) holder).noimage.setVisibility(View.GONE);
            ((HorizontalItemHolder) holder).image.setVisibility(View.VISIBLE);
        } else {
            ((HorizontalItemHolder) holder).noimage.setVisibility(View.VISIBLE);
            ((HorizontalItemHolder) holder).image.setVisibility(View.GONE);
        }

        ((HorizontalItemHolder) holder).container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (row != null && row.isHasContent()) {
//                    OpenCard openCard = new OpenCard(EventBusIds.OPEN_CARD.getName(),row.getCardId(), row.getCardVersion(), row.getCardType());
//                    EventBusManager.getInstance().post(openCard);
                    if (!(row.getCardType().equals(Card.TypeEnum.MOVIE.getValue())) && !(row.getCardType().equals(Card.TypeEnum.SERIE.getValue())) &&!(row.getCardType().equals(Card.TypeEnum.CHAPTER.getValue())))
                        tvCardDetailListener.onCallCardDetail(row.getCardId(), row.getCardVersion(), Card.TypeEnum.fromValue(row.getCardType()));
                }
            }
        });
    }

    private static class HorizontalItemHolder extends RecyclerView.ViewHolder {

        private RelativeLayout container;
        private ImageView image;
        private ImageView noimage;
        private TextView title;
        private FrameLayout frame;

        HorizontalItemHolder(View v) {
            super(v);
            container = (RelativeLayout) v.findViewById(R.id.tv_module_horizontal_item_container);
            image = (ImageView) v.findViewById(R.id.tv_module_horizontal_item_image);
            noimage = (ImageView) v.findViewById(R.id.tv_module_horizontal_item_noimage);
            title = (TextView) v.findViewById(R.id.tv_module_horizontal_item_title);
            frame = (FrameLayout) v.findViewById(R.id.tv_module_horizontal_item_image_border);
        }
    }
}