package sdk.dive.tv.ui.listeners;

import com.touchvie.sdk.model.Card;

import java.util.List;

/**
 * Created by emilio.alvarez on 27/11/2017.
 */

    public interface SocketApiListener {

        /**
         * On movie-start event received.
         *
         * @param movie the card of movie started
         */
        void onMovieStartEventReceived(Card movie);

        /**
         * On movie-end event received.
         *
         */
        void onMovieEndEventReceived();

        /**
         * On scene-start event received.
         *
         * @param cards the list of cards received on scene-start event
         */
        void onSceneStartEventReceived(List<Card> cards);

        /**
         * On scene-update event received.
         *
         * @param cards the list of cards received on scene-update event
         */
        void onSceneUpdateEventReceived(List<Card> cards);

        /**
         * On scene-end event received.
         *
         */
        void onSceneEndEventReceived();

        /**
         * On paused-start event received.
         *
         */
        void onPausedStartEventReceived();

        /**
         * On paused-start event received.
         *
         */
        void onPausedEndEventReceived();

        /**
         * On error received.
         */
//        void onErrorReceived(StreamingError error);


    }
