package sdk.dive.tv.ui.views;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Tagsonomy S.L. on 18/10/2016.
 */

public class ItemHorizontalOffsetDecoration extends RecyclerView.ItemDecoration {
    private int offset;

    public ItemHorizontalOffsetDecoration(int offset) {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = offset;
        outRect.right = offset;
    }
}