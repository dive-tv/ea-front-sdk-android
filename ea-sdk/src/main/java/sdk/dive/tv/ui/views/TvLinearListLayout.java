package sdk.dive.tv.ui.views;

/**
 * Created by Tagsonomy S.L. on 24/07/2017.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;

public class TvLinearListLayout extends LinearLayout {

    private Adapter list;
    private OnClickListener listener;
    private int separatorColor = -1;

    /**
     * Instantiates a new Linear list layout.
     *
     * @param context the context
     */
    public TvLinearListLayout(Context context) {
        super(context);
    }

    /**
     * Instantiates a new Linear list layout.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public TvLinearListLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    /**
     * Instantiates a new Linear list layout.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public TvLinearListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Sets list.
     *
     * @param list          the list
     * @param withSeparator the with separator
     */
    public void setList(Adapter list, boolean withSeparator, View customSeparator) {
        this.list = list;
        this.removeAllViews();

        //Popolute list
        if (this.list != null) {
            if (withSeparator) {
                if (customSeparator != null) {
                    for (int i = 0; i < this.list.getCount(); i++) {
                        View item = list.getView(i, null, null);
                        this.addView(item);
                        if (i < this.list.getCount() - 1) {
                            FrameLayout separator = new FrameLayout(getContext());
                            FrameLayout separator2 = new FrameLayout(getContext());
                            separator.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            separator2.setBackground(customSeparator.getBackground());
                            separator2.setLayoutParams(customSeparator.getLayoutParams());
                            separator.addView(separator2);
                            this.addView(separator);
                        }
                    }
                } else {
                    for (int i = 0; i < this.list.getCount(); i++) {
                        View item = list.getView(i, null, null);
                        this.addView(item);
                        if (i < this.list.getCount() - 1) {
                            FrameLayout separator = new FrameLayout(getContext());
                            FrameLayout.LayoutParams layoutparams;

                            if (getOrientation() == LinearLayout.VERTICAL) {
                                layoutparams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.stroke));
                            } else {
                                layoutparams = new FrameLayout.LayoutParams((int) getResources().getDimension(R.dimen.stroke), ViewGroup.LayoutParams.MATCH_PARENT);
                            }
                            separator.setBackgroundColor(Utils.getColor(getContext(), R.color.warm_grey));
                            separator.setLayoutParams(layoutparams);
                            this.addView(separator);
                        }
                    }
                }

            } else {
                for (int i = 0; i < this.list.getCount(); i++) {
                    View item = list.getView(i, null, null);
                    this.addView(item);
                }
            }
        }

    }

    public void notifyDataSetChanged(boolean withSeparator, View customSeparator, Context context) {
        this.removeAllViews();

        //Popolute list
        if (this.list != null) {
            if (withSeparator) {
                if (customSeparator != null) {
                    for (int i = 0; i < this.list.getCount(); i++) {
                        View item = list.getView(i, null, null);
                        this.addView(item);
                        if (i < this.list.getCount() - 1) {
                            this.addView(customSeparator);
                        }
                    }
                } else {
                    for (int i = 0; i < this.list.getCount(); i++) {
                        View item = list.getView(i, null, null);
                        this.addView(item);
                        if (i < this.list.getCount() - 1) {
                            FrameLayout separator = new FrameLayout(getContext());
                            FrameLayout.LayoutParams layoutparams;

                            if (getOrientation() == LinearLayout.VERTICAL) {
                                layoutparams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.stroke));
                            } else {
                                layoutparams = new FrameLayout.LayoutParams((int) getResources().getDimension(R.dimen.stroke), ViewGroup.LayoutParams.MATCH_PARENT);
                            }
                            separator.setLayoutParams(layoutparams);

                            separator.setBackgroundColor(Utils.getColor(context, separatorColor != -1 ? separatorColor : R.color.paleGrey));
                            this.addView(separator);
                        }
                    }
                }

            } else {
                for (int i = 0; i < this.list.getCount(); i++) {
                    View item = list.getView(i, null, null);
                    this.addView(item, i);
                }
            }
        }

    }

    public void setStyle(int separatorColor) {
        this.separatorColor = separatorColor;
    }

    /**
     * Sets listener to manage each row click.
     *
     * @param listener the m listener
     */
    public void setlistener(OnClickListener listener) {
        this.listener = listener;
    }
}