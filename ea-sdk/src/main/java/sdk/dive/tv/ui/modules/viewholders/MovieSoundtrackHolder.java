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
import sdk.dive.tv.ui.modules.adapters.MovieSoundtrackAdapter;
import sdk.dive.tv.ui.modules.data.TwoTextRowData;

import static com.touchvie.sdk.model.Single.ContentTypeEnum.MOVIE_SONGS;


/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public class MovieSoundtrackHolder extends HorizontalListHolder {
    private HashMap<String, ModuleStyleData> genericStyles;

    public MovieSoundtrackHolder(View itemView, String simpleName) {
        super(itemView);
        this.moduleName = simpleName;
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, TvCardDetailListener cardDetailListener, SectionListener sectionListener) {

        super.configure(cardData, picasso, context, cardDetailListener, sectionListener);
        mTitle.setText(context.getResources().getString(R.string.CARD_MODULE_TITLE_SOUNDTRACK));

        final ArrayList<TwoTextRowData> rows = new ArrayList<>();

        RelationModule relation = Utils.getRelation(cardData, MOVIE_SONGS.getValue());
        if (relation == null) {
            return;
        }

        for (Card relData : ((Single) relation).getData()) {
            TwoTextRowData row = new TwoTextRowData();
            if (relData != null) {
                row.setCardId(relData.getCardId());
                row.setText(relData.getTitle());
                row.setSubtitle(relData.getSubtitle());
                row.setCardType(relData.getType().getValue());
                row.setHasContent(relData.getHasContent());
            }
            rows.add(row);
        }

        MovieSoundtrackAdapter adapter = new MovieSoundtrackAdapter(context, rows, cardDetailListener);
        super.setAdapter(adapter, rows.size());
        if (cardDetailListener != null && cardDetailListener.getGenericStyles() != null){
            genericStyles = cardDetailListener.getGenericStyles();
            mContainer.setBackground(Utils.makeSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor("#00000000"), genericStyles.get("backgroundModuleColor").getValue()));
            btnBack.setBackground(Utils.makeButtonSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("selectedColor").getValue()));
            btnNext.setBackground(Utils.makeButtonSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("selectedColor").getValue()));
        }

    }
}
