package sdk.dive.tv.ui.managers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import sdk.dive.tv.ui.views.CommercialView;
import sdk.dive.tv.ui.views.EndView;
import sdk.dive.tv.ui.views.OffView;
import sdk.dive.tv.ui.views.ReadyView;

/**
 * Created by Tagsonomy S.L. on 05/07/2017.
 */

public class CarouselPromptManager {

    public @interface HideOverlayFlag {
    }

    // FLAG_HIDE_OVERLAYs must be bitwise exclusive.
    public static final int FLAG_HIDE_OVERLAYS_DEFAULT = 0b000000000;
    public static final int FLAG_HIDE_OVERLAYS_WITHOUT_ANIMATION = 0b000000010;
    public static final int FLAG_HIDE_OVERLAYS_KEEP_SCENE = 0b000000100;

    private CarouselTransitionManager mTransitionManager;


    public CarouselPromptManager(Context ctx, ViewGroup parentContainer, RecyclerView carouselList, ViewGroup sceneContainer, CommercialView commercialView, ReadyView readyView, EndView endView, OffView offView) {

        mTransitionManager = new CarouselTransitionManager(ctx, parentContainer, carouselList, sceneContainer, commercialView, readyView, endView, offView);

    }

    public void showCommercial() {
        mTransitionManager.goToCommercialScene();
    }

    public void showReady() {
        mTransitionManager.goToReadyScene();
    }

    public void showEnd() {
        mTransitionManager.goToEndScene();
    }

    public void showOff() {
        mTransitionManager.goToOffScene();
    }

    public void hideOverlays(@HideOverlayFlag int flags) {

        boolean withAnimation = (flags & FLAG_HIDE_OVERLAYS_WITHOUT_ANIMATION) == 0;

        if ((flags & FLAG_HIDE_OVERLAYS_KEEP_SCENE) != 0) {
            // Keeps the current scene.
        } else {
            mTransitionManager.goToEmptyScene(withAnimation);
        }

    }
}
