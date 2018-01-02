package sdk.dive.tv.ui.modules.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Product;

import java.util.ArrayList;
import java.util.Currency;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;

import static android.view.View.GONE;

/**
 * Created by Tagsonomy S.L. on 10/10/2016.
 */

public class TravelShopAdapter extends TVScrollTravelShopAdapter {

    private final TvCardDetailListener tvCardDetailListener;
    Context context;
    Picasso mPicasso;
    private Typeface latoRegular;
    private Typeface latoSemibold;

    /**
     * Instantiates a new Cards gallery grid adapter.
     *
     * @param context              the context
     * @param allData
     * @param tvCardDetailListener
     */
    public TravelShopAdapter(Context context, ArrayList<Product> allData, TvCardDetailListener tvCardDetailListener) {
        super();
        this.context = context;
        this.allData.addAll(allData);
        this.tvCardDetailListener = tvCardDetailListener;
        this.mPicasso = Picasso.with(context);
        latoRegular = Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR);
        latoSemibold = Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD);
        setData();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_travel_shop_row, parent, false);
        return new TravelShopItemHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Product productData = (Product) rowItems[holder.getAdapterPosition()];

        if (productData.getImage() != null) {
            ((TravelShopItemHolder) holder).image.post(new Runnable() {
                @Override
                public void run() {
                    mPicasso
                            .load(productData.getImage())
                            .fit().centerCrop()
                            .into(((TravelShopItemHolder) holder).image);
                }
            });
        } else {
            ((TravelShopItemHolder) holder).image.getLayoutParams().height = (int) context.getResources().getDimension(R.dimen.simageitems_image_nophoto);
            ((TravelShopItemHolder) holder).image.getLayoutParams().width = (int) context.getResources().getDimension(R.dimen.simageitems_image_nophoto);
            ((TravelShopItemHolder) holder).image.post(new Runnable() {
                @Override
                public void run() {
                    mPicasso.load(R.drawable.ico_nophoto_medium)
                            .into(((TravelShopItemHolder) holder).image);
                }
            });
        }

        if (productData.getSource() != null && productData.getSource().getImage() != null) {
            ((TravelShopItemHolder) holder).image.post(new Runnable() {
                @Override
                public void run() {
                    mPicasso
                            .load(productData.getSource().getImage())
                            .into(((TravelShopItemHolder) holder).brand);
                }
            });
        } else {
            ((TravelShopItemHolder) holder).brand.setVisibility(GONE);
        }

        if (productData.getTitle() != null) {
            ((TravelShopItemHolder) holder).title.setText(productData.getTitle());
            ((TravelShopItemHolder) holder).title.setTypeface(latoSemibold);
        }

        if (productData.getRating() != null && productData.getRating() > 0) {
            ((TravelShopItemHolder) holder).rating.setVisibility(View.VISIBLE);

            int number = (int) Math.floor(productData.getRating());
            switch (number) {
                case 0:
                    if (productData.getRating() > 0.5) {
                        mPicasso
                                .load(R.drawable.ico_star_mitad)
                                .into(((TravelShopItemHolder) holder).star1);
                    }

                    ((TravelShopItemHolder) holder).star1.setColorFilter(Utils.getColor(context, R.color.tealGreen));
                    ((TravelShopItemHolder) holder).star2.setColorFilter(Utils.getColor(context, R.color.tealGreen));
                    ((TravelShopItemHolder) holder).star3.setColorFilter(Utils.getColor(context, R.color.tealGreen));
                    ((TravelShopItemHolder) holder).star4.setColorFilter(Utils.getColor(context, R.color.tealGreen));
                    ((TravelShopItemHolder) holder).star5.setColorFilter(Utils.getColor(context, R.color.tealGreen));

                    break;
                case 1:
                    if (productData.getRating() > 1.5) {
                        mPicasso
                                .load(R.drawable.ico_star_mitad)
                                .into(((TravelShopItemHolder) holder).star2);
                    }

                    ((TravelShopItemHolder) holder).star1.setColorFilter(Utils.getColor(context, R.color.tealGreen));
                    ((TravelShopItemHolder) holder).star2.setColorFilter(Utils.getColor(context, R.color.warm_grey));
                    ((TravelShopItemHolder) holder).star3.setColorFilter(Utils.getColor(context, R.color.warm_grey));
                    ((TravelShopItemHolder) holder).star4.setColorFilter(Utils.getColor(context, R.color.warm_grey));
                    ((TravelShopItemHolder) holder).star5.setColorFilter(Utils.getColor(context, R.color.warm_grey));
                    break;
                case 2:
                    if (productData.getRating() > 2.5) {
                        mPicasso
                                .load(R.drawable.ico_star_mitad)
                                .into(((TravelShopItemHolder) holder).star3);
                    }

                    ((TravelShopItemHolder) holder).star1.setColorFilter(Utils.getColor(context, R.color.tealGreen));
                    ((TravelShopItemHolder) holder).star2.setColorFilter(Utils.getColor(context, R.color.tealGreen));
                    ((TravelShopItemHolder) holder).star3.setColorFilter(Utils.getColor(context, R.color.warm_grey));
                    ((TravelShopItemHolder) holder).star4.setColorFilter(Utils.getColor(context, R.color.warm_grey));
                    ((TravelShopItemHolder) holder).star5.setColorFilter(Utils.getColor(context, R.color.warm_grey));
                    break;
                case 3:
                    if (productData.getRating() > 3.5) {
                        mPicasso
                                .load(R.drawable.ico_star_mitad)
                                .into(((TravelShopItemHolder) holder).star4);
                    }

                    ((TravelShopItemHolder) holder).star1.setColorFilter(Utils.getColor(context, R.color.tealGreen));
                    ((TravelShopItemHolder) holder).star2.setColorFilter(Utils.getColor(context, R.color.tealGreen));
                    ((TravelShopItemHolder) holder).star3.setColorFilter(Utils.getColor(context, R.color.tealGreen));
                    ((TravelShopItemHolder) holder).star4.setColorFilter(Utils.getColor(context, R.color.warm_grey));
                    ((TravelShopItemHolder) holder).star5.setColorFilter(Utils.getColor(context, R.color.warm_grey));
                    break;
                case 4:
                    if (productData.getRating() > 4.5) {
                        mPicasso
                                .load(R.drawable.ico_star_mitad)
                                .into(((TravelShopItemHolder) holder).star5);
                    }

                    ((TravelShopItemHolder) holder).star1.setColorFilter(Utils.getColor(context, R.color.tealGreen));
                    ((TravelShopItemHolder) holder).star2.setColorFilter(Utils.getColor(context, R.color.tealGreen));
                    ((TravelShopItemHolder) holder).star3.setColorFilter(Utils.getColor(context, R.color.tealGreen));
                    ((TravelShopItemHolder) holder).star4.setColorFilter(Utils.getColor(context, R.color.tealGreen));
                    ((TravelShopItemHolder) holder).star5.setColorFilter(Utils.getColor(context, R.color.warm_grey));
                    break;
                case 5:
                    ((TravelShopItemHolder) holder).star1.setColorFilter(Utils.getColor(context, R.color.tealGreen));
                    ((TravelShopItemHolder) holder).star2.setColorFilter(Utils.getColor(context, R.color.tealGreen));
                    ((TravelShopItemHolder) holder).star3.setColorFilter(Utils.getColor(context, R.color.tealGreen));
                    ((TravelShopItemHolder) holder).star4.setColorFilter(Utils.getColor(context, R.color.tealGreen));
                    ((TravelShopItemHolder) holder).star5.setColorFilter(Utils.getColor(context, R.color.tealGreen));
                    break;
            }

        } else {
            ((TravelShopItemHolder) holder).rating.setVisibility(GONE);
        }

        if (productData.getPrice() != null && productData.getPrice() > 0) {
            ((TravelShopItemHolder) holder).from.setVisibility(View.VISIBLE);
            ((TravelShopItemHolder) holder).from.setTypeface(latoRegular);
            ((TravelShopItemHolder) holder).price.setVisibility(View.VISIBLE);
            String price = (productData.getPrice() != null ? productData.getPrice() : "") + (productData.getCurrency() != null ? Currency.getInstance(productData.getCurrency()).getSymbol() : "");
            ((TravelShopItemHolder) holder).price.setText(price);
            ((TravelShopItemHolder) holder).price.setTypeface(latoSemibold);
        } else {
            ((TravelShopItemHolder) holder).from.setVisibility(View.GONE);
            ((TravelShopItemHolder) holder).price.setVisibility(View.GONE);
        }

        if (productData.getAddress() != null) {
            ((TravelShopItemHolder) holder).address.setText(productData.getAddress());
            ((TravelShopItemHolder) holder).address.setTypeface(latoRegular);
        }

        if (productData.getUrl() != null) {
            ((TravelShopItemHolder) holder).container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    EventBusManager.getInstance().post(new OpenWeb(EventBusIds.OPEN_WEB.getName(), productData.getUrl()));
                    tvCardDetailListener.onShowProduct(productData.getUrl());

                }
            });
            ((TravelShopItemHolder) holder).bookNow.setVisibility(View.VISIBLE);
        } else {
            ((TravelShopItemHolder) holder).bookNow.setVisibility(GONE);
        }

    }

    private static class TravelShopItemHolder extends RecyclerView.ViewHolder {

        private LinearLayout container;
        private ImageView image;
        private ImageView brand;
        private RelativeLayout infoBox;
        private TextView title;
        private TextView address;
        private LinearLayout rating;
        private ImageView star1;
        private ImageView star2;
        private ImageView star3;
        private ImageView star4;
        private ImageView star5;
        private RelativeLayout priceBox;
        private TextView from;
        private TextView price;
        private TextView bookNow;


        TravelShopItemHolder(View v) {
            super(v);
            container = (LinearLayout) v.findViewById(R.id.travelshop_container);
            image = (ImageView) v.findViewById(R.id.imgv_travelshop_image);
            brand = (ImageView) v.findViewById(R.id.imgv_travelshop_brand);
            infoBox = (RelativeLayout) v.findViewById(R.id.travelshop_infobox);
            title = (TextView) v.findViewById(R.id.txtv_travelshop_title);
            address = (TextView) v.findViewById(R.id.txtv_travelshop_address);
            rating = (LinearLayout) v.findViewById(R.id.llay_travelshop_stars);
            star1 = (ImageView) v.findViewById(R.id.imgv_travelshop_star1);
            star2 = (ImageView) v.findViewById(R.id.imgv_travelshop_star2);
            star3 = (ImageView) v.findViewById(R.id.imgv_travelshop_star3);
            star4 = (ImageView) v.findViewById(R.id.imgv_travelshop_star4);
            star5 = (ImageView) v.findViewById(R.id.imgv_travelshop_star5);
            priceBox = (RelativeLayout) v.findViewById(R.id.travelshop_pricebox);
            from = (TextView) v.findViewById(R.id.txtv_travelshop_from);
            price = (TextView) v.findViewById(R.id.txtv_travelshop_price);
            bookNow = (TextView) v.findViewById(R.id.txtv_travelshop_button);


        }
    }
}