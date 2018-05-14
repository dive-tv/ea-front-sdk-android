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

import static com.touchvie.sdk.model.Single.ContentTypeEnum.HOME_DECO;

/**
 * Created by Tagsonomy S.L. on 18/11/2016.
 */

public class HomeHolder extends HorizontalListHolder {
    private HashMap<String, ModuleStyleData> genericStyles;

    public HomeHolder(View itemView, String simpleName) {
        super(itemView);
        this.moduleName = simpleName;
    }

    @Override
    public void configure(final Card cardData, Picasso picasso, final Context context, TvCardDetailListener tvCardDetailListener, final SectionListener sectionListener) {

        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);

        mTitle.setText(context.getResources().getString(R.string.HOME));

        final ArrayList<HorizontalItemData> rows = new ArrayList<>();

        RelationModule rel = Utils.getRelation(cardData, HOME_DECO.getValue());
        if (rel == null) {
            return;
        }

        for (Card relData : ((Single) rel).getData()) {
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
