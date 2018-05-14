package sdk.dive.tv.ui.interfaces;

import android.app.FragmentManager;

import com.touchvie.sdk.model.Card;

/**
 * Created by Tagsonomy S.L. on 30/05/2017.
 */

public interface ComponentsInterface {

    void addCarousel(String apiKey, String movieId, String channelId, boolean isMovie, int movieTime, String previousScreen, String movieName, String style);

    void removeCarousel();

    void addCardDetail(String cardId, String versionId, Card.TypeEnum typeOfCard);

    void removeCardDetail();

    void addMenu();

    void showMenu();

    void hideMenu();

    void removeMenu();

    void addPocket();

    void removePocket();

    void addTV();

    void addTVGrid();

    void removeTVGrid();

    void addSettings();

    void removeSettings();

    void addExitFragment();

    void removeExitFragment();

    void addStreamingPlayer(String movieId, String channelId);
}
