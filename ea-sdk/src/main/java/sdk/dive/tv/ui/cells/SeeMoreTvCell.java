package sdk.dive.tv.ui.cells;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.views.CarouselTVCellLinear;

/**
 * Created by Tagsonomy S.L. on 04/07/2017.
 */

public class SeeMoreTvCell extends CarouselTvCell {

    public SeeMoreTvCell() {

    }

    @Override
    public LinearLayout getView(final Context context) {
        Typeface latoSemi = Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View baseView = inflater.inflate(R.layout.tv_cell_see_more, null);

        cellLayout = (CarouselTVCellLinear) baseView.findViewById(R.id.see_more_celltv_container);

        mImageBorder = (FrameLayout) baseView.findViewById(R.id.see_more_tv_cell_border);

        mTypeBox = (TextView) baseView.findViewById(R.id.see_more_tv_cell_type);
        mTypeBox.setTypeface(latoSemi);
        mTypeBox.setText(R.string.SEE_MORE);

        final ImageView iconImage = (ImageView) baseView.findViewById(R.id.see_more_tv_cell_noimage);

        cellLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityListener.onShowMoreRelations(getCard(), getStyleCell());
                if (cellLayout != null && mCarouselInterface != null)
                    mCarouselInterface.clickedView(cellLayout);
            }
        });

        cellLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    onCellFocusedSetColors(context);
                    onCellFocused();
                    iconImage.setColorFilter(Utils.getColor(context, R.color.white)); // White Tint
                } else {
                    onCellUnFocusedColors(context);
                    onCellUnfocused(context);
                    iconImage.setColorFilter(Utils.getColor(context, R.color.warm_grey)); // White Tint
                }
            }
        });

        return cellLayout;
    }

}
