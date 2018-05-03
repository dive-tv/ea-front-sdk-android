package sdk.dive.tv.ui.modules.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;

import java.util.ArrayList;
import java.util.HashMap;

import sdk.dive.tv.R;
import sdk.dive.tv.eventbus.EventBusManager;
import sdk.dive.tv.eventbus.EventBusIds;
import sdk.dive.tv.eventbus.OpenCard;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.data.ModuleMainStyle;
import sdk.dive.tv.ui.data.ModuleStyleData;
import sdk.dive.tv.ui.data.ModuleStyleProperty;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.data.SquareImageRowData;
import sdk.dive.tv.ui.utils.CropSquareTransformation2;

/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public class SquareImageItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<SquareImageRowData> rows;
    private Picasso mPicasso;
    private Typeface latoRegular;
    private Typeface latoSemibold;
    private boolean styleConfigRetrieved = false;
    private boolean darkStyle = false;
    private TvCardDetailListener tvCardDetailListener;
    private Context context;
    private String parentId;
    private String parentType;
    private HashMap<String, ModuleStyleData> genericStyles;

    public SquareImageItemsAdapter(Context context, ArrayList<SquareImageRowData> rows, TvCardDetailListener tvCardDetailListener, String cardId) {
        super();
        this.rows = rows;
        mPicasso = Picasso.with(context);
        latoRegular = Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR);
        latoSemibold = Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD);
        this.tvCardDetailListener = tvCardDetailListener;
        this.context = context;
        this.parentId = cardId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.square_image_item_row, parent, false);
        return new SquareImageItemsItemHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final SquareImageRowData row = rows.get(position);
        if (row.getImage() != null && row.getImage().getThumb() != null) {
            ((sdk.dive.tv.ui.modules.adapters.SquareImageItemsAdapter.SquareImageItemsItemHolder) holder).image.post(new Runnable() {
                @Override
                public void run() {
                    mPicasso
                            .load(row.getImage().getThumb())
                            .transform(new CropSquareTransformation2(((sdk.dive.tv.ui.modules.adapters.SquareImageItemsAdapter.SquareImageItemsItemHolder) holder).image.getMeasuredWidth(), ((sdk.dive.tv.ui.modules.adapters.SquareImageItemsAdapter.SquareImageItemsItemHolder) holder).image.getMeasuredHeight(),
                                    row.getImage().getAnchorX().intValueExact(), row.getImage().getAnchorY().intValueExact()))
//                            .resize(((SquareImageItemsAdapter.SquareImageItemsItemHolder) holder).image.getMeasuredWidth(), ((SquareImageItemsAdapter.SquareImageItemsItemHolder) holder).image.getMeasuredHeight())
                            .into(((sdk.dive.tv.ui.modules.adapters.SquareImageItemsAdapter.SquareImageItemsItemHolder) holder).image);
                }
            });
        } else { //TODO:see this case.
            ((sdk.dive.tv.ui.modules.adapters.SquareImageItemsAdapter.SquareImageItemsItemHolder) holder).image.getLayoutParams().height = (int) context.getResources().getDimension(R.dimen.simageitems_image_nophoto);
            ((sdk.dive.tv.ui.modules.adapters.SquareImageItemsAdapter.SquareImageItemsItemHolder) holder).image.getLayoutParams().width = (int) context.getResources().getDimension(R.dimen.simageitems_image_nophoto);
            ((sdk.dive.tv.ui.modules.adapters.SquareImageItemsAdapter.SquareImageItemsItemHolder) holder).image.post(new Runnable() {
                @Override
                public void run() {
                    mPicasso.load(R.drawable.ico_nophoto_medium)
                            .into(((sdk.dive.tv.ui.modules.adapters.SquareImageItemsAdapter.SquareImageItemsItemHolder) holder).image);
                }
            });
        }

        configureStyle((SquareImageItemsItemHolder) holder);

        if (row.getTitle() != null) {

            ((SquareImageItemsItemHolder) holder).title.setText(row.getTitle());
            ((SquareImageItemsItemHolder) holder).title.setTypeface(latoSemibold);

        }
        if (row.getSubtitle() != null) {
            ((SquareImageItemsItemHolder) holder).subtitle.setText(row.getSubtitle());
            ((SquareImageItemsItemHolder) holder).subtitle.setTypeface(latoRegular);
        }

      ((SquareImageItemsItemHolder) holder).container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (row.hasContent()) {
//                    OpenCard openCard = new OpenCard(EventBusIds.OPEN_CARD.getName(), row.getCardId(), row.getCardType());
//                    EventBusManager.getInstance().post(openCard);
                    tvCardDetailListener.onCallCardDetail(row.getCardId(), row.getCardVersion(), Card.TypeEnum.fromValue(row.getCardType()));
                }
            }
        });

    }

    public void setModuleStyle(String moduleName) {

        if (!styleConfigRetrieved) {
            if (tvCardDetailListener != null && tvCardDetailListener.getModuleStyles(moduleName) != null) {
                if (ModuleMainStyle.DARK.getName().equals(tvCardDetailListener.getModuleStyles(moduleName).get(ModuleStyleProperty.MAIN_STYLE.getName()).getValue())) {
                    darkStyle = true;
                }
            } else if (tvCardDetailListener != null && tvCardDetailListener.getModuleStyles("SquareImageItems") != null) {
                if (ModuleMainStyle.DARK.getName().equals(tvCardDetailListener.getModuleStyles("SquareImageItems").get(ModuleStyleProperty.MAIN_STYLE.getName()).getValue())) {
                    darkStyle = true;
                }
            }
            if (tvCardDetailListener != null && tvCardDetailListener.getGenericStyles() != null){
                genericStyles = tvCardDetailListener.getGenericStyles();
            }

            styleConfigRetrieved = true;
        }
    }

    private void configureStyle(sdk.dive.tv.ui.modules.adapters.SquareImageItemsAdapter.SquareImageItemsItemHolder holder) {

        if (genericStyles!=null){
            holder.container.setBackgroundColor(Color.parseColor(genericStyles.get("backgroundColor").getValue()));
        } else if (darkStyle) {
            holder.container.setBackgroundColor(Utils.getColor(context, R.color.black));

            holder.info.setBackgroundColor(Utils.getColor(context, R.color.black));

            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion < 23) {
                holder.title.setTextAppearance(context, R.style.MinicardTitleLight);
            } else {
                holder.title.setTextAppearance(R.style.MinicardTitleLight);
            }
        }
    }

    @Override
    public int getItemCount() {
        return rows.size() > 15 ? 15 : rows.size();
    }

    private static class SquareImageItemsItemHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView subtitle;
        LinearLayout container;
        LinearLayout info;

        SquareImageItemsItemHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.simageitems_image);
            title = (TextView) v.findViewById(R.id.simageitems_title);
            subtitle = (TextView) v.findViewById(R.id.simageitems_subtitle);
            container = (LinearLayout) v.findViewById(R.id.simageitems_container);
            info = (LinearLayout) v.findViewById(R.id.simageitems_info);
        }
    }

}


