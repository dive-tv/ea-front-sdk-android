package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.CardContainer;
import com.touchvie.sdk.model.Listing;

import java.util.ArrayList;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.data.TwoTextRowData;

import static com.touchvie.sdk.model.Listing.ContentTypeEnum.BASIC_DATA;

/**
 * Created by Tagsonomy S.L. on 05/10/2016.
 */

public class BasicInfoHolder extends TwoColsModuleHolder {

    /**
     * Default constructor
     *
     * @param itemView
     * @param simpleName
     */
    public BasicInfoHolder(View itemView, String simpleName) {
        super(itemView);
        this.moduleName = simpleName;
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, TvCardDetailListener tvCardDetailListener, SectionListener sectionListener) {

        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);
        mTitle.setText(context.getResources().getString(R.string.CARD_MODULE_TITLE_BASIC_DATA));

        ArrayList<TwoTextRowData> rows = new ArrayList<>();

        CardContainer container = Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), BASIC_DATA.getValue());
        if (container == null) {
            return;
        }

        Listing info = (Listing) container;

        if (info.getData() == null || info.getData().size() == 0) {
            return;
        }
        for (int i = 0; i < info.getData().size(); i++) {
            TwoTextRowData rowData = new TwoTextRowData();
            rowData.setText(info.getData().get(i).getText());
            rowData.setSubtitle(info.getData().get(i).getValue());
            rows.add(rowData);
        }

        super.setData(rows, context, "BasicInfo", context.getResources().getString(R.string.CARD_MODULE_TITLE_BASIC_DATA), tvCardDetailListener);
    }
}
