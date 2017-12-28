package sdk.dive.tv.ui.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
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

public class CommercialView extends RelativeLayout implements CarouselTransitionManager.TransitionLayout {

    private FrameLayout mClose;
    private TextView mText1;
    private TextView mText2;
    private TextView mText3;

    private CarouselInterface mCarouselInterface;


    public CommercialView(Context context) {
        super(context);
    }

    public CommercialView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommercialView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CommercialView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        Typeface zonaProSemi = Utils.getFont(getContext(), Utils.TypeFaces.ZONAPRO_SEMIBOLD);
        Typeface latoSemi = Utils.getFont(getContext(), Utils.TypeFaces.LATO_SEMIBOLD);
        Typeface zonaProExtraBold = Utils.getFont(getContext(), Utils.TypeFaces.ZONAPRO_EXTRABOLD);

        mClose = (FrameLayout) findViewById(R.id.carousel_commercial_button_close);
        mClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });

        mText1 = (TextView) findViewById(R.id.commercial_text1);
        mText1.setTypeface(zonaProSemi);
        mText2 = (TextView) findViewById(R.id.commercial_text2);
        mText2.setTypeface(latoSemi);
        mText3 = (TextView) findViewById(R.id.commercial_text3);
        mText3.setTypeface(latoSemi);

        RelativeLayout mCommercialImageContainer = (RelativeLayout) findViewById(R.id.commercial_image_container);
        mCommercialImageContainer.setBackground(new TriangleCanvas(Utils.getColor(getContext(), R.color.off_yellow)));
        TextView commercialImageText = (TextView) findViewById(R.id.commercial_image_text);
        commercialImageText.setTypeface(zonaProExtraBold);
        mClose.requestFocus();
    }

    @Override
    public void onEnterAction(boolean fromEmptyScene) {
        mClose.requestFocus();
        if (mCarouselInterface != null) {
            mCarouselInterface.clickedView(mClose);
        }

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
        mClose.requestFocus();
        if (mCarouselInterface != null) {
            mCarouselInterface.clickedView(mClose);
        }

    }
}
