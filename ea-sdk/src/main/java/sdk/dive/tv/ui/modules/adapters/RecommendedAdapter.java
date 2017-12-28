package sdk.dive.tv.ui.modules.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.modules.data.RecommendedData;
import sdk.dive.tv.ui.utils.CropSquareTransformation2;

/**
 * Created by Tagsonomy S.L. on 15/11/2016.
 */

public class RecommendedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private ArrayList<RecommendedData> rows;
    private Picasso mPicasso;
    private String parentId;
    private String parentType;

    public RecommendedAdapter(Context context, ArrayList<RecommendedData> rows, String cardId, String cardType) {
        super();
        this.context = context;
        this.rows = rows;
        this.parentId = cardId;
        this.parentType = cardType;
        mPicasso = Picasso.with(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_row, parent, false);
        return new sdk.dive.tv.ui.modules.adapters.RecommendedAdapter.RecommendedItemHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final RecommendedData rowItem = rows.get(holder.getAdapterPosition());

        if (rowItem.getImage() != null && rowItem.getImage().getThumb() != null) {

            ((sdk.dive.tv.ui.modules.adapters.RecommendedAdapter.RecommendedItemHolder) holder).image.post(new Runnable() {
                @Override
                public void run() {
                    mPicasso
                            .load(rowItem.getImage().getThumb())
                            .transform(new CropSquareTransformation2(((sdk.dive.tv.ui.modules.adapters.RecommendedAdapter.RecommendedItemHolder) holder).image.getMeasuredWidth(), ((sdk.dive.tv.ui.modules.adapters.RecommendedAdapter.RecommendedItemHolder) holder).image.getMeasuredHeight(),
                                    rowItem.getImage().getAnchorX().intValueExact(), rowItem.getImage().getAnchorY().intValueExact()))
                            .into(((sdk.dive.tv.ui.modules.adapters.RecommendedAdapter.RecommendedItemHolder) holder).image);

                }
            });
        } else {
            ((sdk.dive.tv.ui.modules.adapters.RecommendedAdapter.RecommendedItemHolder) holder).image.getLayoutParams().height = (int) context.getResources().getDimension(R.dimen.simageitems_image_nophoto);
            ((sdk.dive.tv.ui.modules.adapters.RecommendedAdapter.RecommendedItemHolder) holder).image.getLayoutParams().width = (int) context.getResources().getDimension(R.dimen.simageitems_image_nophoto);
            ((sdk.dive.tv.ui.modules.adapters.RecommendedAdapter.RecommendedItemHolder) holder).image.post(new Runnable() {
                @Override
                public void run() {
                    mPicasso
                            .load(R.drawable.ico_nophoto_medium)
                            .into(((sdk.dive.tv.ui.modules.adapters.RecommendedAdapter.RecommendedItemHolder) holder).image);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }


    public static class RecommendedItemHolder extends RecyclerView.ViewHolder {

        ImageView image;


        public RecommendedItemHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.imgv_recommended_image);
        }
    }


}


