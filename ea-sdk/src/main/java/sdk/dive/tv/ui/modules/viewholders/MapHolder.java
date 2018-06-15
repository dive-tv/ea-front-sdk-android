package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.CardContainer;
import com.touchvie.sdk.model.Map;

import java.util.HashMap;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.data.ModuleStyleData;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;

import static com.touchvie.sdk.model.Map.ContentTypeEnum.LOCATION;

/**
 * Created by Tagsonomy S.L. on 18/10/2016.
 */

public class MapHolder extends TvModuleHolder {
    Picasso mPicasso;
    private LinearLayout mContainer;
    private TextView mTitle;
    private ImageView mImage;
    private HashMap<String, ModuleStyleData> genericStyles;


    public MapHolder(View itemView, String simpleName) {
        super(itemView);
        this.moduleName = simpleName;
        mContainer = (LinearLayout) itemView.findViewById(R.id.lay_container);
        mTitle = (TextView) itemView.findViewById(R.id.txtv_tv_title);
        mImage = (ImageView) itemView.findViewById(R.id.tv_module_maps_img);
    }

    @Override
    public void configure(Card cardData, Picasso picasso, final Context context, TvCardDetailListener tvCardDetailListener, final SectionListener sectionListener) {

        this.mPicasso = Picasso.with(context);

        mTitle.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR));
        mTitle.setText(context.getResources().getString(R.string.tv_module_location_title));

        final CardContainer container = Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), LOCATION.getValue());
        if (container == null) {
            return;
        }
        if (((Map) container).getData() != null && ((Map) container).getData().size() == 1) {

            final String location = String.valueOf(((Map) container).getData().get(0).getLatitude()) + "," + String.valueOf(((Map) container).getData().get(0).getLongitude()) + "&zoom=" + String.valueOf(((Map) container).getData().get(0).getZoom());

            mPicasso
                    .load("https://maps.googleapis.com/maps/api/staticmap?center=" + location + "&size=" + (int) context.getResources().getDimension(R.dimen.cardetail_module_map_width) + "x" + (int) context.getResources().getDimension(R.dimen.cardetail_module_map_height))
                    .into(mImage);

        }
        if (tvCardDetailListener != null && tvCardDetailListener.getGenericStyles() != null){
            genericStyles = tvCardDetailListener.getGenericStyles();
            mContainer.setBackground(Utils.makeSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor("#00000000"), genericStyles.get("backgroundModuleColor").getValue()));

        }
    }
}
