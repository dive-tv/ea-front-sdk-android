package sdk.dive.tv.ui.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class CustomLinearLayoutManagerCarousel extends android.support.v7.widget.LinearLayoutManager {

    public CustomLinearLayoutManagerCarousel(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public View onInterceptFocusSearch(View focused, int direction) {

        if (direction == View.FOCUS_RIGHT) {
            View view = getChildAt(getItemCount() - 1);
            if (lastViewIsFocusedview(view, focused)) {
                return focused;
            }
        } else if (direction == View.FOCUS_LEFT) {
            View view = getChildAt(0);
            if (lastViewIsFocusedview(view, focused)) {
                return focused;
            }
        }
        return super.onInterceptFocusSearch(focused, direction);
    }

    private boolean lastViewIsFocusedview(View view, View focused) {
        View viewFocused = focused;
        while (viewFocused != null) {
            try {
                viewFocused = (View) viewFocused.getParent();
            } catch (Exception e) {
                return false;
            }
            if (viewFocused != null && viewFocused == view)
                return true;
        }

        return false;
    }

    @Override
    public View onFocusSearchFailed(View focused, int focusDirection, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (focusDirection == View.FOCUS_RIGHT) {
            scrollToPosition(findLastVisibleItemPosition() + 1);
        } else {
            scrollToPosition(findFirstVisibleItemPosition() - 1);
        }

        return super.onFocusSearchFailed(focused, focusDirection, recycler, state);
    }
}
