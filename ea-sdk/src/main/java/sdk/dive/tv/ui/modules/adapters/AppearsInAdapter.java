package sdk.dive.tv.ui.modules.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.modules.data.ImageTextsRowData;

/**
 * Created by Tagsonomy S.L. on 18/10/2016.
 */

public class AppearsInAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ImageTextsRowData> rows;
    private Context context;
    private Typeface latoRegular;
    private Typeface latoSemibold;
    private Picasso mPicasso;
    private String parentId;
    private String parentType;

    public AppearsInAdapter(Context context, ArrayList<ImageTextsRowData> rows, String parentId, String parentType) {
        super();
        this.rows = rows;
        this.context = context;
        this.parentId = parentId;
        this.parentType = parentType;
        mPicasso = Picasso.with(context);
        latoRegular = Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR);
        latoSemibold = Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_rectang_image_row, parent, false);
        return new sdk.dive.tv.ui.modules.adapters.AppearsInAdapter.AppearsInItemHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ImageTextsRowData row = rows.get(position);
        if (row.getImage() != null) {
            mPicasso.load(row.getImage())
                    .into(((sdk.dive.tv.ui.modules.adapters.AppearsInAdapter.AppearsInItemHolder) holder).image);
        } else {
            ((sdk.dive.tv.ui.modules.adapters.AppearsInAdapter.AppearsInItemHolder) holder).image.getLayoutParams().height = (int) context.getResources().getDimension(R.dimen.simageitems_image_nophoto);
            ((sdk.dive.tv.ui.modules.adapters.AppearsInAdapter.AppearsInItemHolder) holder).image.getLayoutParams().width = (int) context.getResources().getDimension(R.dimen.simageitems_image_nophoto);
            mPicasso.load(R.drawable.ico_nophoto_medium)
                    .into(((sdk.dive.tv.ui.modules.adapters.AppearsInAdapter.AppearsInItemHolder) holder).image);
        }

        ((sdk.dive.tv.ui.modules.adapters.AppearsInAdapter.AppearsInItemHolder) holder).title.setText(row.getTitle());
        ((sdk.dive.tv.ui.modules.adapters.AppearsInAdapter.AppearsInItemHolder) holder).title.setTypeface(latoSemibold);
        ((sdk.dive.tv.ui.modules.adapters.AppearsInAdapter.AppearsInItemHolder) holder).director.setText(row.getSubtitle());
        ((sdk.dive.tv.ui.modules.adapters.AppearsInAdapter.AppearsInItemHolder) holder).director.setTypeface(latoSemibold);
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    public static class AppearsInItemHolder extends RecyclerView.ViewHolder {

        private LinearLayout layout;
        private ImageView image;
        private TextView title;
        private TextView director;

        public AppearsInItemHolder(View v) {
            super(v);
            layout = (LinearLayout) v.findViewById(R.id.simple_rimage_container);
            image = (ImageView) v.findViewById(R.id.simple_rimage_image);
            title = (TextView) v.findViewById(R.id.simple_rimage_title);
            director = (TextView) v.findViewById(R.id.simple_rimage_subtitle);
        }
    }
}
