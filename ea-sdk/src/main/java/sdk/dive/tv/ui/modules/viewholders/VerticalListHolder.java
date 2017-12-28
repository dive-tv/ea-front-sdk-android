package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.views.TvLinearListLayout;

import static android.view.View.FOCUS_DOWN;
import static android.view.View.FOCUS_UP;
import static android.view.View.VISIBLE;

/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public abstract class VerticalListHolder extends TvModuleHolder {

    RelativeLayout mContainer;
    TvLinearListLayout mList;
    protected TextView mTitle;
    protected ScrollView scroll;
    private FrameLayout btnDown, btnUp;

    /**
     * Default constructor
     *
     * @param itemView
     */

    VerticalListHolder(View itemView) {

        super(itemView);

        this.mContainer = (RelativeLayout) itemView.findViewById(R.id.lay_container);
        this.mTitle = (TextView) itemView.findViewById(R.id.txtv_tv_title);
        this.mList = (TvLinearListLayout) itemView.findViewById(R.id.lllay_list_list);

        this.btnDown = (FrameLayout) itemView.findViewById(R.id.tv_module_list_btn_down);
        this.btnUp = (FrameLayout) itemView.findViewById(R.id.tv_module_list_btn_up);

        this.scroll = (ScrollView) itemView.findViewById(R.id.tv_module_scroll_list_desc);
        this.scroll.setFocusable(false);
        this.scroll.setFocusableInTouchMode(false);

        this.btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scroll.arrowScroll(FOCUS_UP);
            }
        });

        this.btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scroll.arrowScroll(FOCUS_DOWN);
                if (btnUp.getVisibility() != VISIBLE) {
                    btnUp.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    protected void setAdapter(BaseAdapter adapter, boolean withSeparator, View customSeparator) {
        mList.setList(adapter, withSeparator, customSeparator);

        checkScrollAndButtons();
    }

    protected void notifyDataSetChanged(boolean withSeparator, View customSeparator, Context context) {
        mList.notifyDataSetChanged(withSeparator, customSeparator, context);
        checkScrollAndButtons();
    }

    private void checkScrollAndButtons() {
        mList.post(new Runnable() {
            @Override
            public void run() {
                int childHeight = mList.getHeight();
                boolean isScrollable = scroll.getHeight() < childHeight + scroll.getPaddingTop() + scroll.getPaddingBottom();

                if (!isScrollable) {
                    btnUp.setVisibility(View.GONE);
                    btnDown.setVisibility(View.GONE);
                    mContainer.setFocusable(true);
                    mContainer.setFocusableInTouchMode(true);
                } else {
                    mContainer.setFocusable(false);
                    mContainer.setFocusableInTouchMode(false);
                    btnDown.setVisibility(VISIBLE);
                }
            }
        });

    }
}
