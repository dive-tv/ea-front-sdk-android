package sdk.dive.tv.ui.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import sdk.dive.tv.ui.Utils;

/**
 * Created by Tagsonomy S.L. on 03/07/2017.
 */

public class CategoriesAdapter extends ArrayAdapter<String> {

    int resource;
    Typeface latoMedium;

    public CategoriesAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        this.resource=resource;
        latoMedium= Utils.getFont(context, Utils.TypeFaces.LATO_MEDIUM);
    }

    public CategoriesAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId) {
        super(context, resource, textViewResourceId);
        this.resource=resource;
        latoMedium= Utils.getFont(context, Utils.TypeFaces.LATO_MEDIUM);
    }

    public CategoriesAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull String[] objects) {
        super(context, resource, objects);
        this.resource=resource;
        latoMedium= Utils.getFont(context, Utils.TypeFaces.LATO_MEDIUM);
    }

    public CategoriesAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull String[] objects) {
        super(context, resource, textViewResourceId, objects);
        this.resource=resource;
        latoMedium= Utils.getFont(context, Utils.TypeFaces.LATO_MEDIUM);
    }

    public CategoriesAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.resource=resource;
        latoMedium= Utils.getFont(context, Utils.TypeFaces.LATO_MEDIUM);
    }

    public CategoriesAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<String> objects) {
        super(context, resource, textViewResourceId, objects);
        this.resource=resource;
        latoMedium= Utils.getFont(context, Utils.TypeFaces.LATO_MEDIUM);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //You can use the new tf here.
        RelativeLayout view = (RelativeLayout) super.getView(position, convertView, parent);
        TextView text= (TextView ) view.findViewById(android.R.id.text1);
        text.setTypeface(latoMedium);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LinearLayout view = (LinearLayout) super.getDropDownView(position, convertView, parent);
        TextView text= (TextView ) view.findViewById(android.R.id.text1);
        text.setTypeface(latoMedium);
        return view;
    }

}
