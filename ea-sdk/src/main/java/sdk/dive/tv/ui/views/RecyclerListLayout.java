package sdk.dive.tv.ui.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import sdk.dive.tv.R;


/**
 * Created by Tagsonomy S.L. on 19/09/2016.
 */
public class RecyclerListLayout extends LinearLayout {

    /**
     * Instantiates a new Linear list layout.
     *
     * @param context the context
     */
    public RecyclerListLayout(Context context) {
        super(context);
    }

    /**
     * Instantiates a new Linear list layout.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public RecyclerListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Instantiates a new Linear list layout.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public RecyclerListLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    /**
     * Sets list.
     * @param adapter       the recycleradapter
     * @param withSeparator choose whether print a separator or not
     */
    public void setList(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, boolean withSeparator) {
        this.removeAllViews();

        //Popolute list
        if (adapter != null) {
            if (withSeparator) {
                for (int position = 0; position < adapter.getItemCount(); position++) {
                    RecyclerView.ViewHolder holder = adapter.onCreateViewHolder(this, adapter.getItemViewType(position));
                    adapter.onBindViewHolder(holder, position);
                    this.addView(holder.itemView);

                    if (position < adapter.getItemCount() - 1) {
                        FrameLayout separator = new FrameLayout(getContext());
                        FrameLayout.LayoutParams layoutparams;
                        if (getOrientation() == LinearLayout.VERTICAL) {
                            layoutparams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.stroke));
                        } else {
                            layoutparams = new FrameLayout.LayoutParams((int) getResources().getDimension(R.dimen.stroke), ViewGroup.LayoutParams.MATCH_PARENT);
                        }
                        separator.setLayoutParams(layoutparams);
                        this.addView(separator);
                    }
                }
            } else {
                for (int position = 0; position < adapter.getItemCount(); position++) {
                    RecyclerView.ViewHolder holder = adapter.onCreateViewHolder(this, adapter.getItemViewType(position));
                    adapter.onBindViewHolder(holder, position);
                    this.addView(holder.itemView);
                }
            }
        }

    }
}