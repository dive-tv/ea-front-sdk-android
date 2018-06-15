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

public class AppearsInSingleHolder extends HorizontalListHolder {
    private HashMap<String, ModuleStyleData> genericStyles;

    /**
     * Default constructor
     *
     * @param itemView
     * @param simpleName
     */
    public AppearsInSingleHolder(View itemView, String simpleName) {
        super(itemView);
        this.moduleName = simpleName;
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, TvCardDetailListener tvCardDetailListener, SectionListener sectionListener) {

        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);

        mTitle.setText(context.getResources().getString(R.string.CARD_MODULE_TITLE_APPEARS_IN));

        RelationModule rel = Utils.getRelation(cardData, APPEARS_IN.getValue());
        if (rel == null) {
            return;
        }

        if (((Single) rel).getData().size() != 1) {
            return;
        }
        final Card relData = ((Single) rel).getData().get(0);
        if (relData == null) {
            return;
        }

        final ArrayList<HorizontalItemData> rows = new ArrayList<>();

        HorizontalItemData row = new HorizontalItemData();

        if (relData.getImage() != null) {
            row.setImage(relData.getImage());
        } else {
            row.setImage(null);
        }
        row.setCardId(relData.getCardId());
        row.setCardType(relData.getType().getValue());
        row.setHasContent(relData.getHasContent());
        rows.add(row);

        HorizontalListAdapter adapter = new HorizontalListAdapter(context, rows, tvCardDetailListener);
        super.setAdapter(adapter, rows.size());
        if (tvCardDetailListener != null && tvCardDetailListener.getGenericStyles() != null) {
            genericStyles = tvCardDetailListener.getGenericStyles();
            mContainer.setBackgroundColor(Color.parseColor(genericStyles.get("backgroundModuleColor").getValue()));
            btnBack.setBackground(Utils.makeButtonSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()), Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("selectedColor").getValue()));
            btnNext.setBackground(Utils.makeButtonSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()), Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("selectedColor").getValue()));
        }
    }

}
