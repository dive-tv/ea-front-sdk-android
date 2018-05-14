package sdk.dive.tv.ui.modules.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import sdk.dive.tv.ui.modules.data.CastLocationRowData;
import sdk.dive.tv.ui.utils.CropSquareTransformation2;

/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public class CastLocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CastLocationRowData> rows;
    private Typeface latoRegular;
    private Typeface latoSemibold;
    private Picasso mPicasso;
    private TvCardDetailListener tvCardDetailListener;
    private Context context;
    private String parentId;
    private String parentType;

    private boolean styleConfigRetrieved = false;
    private boolean darkStyle = false;
    private HashMap<String, ModuleStyleData> genericStyles;

    public CastLocationAdapter(Context context, ArrayList<CastLocationRowData> rows, TvCardDetailListener tvCardDetailListener, String cardId) {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_cast_row, parent, false);
        return new CastLocationItemHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final CastLocationRowData row = rows.get(position);

        if(genericStyles!=null && genericStyles.get("backgroundModuleColor")!=null) {
            ((CastLocationItemHolder) holder).container.setBackgroundColor(Color.parseColor(genericStyles.get("backgroundModuleColor").getValue()));
        }

        if (row.getImage() != null && row.getImage().getThumb() != null) {
            ((CastLocationItemHolder) holder).image.getLayoutParams().height = (int) context.getResources().getDimension(R.dimen.simageitems_image_height);
            ((CastLocationItemHolder) holder).image.getLayoutParams().width = (int) context.getResources().getDimension(R.dimen.simageitems_image_height);
            ((CastLocationItemHolder) holder).image.post(new Runnable() {
                @Override
                public void run() {
                    mPicasso
                            .load(row.getImage().getThumb())
                            .transform(new CropSquareTransformation2(((CastLocationItemHolder) holder).image.getMeasuredWidth(), ((CastLocationItemHolder) holder).image.getMeasuredHeight(),
                                    row.getImage().getAnchorX().intValueExact(), row.getImage().getAnchorY().intValueExact()))
//                            .resize( ((CastLocationItemHolder) holder).image.getMeasuredWidth(),  ((CastLocationItemHolder) holder).image.getMeasuredHeight())
                            .into(((CastLocationItemHolder) holder).image);
                }
            });
        } else {
            ((CastLocationItemHolder) holder).image.getLayoutParams().height = (int) context.getResources().getDimension(R.dimen.simageitems_image_nophoto);
            ((CastLocationItemHolder) holder).image.getLayoutParams().width = (int) context.getResources().getDimension(R.dimen.simageitems_image_nophoto);
            ((CastLocationItemHolder) holder).image.post(new Runnable() {
                @Override
                public void run() {
                    mPicasso.load(R.drawable.ico_nophoto_medium)
                            .into(((CastLocationItemHolder) holder).image);
                }
            });
        }

        ((CastLocationItemHolder) holder).image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (row.isRealItemHasContent()) {
//                    OpenCard openCard = new OpenCard(EventBusIds.OPEN_CARD.getName(), row.getRealItemCardId(), row.getRealItemType());
//                    EventBusManager.getInstance().post(openCard);
                    tvCardDetailListener.onCallCardDetail(row.getRealItemCardId(), row.getRealItemCardVersion(), Card.TypeEnum.fromValue(row.getRealItemType()));

                }
            }
        });

        ((CastLocationItemHolder) holder).fictionalItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (row.isRealItemHasContent()) {
//                    OpenCard openCard = new OpenCard(EventBusIds.OPEN_CARD.getName(), row.getRealItemCardId(), row.getRealItemType());
//                    EventBusManager.getInstance().post(openCard);
                    tvCardDetailListener.onCallCardDetail(row.getRealItemCardId(), row.getRealItemCardVersion(), Card.TypeEnum.fromValue(row.getRealItemType()));
                }
            }
        });

        ((CastLocationItemHolder) holder).fictionalItem.setTypeface(latoSemibold);
        if (row.getRealItem() != null) {
            ((CastLocationItemHolder) holder).fictionalItem.setText(row.getRealItem());
        } else if (row.getRealItem() != null) {
            ((CastLocationItemHolder) holder).fictionalItem.setText(row.getFictionalItem());
        } else {
            ((CastLocationItemHolder) holder).fictionalItem.setText("");
        }

        if (row.getFictionalItem() != null) {
            ((CastLocationItemHolder) holder).fictionalItem.setText(row.getFictionalItem());
        }


    }

    public void setModuleStyle(String moduleName) {
        if (!styleConfigRetrieved) {
            if (tvCardDetailListener != null && tvCardDetailListener.getModuleStyles(moduleName) != null) {
                if (ModuleMainStyle.DARK.getName().equals(tvCardDetailListener.getModuleStyles(moduleName).get(ModuleStyleProperty.MAIN_STYLE.getName()).getValue())) {
                    darkStyle = true;
                }
            } else if (tvCardDetailListener != null && tvCardDetailListener.getModuleStyles("CastLocation") != null) {
                if (ModuleMainStyle.DARK.getName().equals(tvCardDetailListener.getModuleStyles("CastLocation").get(ModuleStyleProperty.MAIN_STYLE.getName()).getValue())) {
                    darkStyle = true;
                }
            }
            if (tvCardDetailListener != null && tvCardDetailListener.getGenericStyles() != null){
                genericStyles = tvCardDetailListener.getGenericStyles();
            }
            styleConfigRetrieved = true;
        }
    }

    @Override
    public int getItemCount() {
        return rows.size() >= 15 ? 15 : rows.size();
    }

    public static class CastLocationItemHolder extends RecyclerView.ViewHolder {

        private CardView container;
        private ImageView image;
        private TextView fictionalItem;

        public CastLocationItemHolder(View v) {
            super(v);
            container = (CardView) v.findViewById(R.id.cview_container);
            image = (ImageView) v.findViewById(R.id.castlocation_image);
            fictionalItem = (TextView) v.findViewById(R.id.tv_cast_row_title);
        }
    }
}

