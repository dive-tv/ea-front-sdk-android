package sdk.dive.tv.ui.views;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Tagsonomy S.L. on 18/10/2016.
 */

public class ItemBottomOffsetDecoration extends RecyclerView.ItemDecoration {
    private int offset;

    public ItemBottomOffsetDecoration(int offset) {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = offset;
    }
}