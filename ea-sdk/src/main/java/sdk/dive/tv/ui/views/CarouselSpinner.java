package sdk.dive.tv.ui.views;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.KeyEvent;

/**
 * Created by Tagsonomy S.L. on 17/08/2017.
 */

public class CarouselSpinner extends android.support.v7.widget.AppCompatSpinner {

    private boolean allowRightNav = false;

    public CarouselSpinner(Context context) {
        super(context);
    }

    public CarouselSpinner(Context context, int mode) {
        super(context, mode);
    }

    public CarouselSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CarouselSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CarouselSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public CarouselSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
    }

    public void setAllowRightNav(boolean allowRightNav) {
        this.allowRightNav = allowRightNav;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (!allowRightNav)
                    return true;
                else
                    return false;
        }
        return super.dispatchKeyEvent(event);
    }
}