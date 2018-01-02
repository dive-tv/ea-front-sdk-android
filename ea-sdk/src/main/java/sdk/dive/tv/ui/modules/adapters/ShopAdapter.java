package sdk.dive.tv.ui.modules.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Product;

import java.util.ArrayList;
import java.util.Currency;

import sdk.dive.tv.R;
import sdk.dive.tv.eventbus.EventBusIds;
import sdk.dive.tv.eventbus.EventBusManager;
import sdk.dive.tv.eventbus.OpenWeb;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;

/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public class ShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final TvCardDetailListener tvCardDetailListener;
    ArrayList<Product> rows;
    private Picasso mPicasso;
    private Typeface latoRegular;
    private Typeface latoSemibold;
    private boolean darkStyle = false;
    private String parentId;
    private String parentType;

    public ShopAdapter(Context context, ArrayList<Product> data, TvCardDetailListener tvCardDetailListener, String cardId, String cardType) {
        super();
        this.rows = data;
        this.context = context;
        this.tvCardDetailListener = tvCardDetailListener;
        mPicasso = Picasso.with(context);
        latoRegular = Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR);
        latoSemibold = Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD);
        parentId = cardId;
        parentType = cardType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_row, parent, false);
        return new ShopItemHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        final Product row = rows.get(position);
        if (row.getImage() != null) {
            ((ShopItemHolder) holder).image.post(new Runnable() {
                @Override
                public void run() {
                    mPicasso
                            .load(row.getImage())
                            .fit().centerCrop()
                            .into(((ShopItemHolder) holder).image);
                }
            });
        } else { //TODO:see this case.
            ((ShopItemHolder) holder).image.getLayoutParams().height = (int) context.getResources().getDimension(R.dimen.simageitems_image_nophoto);
            ((ShopItemHolder) holder).image.getLayoutParams().width = (int) context.getResources().getDimension(R.dimen.simageitems_image_nophoto);
            ((ShopItemHolder) holder).image.post(new Runnable() {
                @Override
                public void run() {
                    mPicasso.load(R.drawable.ico_nophoto_medium)
                            .into(((ShopItemHolder) holder).image);
                }
            });
        }

        String price = (row.getPrice() != null ? row.getPrice() : "") + (row.getCurrency() != null ? Currency.getInstance(row.getCurrency()).getSymbol() : "");
        ((ShopItemHolder) holder).price.setText(price);
        ((ShopItemHolder) holder).price.setTypeface(latoSemibold);
        ((ShopItemHolder) holder).shop.setText(row.getSource().getName());
        ((ShopItemHolder) holder).shop.setTypeface(latoRegular);

        if (row.getUrl() != null) {
            ((ShopItemHolder) holder).container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBusManager.getInstance().post(new OpenWeb(EventBusIds.OPEN_WEB.getName(), row.getUrl()));
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    public static class ShopItemHolder extends RecyclerView.ViewHolder {

        private LinearLayout container;
        private ImageView image;
        private TextView price;
        private TextView shop;
        private LinearLayout shopInfo;
        private FrameLayout shopNow;

        public ShopItemHolder(View v) {
            super(v);
            container = (LinearLayout) v.findViewById(R.id.simageitems_container);
            image = (ImageView) v.findViewById(R.id.shop_image);
            price = (TextView) v.findViewById(R.id.shop_price);
            shop = (TextView) v.findViewById(R.id.shop_shop);
            shopInfo = (LinearLayout) v.findViewById(R.id.shop_info);
            shopNow = (FrameLayout) v.findViewById(R.id.shop_button);
        }
    }

}

