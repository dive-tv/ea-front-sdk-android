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
import com.touchvie.sdk.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.data.ModuleMainStyle;
import sdk.dive.tv.ui.data.ModuleStyleData;
import sdk.dive.tv.ui.data.ModuleStyleProperty;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.utils.CropSquareTransformation2;

/**
 * Created by Tagsonomy S.L. on 18/11/2016.
 */

public class GroupedModuleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final TvCardDetailListener tvCardDetailListener;
    private final Context context;
    private ArrayList<Card> rows;
    private Picasso mPicasso;
    private Typeface latoRegular;
    private Typeface latoSemibold;
    private boolean darkStyle = false;
    private boolean styleConfigRetrieved = false;
    private String parentId;
    private HashMap<String, ModuleStyleData> genericStyles;

    public GroupedModuleAdapter(Context context, ArrayList<Card> rows, TvCardDetailListener tvCardDetailListener, String cardId) {
        super();
        this.rows = rows;
        this.context = context;
        this.tvCardDetailListener = tvCardDetailListener;
        this.parentId = cardId;
        mPicasso = Picasso.with(context);
        latoRegular = Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR);
        latoSemibold = Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD);
    }

    @Override
    public int getItemViewType(int position) {

        if (rows.get(position).getProducts() != null && rows.get(position).getProducts().size() > 0) {
            return 0;
        } else {
            return 1;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View v0 = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_list_row, parent, false);
                return new sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedBuyItemHolder(v0);
            case 1:
                View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.square_image_item_row, parent, false);
                return new sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedItemHolder(v1);
            default:
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.square_image_item_row, parent, false);
                return new sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedItemHolder(v2);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Card row = rows.get(holder.getAdapterPosition());

        if (getItemViewType(holder.getAdapterPosition()) == 0) {

            ArrayList<Product> arr = new ArrayList<>();
            Collections.addAll(arr, row.getProducts().toArray(new Product[row.getProducts().size()]));
            ArrayList<Product> products = arr;
            final Product product = products.get(0);

            if (product.getImage() != null && product.getImage() != null) {
                ((sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedBuyItemHolder) holder).image.post(new Runnable() {
                    @Override
                    public void run() {
                        mPicasso
                                .load(product.getImage())
                                .fit().centerCrop()
                                .into(((sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedBuyItemHolder) holder).image);
                    }
                });
            } else {
                ((sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedBuyItemHolder) holder).image.post(new Runnable() {
                    @Override
                    public void run() {
                        mPicasso.load(R.drawable.ico_nophoto_medium)
                                .fit().centerCrop()
                                .into(((sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedBuyItemHolder) holder).image);
                    }
                });
            }

            if (product.getPrice() != null) {
                ((GroupedBuyItemHolder) holder).price.setText(String.valueOf(product.getPrice()));
                ((GroupedBuyItemHolder) holder).price.setTypeface(latoSemibold);
            }

            if (product.getSource() != null && product.getSource().getName() != null) {
                ((GroupedBuyItemHolder) holder).merchant.setText(String.valueOf(product.getSource().getName()));
                ((GroupedBuyItemHolder) holder).merchant.setTypeface(latoRegular);
            }

            ((GroupedBuyItemHolder) holder).container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            configureStyle((sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedBuyItemHolder) holder);

        } else {
            if (row.getImage() != null && row.getImage().getThumb() != null) {
                ((sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedItemHolder) holder).image.post(new Runnable() {
                    @Override
                    public void run() {
                        mPicasso
                                .load(row.getImage().getThumb())
                                .transform(new CropSquareTransformation2(((sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedItemHolder) holder).image.getMeasuredWidth(), ((sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedItemHolder) holder).image.getMeasuredHeight(),
                                        row.getImage().getAnchorX().intValueExact(), row.getImage().getAnchorY().intValueExact()))
                                .into(((sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedItemHolder) holder).image);
                    }
                });
            } else { //TODO:see this case.
                ((sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedItemHolder) holder).image.getLayoutParams().height = (int) context.getResources().getDimension(R.dimen.simageitems_image_nophoto);
                ((sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedItemHolder) holder).image.getLayoutParams().width = (int) context.getResources().getDimension(R.dimen.simageitems_image_nophoto);
                ((sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedItemHolder) holder).image.post(new Runnable() {
                    @Override
                    public void run() {
                        mPicasso.load(R.drawable.ico_nophoto_medium)
                                .into(((sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedItemHolder) holder).image);
                    }
                });
            }

            configureStyle((sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedItemHolder) holder);

            if (row.getTitle() != null) {
                ((sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedItemHolder) holder).title.setText(row.getTitle());
                ((sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedItemHolder) holder).title.setTypeface(latoSemibold);
            }
        }
    }


    public void setModuleStyle(String moduleName) {

        if (!styleConfigRetrieved) {
            if (tvCardDetailListener != null && tvCardDetailListener.getModuleStyles(moduleName) != null) {
                if (ModuleMainStyle.DARK.getName().equals(tvCardDetailListener.getModuleStyles(moduleName).get(ModuleStyleProperty.MAIN_STYLE.getName()).getValue())) {
                    darkStyle = true;
                }
            } else if (tvCardDetailListener != null && tvCardDetailListener.getModuleStyles("GroupedModule") != null) {
                if (ModuleMainStyle.DARK.getName().equals(tvCardDetailListener.getModuleStyles("GroupedModule").get(ModuleStyleProperty.MAIN_STYLE.getName()).getValue())) {
                    darkStyle = true;
                }
            }
            styleConfigRetrieved = true;
        }
        if (tvCardDetailListener != null && tvCardDetailListener.getGenericStyles() != null){
            genericStyles = tvCardDetailListener.getGenericStyles();
        }
    }

    private void configureStyle(sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedBuyItemHolder holder) {
        if (darkStyle) {
            holder.container.setBackgroundColor(Utils.getColor(context, R.color.black));

            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion < 23) {
                holder.price.setTextAppearance(context, R.style.MinicardTitleLight);
            } else {
                holder.price.setTextAppearance(R.style.MinicardTitleLight);
            }
        }
        if (genericStyles!=null){
            int backgroundColor = Color.parseColor(genericStyles.get("backgroundColor").getValue());
            holder.container.setBackgroundColor(Utils.getColor(context, backgroundColor));
        }
    }

    private void configureStyle(sdk.dive.tv.ui.modules.adapters.GroupedModuleAdapter.GroupedItemHolder holder) {

        if (darkStyle) {
             holder.container.setBackgroundColor(Utils.getColor(context, R.color.black));

            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion < 23) {
                holder.title.setTextAppearance(context, R.style.MinicardTitleLight);
            } else {
                holder.title.setTextAppearance(R.style.MinicardTitleLight);
            }

        }
        if (genericStyles!=null){
            int backgroundColor = Color.parseColor(genericStyles.get("backgroundColor").getValue());
            holder.container.setBackgroundColor(Utils.getColor(context, backgroundColor));
        }

    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    private static class GroupedBuyItemHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView price;
        TextView merchant;
        LinearLayout container;

        GroupedBuyItemHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.shop_image);
            price = (TextView) v.findViewById(R.id.shop_price);
            merchant = (TextView) v.findViewById(R.id.shop_shop);
            container = (LinearLayout) v.findViewById(R.id.simageitems_container);
        }
    }

    private static class GroupedItemHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        LinearLayout container;

        GroupedItemHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.simageitems_image);
            title = (TextView) v.findViewById(R.id.simageitems_title);
            container = (LinearLayout) v.findViewById(R.id.simageitems_container);
        }
    }
}


