package sdk.dive.tv.ui.modules.adapters;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Tagsonomy S.L. on 10/10/2016.
 */

public abstract class TVScrollAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Object> allData = new ArrayList<>();
    Object[] rowItems = new Object[2];
    private int currentPosition = 0;

    @Override
    public int getItemCount() {
        return allData.size() > 2 ? 2 : allData.size();
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
            if (this.allData.get(currentPosition) != null)
                rowItems[0] = this.allData.get(currentPosition);

            if (this.allData.size() > (currentPosition + 1)) {
                if (this.allData.get(currentPosition + 1) != null)
                    rowItems[1] = this.allData.get(currentPosition + 1);

                notifyItemRangeChanged(0, 2);
            } else {
//                notifyItemChanged(0);
                notifyItemRangeChanged(0, 2);

            }
        }
    }
}