package sdk.dive.tv.ui.interfaces;

import android.view.View;

import java.util.HashMap;

import sdk.dive.tv.ui.managers.CarouselPromptManager;

/**
 * Created by Tagsonomy S.L. on 05/07/2017.
 */

public interface CarouselInterface {

    CarouselPromptManager getOverlayManager();

    void onOKMovieEnd();

    void onOKMovieOff();

    void setCardsAreLiked(HashMap<String, Boolean> likedCards);

    void closeOverlay();

    void clickedView(View view);

}
