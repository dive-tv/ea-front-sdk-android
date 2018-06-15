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

import static com.touchvie.sdk.model.Duple.ContentTypeEnum.FEATURED_IN;
import static com.touchvie.sdk.model.DupleData.RelTypeEnum.FILMED_IN;
import static com.touchvie.sdk.model.DupleData.RelTypeEnum.LOCATION_ON_MOVIE;
import static com.touchvie.sdk.model.DupleData.RelTypeEnum.REPRESENTS;


/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public class AppearsInLocationHolder extends HorizontalListHolder {
    private HashMap<String, ModuleStyleData> genericStyles;

    public AppearsInLocationHolder(View itemView, String simpleName) {
        super(itemView);
        this.moduleName = simpleName;
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, final TvCardDetailListener tvCardDetailListener, final SectionListener sectionListener) {

        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);

        mTitle.setText(context.getResources().getString(R.string.CARD_MODULE_TITLE_APPEARS_IN));

        RelationModule relation = Utils.getRelation(cardData, FEATURED_IN.getValue());
        if (relation == null) {
            return;
        }

        final ArrayList<HorizontalItemData> rows = new ArrayList<>(); //to store the carousel row data.

        final HashMap<String, ArrayList<HorizontalItemData>> rowData = new HashMap<>(); //to store the full list row data.

        for (DupleData relData : ((Duple) relation).getData()) {
            HorizontalItemData row = new HorizontalItemData();
            switch (relData.getRelType()) {
                case FILMED_IN:
                    HorizontalItemData filmedInRow = new HorizontalItemData();
                    if (rowData.get(FILMED_IN) == null) {
                        rowData.put(FILMED_IN.getValue(), new ArrayList<HorizontalItemData>());
                    }

                    rowData.get(FILMED_IN).add(filmedInRow);

                    if (relData.getFrom() != null) {

                        if (relData.getFrom().getImage() != null && relData.getFrom().getImage().getThumb() != null) {
                            row.setImage(relData.getFrom().getImage());
                            filmedInRow.setImage(relData.getFrom().getImage());
                        } else {
                            row.setImage(null);
                        }
                        row.setCardId(relData.getFrom().getCardId());
                        row.setCardType(relData.getFrom().getType().getValue());


                        row.setHasContent(relData.getFrom().getHasContent());
                        rows.add(row);
                    }
                    break;
                case REPRESENTS:
                    HorizontalItemData asRow = new HorizontalItemData();
                    if (rowData.get(REPRESENTS) == null) {
                        rowData.put(REPRESENTS.getValue(), new ArrayList<HorizontalItemData>());
                    }
                    rowData.get(REPRESENTS).add(asRow);

                    if (relData.getFrom() != null) {

                        if (relData.getFrom().getImage() != null && relData.getFrom().getImage().getThumb() != null) {
                            row.setImage(relData.getFrom().getImage());
                            asRow.setImage(relData.getFrom().getImage());
                        } else {
                            row.setImage(null);
                        }
                        row.setCardId(relData.getFrom().getCardId());
                        row.setCardType(relData.getFrom().getType().getValue());
                        row.setHasContent(relData.getFrom().getHasContent());
                    }

                    rows.add(row);
                    break;
                case LOCATION_ON_MOVIE:

                    HorizontalItemData featuredRow = new HorizontalItemData();
                    if (rowData.get(LOCATION_ON_MOVIE) == null) {
                        rowData.put(LOCATION_ON_MOVIE.getValue(), new ArrayList<HorizontalItemData>());
                    }
                    rowData.get(LOCATION_ON_MOVIE).add(featuredRow);

                    if (relData.getFrom() != null) {

                        if (relData.getFrom().getImage() != null && relData.getFrom().getImage().getThumb() != null) {
                            row.setImage(relData.getFrom().getImage());
                            featuredRow.setImage(relData.getFrom().getImage());
                        } else {
                            row.setImage(null);
                        }
                        row.setCardType(relData.getFrom().getType().getValue());
                        row.setCardId(relData.getFrom().getCardId());
                        row.setHasContent(relData.getFrom().getHasContent());
                    }
                    rows.add(row);
                    break;
            }
        }
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
