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

import static com.touchvie.sdk.model.Duple.ContentTypeEnum.FILMOGRAPHY;

/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public class FilmographyHolder extends HorizontalListHolder {
    private HashMap<String, ModuleStyleData> genericStyles;

    public FilmographyHolder(View itemView, String simpleName) {
        super(itemView);
        this.moduleName = simpleName;
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, final TvCardDetailListener tvCardDetailListener, final SectionListener sectionListener) {

        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);
        mTitle.setText(context.getResources().getString(R.string.CARD_MODULE_FILMOGRAPHY));

        RelationModule relation = Utils.getRelation(cardData, FILMOGRAPHY.getValue());
        if (relation == null) {
            return;
        }

        final ArrayList<HorizontalItemData> rows = new ArrayList<>();

        for (DupleData relData : ((Duple) relation).getData()) {
            switch (relData.getRelType()) {
                case ACTS_IN:
                case PLAYS:
                    HorizontalItemData playsRow = new HorizontalItemData();


                    if (relData.getFrom() != null) {
                        if (relData.getFrom().getImage() != null) {
                            playsRow.setImage(relData.getFrom().getImage());
                        }
                        playsRow.setCardId(relData.getFrom().getCardId());
                        playsRow.setHasContent(relData.getFrom().getHasContent());
                        playsRow.setCardId(relData.getFrom().getCardId());
                        playsRow.setCardType(relData.getFrom().getType().getValue());
                    }

                    rows.add(playsRow);

                    break;
                case DIRECTS:
                    HorizontalItemData directsRow = new HorizontalItemData(); //Row data for carousel.
                    if (relData.getFrom() != null) {

                        if (relData.getFrom().getImage() != null) {
                            directsRow.setImage(relData.getFrom().getImage()); //get the image for carousel
                        }
                        directsRow.setCardId(relData.getFrom().getCardId());
                        directsRow.setCardType(relData.getFrom().getType().getValue());
                    }
                    directsRow.setHasContent(relData.getFrom().getHasContent());
                    rows.add(directsRow);
                    break;
            }
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
