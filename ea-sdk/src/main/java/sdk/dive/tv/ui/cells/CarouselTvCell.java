package sdk.dive.tv.ui.cells;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.CardContainer;
import com.touchvie.sdk.model.Duple;
import com.touchvie.sdk.model.RelationModule;
import com.touchvie.sdk.model.Single;

import java.util.ArrayList;
import java.util.HashMap;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.fragments.Carousel;
import sdk.dive.tv.ui.interfaces.CarouselInterface;
import sdk.dive.tv.ui.interfaces.PocketAdapterInterface;
import sdk.dive.tv.ui.views.CarouselTVCellButton;
import sdk.dive.tv.ui.views.CarouselTVCellLinear;

/**
 * Created by Tagsonomy S.L. on 04/07/2017.
 */

public class CarouselTvCell {

    private CarouselTvCell instance;
    Card card;

    private static final int DELAY = 500;
    private HashMap<String, CardContainer> contentTypeContainer = null;
    private Handler handler;
    OpenWhenFocused openWhenFocused;
    boolean isSeeMoreFocused = false;
    boolean isLikeBtnFocused = false;
    Carousel.OnCarouselInteractionListener mActivityListener;
    protected CarouselInterface mCarouselInterface;
    PocketAdapterInterface mPocketInterface;
    private HashMap<String, ArrayList<RelationModule>> contentTypeRelations = null;

    //Common views
    CarouselTVCellLinear cellLayout;
    FrameLayout mImageBorder;
    TextView mTypeBox;
    RelativeLayout mInfoContainer;
    FrameLayout mInfoBorder;
    CarouselTVCellButton mSeeMoreContainer;
    CarouselTVCellLinear mLikeContainer;
    TextView mLikeTxt;
    ImageView mLikeIcon;

    private boolean hasRelations = false;

    public CarouselTvCell() {
        instance = this;
        this.handler = new Handler();
        }

    CardContainer getContainer(String contentType, CardContainer[] info) {
        if (info == null || info.length == 0) {
            return null;
        }
        if (this.contentTypeContainer == null) {
            this.contentTypeContainer = new HashMap<>();
            for (CardContainer container : info) {
                this.contentTypeContainer.put(container.getType().getValue(), container);
            }
        }

        return this.contentTypeContainer.get(contentType);
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public CarouselTVCellLinear getCellLayout() {
        return cellLayout;
    }

    void onCellFocusedSetColors(Context context) {
        if (this.mImageBorder != null) {
            this.mImageBorder.setBackgroundResource(R.drawable.focus_border_yellow);
        }

        if (this.mTypeBox != null) {
            this.mTypeBox.setBackgroundResource(R.drawable.focus_border_yellow);
            this.mTypeBox.setTextColor(Utils.getColor(context, R.color.pale_grey));
        }

        if (this.mInfoBorder != null) {
            this.mInfoBorder.setBackgroundResource(R.drawable.focus_border_yellow);
        }

    }

    void onCellFocused() {
        if (this.mInfoContainer != null && openWhenFocused != null) {
            this.openWhenFocused.setFocus();
            this.handler.postDelayed(openWhenFocused, DELAY);
        }
    }


    void onCellUnfocused(Context context) {
        if (this.mImageBorder != null) {
            this.mImageBorder.setBackgroundResource(R.drawable.focus_border_warm_grey);
        }

        if (this.mTypeBox != null) {
            this.mTypeBox.setBackgroundResource(R.drawable.focus_border_warm_grey);
            this.mTypeBox.setTextColor(Utils.getColor(context, R.color.warm_grey));
        }

        if (this.mInfoBorder != null) {
            this.mInfoBorder.setBackgroundResource(R.drawable.focus_border_warm_grey);
        }

        if (this.mInfoContainer != null && this.openWhenFocused != null) {
            this.openWhenFocused.cancelFocus();
            this.handler.removeCallbacksAndMessages(openWhenFocused);
        }
    }


    class OpenWhenFocused implements Runnable {

        private final RelativeLayout infoContainer;
        private final Context context;
        private boolean isCancelled = false;

        OpenWhenFocused(Context context, RelativeLayout infoContainer) {
            this.isCancelled = false;
            this.context = context;
            this.infoContainer = infoContainer;
        }

        @Override
        public void run() {
            if (!isCancelled) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mSeeMoreContainer != null) {
                            isSeeMoreFocused = true;
                            mSeeMoreContainer.requestFocus();
                        } else if (mLikeContainer != null) {
                            isLikeBtnFocused = true;
                            mLikeContainer.requestFocus();
                        }

                        if (mPocketInterface != null) {
                            mPocketInterface.setCurrentInstance(instance);
                        }

                        infoContainer.setVisibility(View.VISIBLE);
                        infoContainer.animate()
                                .setDuration(500)
                                .translationX(0);
                    }
                });
            }
        }

        void cancelFocus() {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isCancelled = true;
                    infoContainer
                            .animate()
                            .setDuration(500)
                            .translationX(-1000);
                    infoContainer.setVisibility(View.GONE);
                }
            });

        }

        void setFocus() {
            isCancelled = false;
        }
    }

    public boolean hasRelations() {
        return hasRelations;
    }

    public void setHasRelations(boolean hasRelations) {
        this.hasRelations = hasRelations;
    }

    public void setActivityListener(Carousel.OnCarouselInteractionListener mActivityListener) {
        this.mActivityListener = mActivityListener;
    }

    public void setCarouselInterface(CarouselInterface mCarouselInterface) {
        this.mCarouselInterface = mCarouselInterface;
    }

    public void setPocketInterface(PocketAdapterInterface mPocketInterface) {
        this.mPocketInterface = mPocketInterface;
    }

    public void setNextFocusUp(int id) {
        if (mSeeMoreContainer != null) {
            mSeeMoreContainer.setNextFocusUpId(id);
        }
    }

    public LinearLayout getView(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View baseView = inflater.inflate(R.layout.carousel_item_linear_container, null);
        LinearLayout layout = (LinearLayout) baseView.findViewById(R.id.carousel_item_linear_container);
        return layout;
    }
}
