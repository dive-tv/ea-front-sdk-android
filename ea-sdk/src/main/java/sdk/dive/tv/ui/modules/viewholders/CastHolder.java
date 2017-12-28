package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.Duple;
import com.touchvie.sdk.model.DupleData;
import com.touchvie.sdk.model.RelationModule;

import java.util.ArrayList;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.adapters.HorizontalListAdapter;
import sdk.dive.tv.ui.modules.data.HorizontalItemData;

import static com.touchvie.sdk.model.Duple.ContentTypeEnum.CASTING;

/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public class CastHolder extends HorizontalListHolder {

    public CastHolder(View itemView, String simpleName) {
        super(itemView);
        this.moduleName = simpleName;
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, final TvCardDetailListener tvCardDetailListener, final SectionListener sectionListener) {

        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);

        if (cardData != null && "movie".equals(cardData.getType())) {
            mTitle.setText(context.getResources().getString(R.string.CARD_MODULE_CAST));
        } else {
            mTitle.setText(context.getResources().getString(R.string.CARD_MODULE_MAIN_CAST));
        }


        RelationModule relation = Utils.getRelation(cardData, CASTING.getValue());
        if (relation == null) {
            return;
        }

        final ArrayList<HorizontalItemData> rows = new ArrayList<>();

        if (((Duple) relation).getData() == null || ((Duple) relation).getData().size() <= 0) {
            return;
        }
        String interpretes = context.getResources().getString(R.string.CARD_MODULE_PLAY);

        for (DupleData relData : ((Duple) relation).getData()) {
            HorizontalItemData row = new HorizontalItemData();
            if (relData.getFrom() == null) {
                continue;
            }
            row.setImage(relData.getFrom().getImage());
            row.setText(relData.getFrom().getTitle());
            row.setHasContent(relData.getFrom().getHasContent());
            row.setCardId(relData.getFrom().getCardId());
            row.setCardType(relData.getFrom().getType().getValue());
            rows.add(row);
        }

        HorizontalListAdapter adapter = new HorizontalListAdapter(context, rows, tvCardDetailListener);
        super.setAdapter(adapter, rows.size());
    }
}