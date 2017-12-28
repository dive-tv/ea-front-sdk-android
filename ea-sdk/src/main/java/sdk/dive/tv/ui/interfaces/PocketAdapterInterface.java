package sdk.dive.tv.ui.interfaces;

import sdk.dive.tv.ui.cells.CarouselTvCell;

/**
 * Created by Tagsonomy S.L. on 05/07/2017.
 */

public interface PocketAdapterInterface {

    void setCurrentInstance(CarouselTvCell instance);

    void setCardDeleted(String cardId);
}
