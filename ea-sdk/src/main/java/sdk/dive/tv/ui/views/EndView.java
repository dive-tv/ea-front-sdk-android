package sdk.dive.tv.ui.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.interfaces.CarouselInterface;
import sdk.dive.tv.ui.managers.CarouselTransitionManager;

/**
 * Created by Tagsonomy S.L. on 05/07/2017.
 */

public class EndView extends RelativeLayout implements CarouselTransitionManager.TransitionLayout {

    private FrameLayout mClose;
    private TextView mText1;
    private TextView mText2;
    private Button mOkay;

    private CarouselInterface mCarouselInterface;


    public EndView(Context context) {
        super(context);

    }

    public EndView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public EndView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public EndView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        Typeface zonaProSemi = Utils.getFont(getContext(), Utils.TypeFaces.ZONAPRO_SEMIBOLD);
        Typeface latoSemi = Utils.getFont(getContext(), Utils.TypeFaces.LATO_SEMIBOLD);

        mClose = (FrameLayout) findViewById(R.id.carousel_end_button_close);
        mClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });

        mText1 = (TextView) findViewById(R.id.end_text1);
        mText1.setTypeface(zonaProSemi);
        mText2 = (TextView) findViewById(R.id.end_text2);
        mText2.setTypeface(latoSemi);
        mOkay = (Button) findViewById(R.id.end_button);
        mOkay.setTypeface(latoSemi);
        mOkay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mCarouselInterface.onOKMovieEnd();
            }
        });
        mOkay.requestFocus();
    }

    @Override
    public void onEnterAction(boolean fromEmptyScene) {
        mOkay.requestFocus();
        if (mCarouselInterface != null)
            mCarouselInterface.clickedView(mOkay);
    }

    @Override
    public void onExitAction() {

    }

    private void hide() {
        mCarouselInterface.closeOverlay();
    }

    public void setListener(CarouselInterface listener) {
        mCarouselInterface = listener;
    }

    public void getFocus() {
        mOkay.requestFocus();
        if (mCarouselInterface != null)
            mCarouselInterface.clickedView(mOkay);
    }
}
