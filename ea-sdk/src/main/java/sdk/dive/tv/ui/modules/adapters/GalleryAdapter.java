package sdk.dive.tv.ui.modules.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.ImageData;

import java.util.ArrayList;

import sdk.dive.tv.R;

/**
 * Created by Tagsonomy S.L. on 10/10/2016.
 */

public class GalleryAdapter extends TVScrollAdapter {

    Context context;
    Picasso mPicasso;

    /**
     * Instantiates a new Cards gallery grid adapter.
     *
     * @param context the context
     */
    public GalleryAdapter(Context context, ArrayList<ImageData> allData) {
        super();
        this.context = context;
        this.allData.addAll(allData);
        this.mPicasso = Picasso.with(context);
        setData();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_module_gallery_item, parent, false);
        return new GalleryHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ImageData row = (ImageData) rowItems[holder.getAdapterPosition()];

        if (row != null) {
            mPicasso.load(row.getThumb())
                    .into(((GalleryHolder) holder).image);
            ((GalleryHolder) holder).noimage.setVisibility(View.GONE);
            ((GalleryHolder) holder).image.setVisibility(View.VISIBLE);
        } else {
            ((GalleryHolder) holder).noimage.setVisibility(View.VISIBLE);
            ((GalleryHolder) holder).image.setVisibility(View.GONE);
        }
    }

    private static class GalleryHolder extends RecyclerView.ViewHolder {

        private RelativeLayout container;
        private ImageView image;
        private ImageView noimage;

        GalleryHolder(View v) {
            super(v);
            container = (RelativeLayout) v.findViewById(R.id.tv_module_horizontal_item_container);
            image = (ImageView) v.findViewById(R.id.tv_module_horizontal_item_image);
            noimage = (ImageView) v.findViewById(R.id.tv_module_horizontal_item_noimage);
        }
    }
}