package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.Duple;
import com.touchvie.sdk.model.DupleData;
import com.touchvie.sdk.model.RelationModule;

import java.util.ArrayList;
import java.util.HashMap;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.data.ModuleStyleData;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.adapters.HorizontalListAdapter;
import sdk.dive.tv.ui.modules.data.HorizontalItemData;

import static com.touchvie.sdk.model.Duple.ContentTypeEnum.MOVIE_LOCATIONS;
import static com.touchvie.sdk.model.DupleData.RelTypeEnum.FEATURES_LOCATION;
import static com.touchvie.sdk.model.DupleData.RelTypeEnum.FILMED_IN;
import static com.touchvie.sdk.model.DupleData.RelTypeEnum.REPRESENTS;

/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public class PlacesShownHolder extends HorizontalListHolder {
    private HashMap<String, ModuleStyleData> genericStyles;

    public PlacesShownHolder(View itemView, String simpleName) {
        super(itemView);
        this.moduleName = simpleName;
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, final TvCardDetailListener tvCardDetailListener, final SectionListener sectionListener) {

        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);

        mTitle.setText(context.getResources().getString(R.string.CARD_MODULE_LOCATIONS_ON));

        RelationModule relation = Utils.getRelation(cardData, MOVIE_LOCATIONS.getValue());
        if (relation == null) {
            return;
        }

        final ArrayList<HorizontalItemData> rows = new ArrayList<>();

        for (DupleData relData : ((Duple) relation).getData()) {
            HorizontalItemData row = new HorizontalItemData();
            if (FEATURES_LOCATION.equals(relData.getRelType())) {
                row.setImage(relData.getFrom().getImage());
                row.setText(relData.getFrom().getTitle());
                row.setCardId(relData.getFrom().getCardId());
                row.setCardType(relData.getFrom().getType().getValue());
                row.setHasContent(relData.getFrom().getHasContent());
                rows.add(row);
            } else if (FILMED_IN.equals(relData.getRelType())) {
                /*row.setImage(relData.getTo().getImage());
                row.setTitle(relData.getTo().getTitle());
                row.setSubtitle(relData.getTo().getSubtitle());
                row.setCardId(relData.getTo().getCardId());
                row.setCardType(relData.getTo().getType());
                rows.add(row);*/
            } else if (REPRESENTS.equals(relData.getRelType())) {
                row.setImage(relData.getFrom().getImage());
                row.setText(relData.getFrom().getTitle());
                row.setCardId(relData.getFrom().getCardId());
                row.setHasContent(relData.getFrom().getHasContent());
                row.setCardType(relData.getTo().getType().getValue());
                rows.add(row);
            }
        }

        HorizontalListAdapter adapter = new HorizontalListAdapter(context, rows, tvCardDetailListener);
        super.setAdapter(adapter, rows.size());
        if (tvCardDetailListener != null && tvCardDetailListener.getGenericStyles() != null){
            genericStyles = tvCardDetailListener.getGenericStyles();
            mContainer.setBackground(Utils.makeSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor("#00000000"), genericStyles.get("backgroundModuleColor").getValue()));
            btnBack.setBackground(Utils.makeButtonSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("selectedColor").getValue()));
            btnNext.setBackground(Utils.makeButtonSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("selectedColor").getValue()));
        }

    }
}
