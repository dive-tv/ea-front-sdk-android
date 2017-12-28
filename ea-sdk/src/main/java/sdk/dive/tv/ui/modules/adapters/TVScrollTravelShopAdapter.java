package sdk.dive.tv.ui.modules.adapters;

import java.util.ArrayList;

/**
 * Created by Tagsonomy S.L. on 10/10/2016.
 */

public abstract class TVScrollTravelShopAdapter extends TVScrollAdapter {

    ArrayList<Object> allData = new ArrayList<>();
    Object[] rowItems = new Object[1];
    private int currentPosition = 0;

    @Override
    public int getItemCount() {
        return allData.size() > 1 ? 1 : allData.size();
    }

    public int stepNext() {
        currentPosition++;
        setData();
        return currentPosition;
    }

    public int stepBack() {
        currentPosition--;
        setData();
        return currentPosition;
    }

    void setData() {
        if (this.allData.size() > 0) {
            if (this.allData.get(currentPosition) != null) {
                rowItems[0] = this.allData.get(currentPosition);
                notifyItemChanged(0);
            }
        }
    }
}