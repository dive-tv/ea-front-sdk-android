package sdk.dive.tv.ui.modules.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.data.ModuleMainStyle;
import sdk.dive.tv.ui.data.ModuleStyleData;
import sdk.dive.tv.ui.data.ModuleStyleProperty;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.data.TwoTextRowData;

/**
 * Created by Tagsonomy S.L. on 19/10/2016.
 */

public class TwoColsModuleAdapter extends BaseAdapter {

    ArrayList<TwoTextRowData> rows;
    LayoutInflater mInflater;
    private Typeface latoRegular;
    private Typeface latoSemiBold;
    private Context context;

    private TvCardDetailListener tvCardDetailListener;
    private boolean styleConfigRetrieved = false;
    private boolean darkStyle = false;

    private String moduleTitle;
    private HashMap<String, ModuleStyleData> genericStyles;

    public TwoColsModuleAdapter(Context context, ArrayList<TwoTextRowData> rows, TvCardDetailListener tvCardDetailListener) {
        this.rows = rows;
        this.mInflater = LayoutInflater.from(context);
        latoRegular = Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR);
        latoSemiBold = Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD);
        this.tvCardDetailListener = tvCardDetailListener;
        this.context = context;
    }


    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Object getItem(int i) {
        return rows.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        sdk.dive.tv.ui.modules.adapters.TwoColsModuleAdapter.TwoColsItemHolder holder;

       /* if (i == 0) {
            view = mInflater.inflate(R.layout.tv_module_two_cols, null);
            sdk.dive.tv.ui.modules.adapters.TwoColsModuleAdapter.TitleHolder priceHolder = new sdk.dive.tv.ui.modules.adapters.TwoColsModuleAdapter.TitleHolder();
            priceHolder.title = (TextView) view.findViewById(R.id.txtv_award_title);
            priceHolder.title.setTypeface(latoRegular);
            priceHolder.container = (LinearLayout) view.findViewById(R.id.lay_container);
            view.setTag(priceHolder);
            priceHolder.title.setText(moduleTitle);
            if (darkStyle) {
                priceHolder.container.setBackgroundColor(Utils.getColor(context, R.color.black));
            }
            return view;
        } else {*/

            if (view == null || !(view.getTag() instanceof sdk.dive.tv.ui.modules.adapters.TwoColsModuleAdapter.TwoColsItemHolder)) {
                view = mInflater.inflate(R.layout.tv_two_cols_row, null);
                holder = new sdk.dive.tv.ui.modules.adapters.TwoColsModuleAdapter.TwoColsItemHolder();
                holder.text1 = (TextView) view.findViewById(R.id.twocols_text1);
                holder.text2 = (TextView) view.findViewById(R.id.twocols_text2);
                holder.container = (LinearLayout) view.findViewById(R.id.llay_container);
                view.setTag(holder);
            } else {
                holder = (sdk.dive.tv.ui.modules.adapters.TwoColsModuleAdapter.TwoColsItemHolder) view.getTag();
            }
//        }

        configureStyle(holder);
        holder.text1.setTypeface(latoSemiBold);
        holder.text2.setTypeface(latoRegular);

        final TwoTextRowData data = rows.get(i);
        holder.text1.setText(data.getText());
        holder.text2.setText(data.getSubtitle());


        return view;
    }

    public void setModuleStyle(String moduleName) {


        if (styleConfigRetrieved == false) {
            if (tvCardDetailListener != null && tvCardDetailListener.getModuleStyles(moduleName) != null) {
                if (ModuleMainStyle.DARK.getName().equals(tvCardDetailListener.getModuleStyles(moduleName).get(ModuleStyleProperty.MAIN_STYLE.getName()).getValue())) {
                    darkStyle = true;
                }
            } else if (tvCardDetailListener != null && tvCardDetailListener.getModuleStyles("TwoColsModule") != null) {
                if (ModuleMainStyle.DARK.getName().equals(tvCardDetailListener.getModuleStyles("TwoColsModule").get(ModuleStyleProperty.MAIN_STYLE.getName()).getValue())) {
                    darkStyle = true;
                }
            }
        }
        if (tvCardDetailListener != null && tvCardDetailListener.getGenericStyles() != null){
            genericStyles = tvCardDetailListener.getGenericStyles();
        }
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    private void configureStyle(sdk.dive.tv.ui.modules.adapters.TwoColsModuleAdapter.TwoColsItemHolder holder) {

        if (genericStyles!=null){
            holder.container.setBackgroundColor(Color.parseColor(genericStyles.get("backgroundColor").getValue()));
        } else if (darkStyle) {

            holder.container.setBackgroundColor(Utils.getColor(context, R.color.black));

            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
                holder.container.setBackgroundColor(context.getResources().getColor(R.color.black, null));
            } else {
                holder.container.setBackgroundColor(context.getResources().getColor(R.color.black));
            }

            if (currentapiVersion < 23) {
                holder.text1.setTextAppearance(context, R.style.MinicardTitleLight);
            } else {
                holder.text1.setTextAppearance(R.style.MinicardTitleLight);
            }

        }

    }


    public static class TitleHolder {
        TextView title;
        LinearLayout container;
    }

    public static class TwoColsItemHolder {
        public TextView text1;
        public TextView text2;
        public LinearLayout container;
    }
}
