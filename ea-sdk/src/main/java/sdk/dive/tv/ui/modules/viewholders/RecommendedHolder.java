package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.RelationModule;
import com.touchvie.sdk.model.Single;

import java.util.ArrayList;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.adapters.HorizontalListAdapter;
import sdk.dive.tv.ui.modules.data.HorizontalItemData;

import static com.touchvie.sdk.model.Single.ContentTypeEnum.RECOMMENDED;

/**
 * Created by Tagsonomy S.L. on 14/11/2016.
 */

public class RecommendedHolder extends HorizontalListHolder {

    public RecommendedHolder(View itemView, String simpleName) {
        super(itemView);
        this.moduleName = simpleName;
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, final TvCardDetailListener tvCardDetailListener, final SectionListener sectionListener) {

        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);
        mTitle.setText(context.getResources().getString(R.string.CARD_MODULE_TITLE_RECOMMENDED));

        final RelationModule relation = Utils.getRelation(cardData, RECOMMENDED.getValue());
        if (relation == null) {
            return;
        }

        final ArrayList<HorizontalItemData> rows = new ArrayList<>();

        for (Card relData : ((Single) relation).getData()) {
            HorizontalItemData data = new HorizontalItemData();
            data.setImage(relData.getImage());
            data.setCardId(relData.getCardId());
            data.setCardType(relData.getType().getValue());
            data.setHasContent(relData.getHasContent());
            rows.add(data);
        }

        HorizontalListAdapter adapter = new HorizontalListAdapter(context, rows, tvCardDetailListener);
        super.setAdapter(adapter, rows.size());
    }

}
