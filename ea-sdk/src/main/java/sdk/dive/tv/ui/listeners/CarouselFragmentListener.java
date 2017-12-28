package sdk.dive.tv.ui.listeners;

import android.os.Handler;

/**
 * Created by Tagsonomy S.L. on 26/10/2016.
 */

public interface CarouselFragmentListener {
//    void setCarouselCardListener(Handler listener);

    void onCallReadyMovie();

    void onCallOffMovie();

    void onCallEndMovie();

    void onCallTDTPlayingMovie();

    void onCallPlayingMovie();

    void setPlayingMovie(boolean playing);

    void setFeedbackIsOpen(boolean feedbackIsOpen);

    void onCallFeedBack(int feedbackOpts, String cardId, String movieId, double timestamp);

    void onCallExitCarousel();

    void onDestroyAndCloseCarousel();

    void onSetMovieLength(float movieLength);

    void updateTimeLineUser(float time, boolean isPaused);

    void updateTimeLineCurrent(float time);

    void updateCarouselMsgBar(int newCards, boolean showBar, boolean isCurrentScene);

    void onShowCarouselError();

    void setCarouselBtnWithCurrentState();

    void showBottomButton();
}
