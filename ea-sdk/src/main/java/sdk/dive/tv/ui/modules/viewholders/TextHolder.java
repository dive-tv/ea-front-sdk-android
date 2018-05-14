package sdk.dive.tv.ui.modules.viewholders;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.touchvie.sdk.model.SourceData;
import com.touchvie.sdk.model.TextData;

import sdk.dive.tv.R;

import static android.view.View.FOCUS_DOWN;
import static android.view.View.FOCUS_UP;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Tagsonomy S.L. on 05/10/2016.
 */

public abstract class TextHolder extends TvModuleHolder {

    protected RelativeLayout container;
    protected TextView title;
    protected TextView text;
    protected TextView link;
    LinearLayout source_cont;
    protected ScrollView scroll;
    FrameLayout btnDown, btnUp;


    TextHolder(View view) {
        super(view);

        container = (RelativeLayout) view.findViewById(R.id.tv_module_text_container);
        title = (TextView) view.findViewById(R.id.tv_module_text_title);
        text = (TextView) view.findViewById(R.id.tv_module_text_desc);
        link = (TextView) view.findViewById(R.id.tv_module_text_source_url);
        source_cont = (LinearLayout) view.findViewById(R.id.tv_module_text_source_cont);
        scroll = (ScrollView) view.findViewById(R.id.tv_module_scroll_text_desc);
        scroll.setFocusable(false);
        scroll.setFocusableInTouchMode(false);

        btnDown = (FrameLayout) view.findViewById(R.id.tv_module_text_btn_next);
        btnUp = (FrameLayout) view.findViewById(R.id.tv_module_text_btn_back);

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scroll.arrowScroll(FOCUS_UP);
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scroll.arrowScroll(FOCUS_DOWN);
                if (btnUp.getVisibility() != VISIBLE) {
                    btnUp.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    void manageSource(final TextData containerText) {
        SourceData source = containerText.getSource();
        if (source != null && ((source.getName() != null && source.getName().length() > 0) || (source.getUrl() != null && source.getUrl().length() > 0))) {
            if (source.getName() != null) {
                link.setText(source.getName());
            } else {
                link.setText(source.getUrl());
            }
            source_cont.setVisibility(View.VISIBLE);
        } else {
            source_cont.setVisibility(GONE);
        }
    }

    void checkScrollAndButtons() {

        text.post(new Runnable() {
            @Override
            public void run() {
                int childHeight = text.getHeight();
                boolean isScrollable = scroll.getHeight() < childHeight + scroll.getPaddingTop() + scroll.getPaddingBottom();

                if (!isScrollable) {
                    btnUp.setVisibility(View.GONE);
                    btnDown.setVisibility(View.GONE);
                    container.setFocusable(true);
                    container.setFocusableInTouchMode(true);
                } else {
                    btnDown.setVisibility(VISIBLE);
                    container.setFocusable(false);
                    container.setFocusableInTouchMode(false);
                }
            }
        });

    }
}
