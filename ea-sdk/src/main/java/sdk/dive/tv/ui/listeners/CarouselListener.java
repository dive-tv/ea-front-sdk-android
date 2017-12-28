package sdk.dive.tv.ui.listeners;

import android.os.Handler;

import com.touchvie.sdk.model.Card;

import java.util.ArrayList;

import sdk.dive.tv.ui.cells.CarouselTvCell;

/**
 * Created by Tagsonomy S.L. on 14/09/2016.
 */
public interface CarouselListener {

    /**
     * Interface to be reported when the data of a card has been received.
     *
     * @param carouselCells array of carousel cell datas to use in the list
     */
    void onRowsToDraw(ArrayList<CarouselTvCell> carouselCells);

    void onCommercialCellsReceived();

    void setHandler(Handler handler);

//    void onSendCarouselCardListenerReceived();
}
