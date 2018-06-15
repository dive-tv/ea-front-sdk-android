package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.CardContainer;
import com.touchvie.sdk.model.Seasons;
import com.touchvie.sdk.model.SeasonsData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.data.ModuleStyleData;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.adapters.HorizontalListAdapter;
import sdk.dive.tv.ui.modules.adapters.SeasonsAdapter;
import sdk.dive.tv.ui.modules.data.HorizontalItemData;
import sdk.dive.tv.ui.modules.data.ImageRowData;

import static com.touchvie.sdk.model.Seasons.ContentTypeEnum.SEASONS;

/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public class SeasonsHolder extends HorizontalListHolder {
    private HashMap<String, ModuleStyleData> genericStyles;

    protected SeasonsAdapter adapter;
    protected ArrayList<ImageRowData> data;

    protected ArrayList<SeasonsData> seasons;

    protected TvCardDetailListener tvCardDetailListener;

    public SeasonsHolder(View itemView, String simpleName) {
        super(itemView);
        this.moduleName = simpleName;
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, TvCardDetailListener tvCardDetailListener, final SectionListener sectionListener) {

        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);

        mTitle.setText(context.getResources().getString(R.string.CARD_MODULE_SEASONS));
        this.tvCardDetailListener = tvCardDetailListener;

        CardContainer cont = Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), SEASONS.getValue());
        if (cont == null) {
            return;
        }
        Seasons container = (Seasons) cont;

        if (container.getData() == null || container.getData().size() == 0)
            return;

        final ArrayList<HorizontalItemData> rows = new ArrayList<>();

        int length = container.getData().size();
        Collections.addAll(seasons, container.getData().toArray(new SeasonsData[container.getData().size()]));

        for (int i = 0; i < length; i++) {
            SeasonsData season = container.getData().get(i);
            HorizontalItemData rowData = new HorizontalItemData();
            rowData.setImage(season.getImage());
            rowData.setText(context.getString(R.string.season_number, String.valueOf(season.getSeasonIndex())));
            rows.add(rowData);
        }

        HorizontalListAdapter adapter = new HorizontalListAdapter(context, rows, tvCardDetailListener);
//        SeasonsAdapter adapter = new SeasonsAdapter(context, rows, this);
        super.setAdapter(adapter, rows.size());
        if (tvCardDetailListener != null && tvCardDetailListener.getGenericStyles() != null){
            genericStyles = tvCardDetailListener.getGenericStyles();
            mContainer.setBackground(Utils.makeSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor("#00000000"), genericStyles.get("backgroundModuleColor").getValue()));
            btnBack.setBackground(Utils.makeButtonSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("selectedColor").getValue()));
            btnNext.setBackground(Utils.makeButtonSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("selectedColor").getValue()));
        }

    }
}
