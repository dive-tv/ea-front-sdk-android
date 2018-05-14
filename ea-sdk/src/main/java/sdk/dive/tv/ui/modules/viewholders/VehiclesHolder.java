package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.RelationModule;
import com.touchvie.sdk.model.Single;

import java.util.ArrayList;
import java.util.HashMap;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.data.ModuleStyleData;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.adapters.HorizontalListAdapter;
import sdk.dive.tv.ui.modules.data.HorizontalItemData;

import static com.touchvie.sdk.model.Single.ContentTypeEnum.MOVIE_VEHICLES;

/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public class VehiclesHolder extends HorizontalListHolder {
    private HashMap<String, ModuleStyleData> genericStyles;

    public VehiclesHolder(View itemView, String simpleName) {
        super(itemView);
        this.moduleName = simpleName;
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, final TvCardDetailListener tvCardDetailListener, final SectionListener sectionListener) {

        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);

        mTitle.setText(context.getResources().getString(R.string.CARD_MODULE_VEHICLES_ON));

        final ArrayList<HorizontalItemData> rows = new ArrayList<>();

        RelationModule relation = Utils.getRelation(cardData, MOVIE_VEHICLES.getValue());
        if (relation == null) {
            return;
        }

        for (Card relData : ((Single) relation).getData()) {
            HorizontalItemData row = new HorizontalItemData();
            row.setImage(relData.getImage());
            row.setText(relData.getTitle());
            row.setCardId(relData.getCardId());
            row.setCardType(relData.getType().getValue());
            row.setHasContent(relData.getHasContent());
            rows.add(row);
        }

        HorizontalListAdapter adapter = new HorizontalListAdapter(context, rows, tvCardDetailListener);
        super.setAdapter(adapter, rows.size());
        if (tvCardDetailListener != null && tvCardDetailListener.getGenericStyles() != null){
            genericStyles = tvCardDetailListener.getGenericStyles();
            btnBack.setBackground(Utils.makeButtonSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("selectedColor").getValue()));
            btnNext.setBackground(Utils.makeButtonSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("selectedColor").getValue()));
        }

    }
}
