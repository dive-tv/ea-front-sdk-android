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

import static com.touchvie.sdk.model.Single.ContentTypeEnum.APPEARS_IN;

/**
 * Created by Tagsonomy S.L. on 18/10/2016.
 */

public class AppearsInHolder extends HorizontalListHolder {
    private HashMap<String, ModuleStyleData> genericStyles;

    public AppearsInHolder(View itemView, String simpleName) {
        super(itemView);
        this.moduleName = simpleName;
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, TvCardDetailListener cardDetailListener, SectionListener sectionListener) {

        super.configure(cardData, picasso, context, cardDetailListener, sectionListener);

        mTitle.setText(context.getResources().getString(R.string.CARD_MODULE_TITLE_APPEARS_IN));

        RelationModule rel = Utils.getRelation(cardData, APPEARS_IN.getValue());
        if (rel == null) {
            return;
        }

        final ArrayList<HorizontalItemData> rows = new ArrayList<>();
        for (Card relData : ((Single) rel).getData()) {
            HorizontalItemData row = new HorizontalItemData();
            if (relData == null)
                continue;
            if (relData.getImage() != null) {
                row.setImage(relData.getImage());
            } else {
                row.setImage(null);
            }
            row.setCardId(relData.getCardId());
            row.setCardType(relData.getType().getValue());
            row.setHasContent(relData.getHasContent());
            rows.add(row);

        }


        HorizontalListAdapter adapter = new HorizontalListAdapter(context, rows, cardDetailListener);
        super.setAdapter(adapter, rows.size());

        if (cardDetailListener != null && cardDetailListener.getGenericStyles() != null){
            genericStyles = cardDetailListener.getGenericStyles();
            btnBack.setBackground(Utils.makeButtonSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("selectedColor").getValue()));
            btnNext.setBackground(Utils.makeButtonSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("selectedColor").getValue()));
        }

    }

}
