package sdk.dive.tv.ui.modules.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import sdk.dive.tv.ui.modules.data.ImageRowData;

/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public class NoPaddingListAdapter extends BaseAdapter {

    private ArrayList<ImageRowData> rows;

    public NoPaddingListAdapter(ArrayList<ImageRowData> rows){
        this.rows=rows;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}