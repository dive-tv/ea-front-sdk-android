package sdk.dive.tv.ui.views;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by rodrigo.moral on 05/09/2017.
 */

public class CustomGridLayoutManagerPocket extends GridLayoutManager {
    public CustomGridLayoutManagerPocket(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public View onFocusSearchFailed(View focused, int focusDirection, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (focusDirection == View.FOCUS_DOWN) {
            scrollToPosition(findLastVisibleItemPosition() + 7);
        } else if (focusDirection == View.FOCUS_UP) {
            scrollToPosition(findFirstVisibleItemPosition() - 7);
        }
        return super.onFocusSearchFailed(focused, focusDirection, recycler, state);
    }
}
