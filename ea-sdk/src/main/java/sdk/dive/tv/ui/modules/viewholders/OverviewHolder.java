package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.CardContainer;
import com.touchvie.sdk.model.Text;
import com.touchvie.sdk.model.TextData;

import java.util.HashMap;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.data.ModuleStyleData;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;

import static android.view.View.GONE;
import static com.touchvie.sdk.model.Text.ContentTypeEnum.OVERVIEW;

/**
 * Created by Tagsonomy S.L. on 18/10/2016.
 */

public class OverviewHolder extends TextHolder {
    private HashMap<String, ModuleStyleData> genericStyles;

    public OverviewHolder(View view, String simpleName) {
        super(view);
        this.moduleName = simpleName;
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, final TvCardDetailListener tvCardDetailListener, SectionListener sectionListener) {
        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);
        title.setText(context.getResources().getString(R.string.CARD_MODULE_TITLE_OVERVIEW));
        title.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR));
        CardContainer mContainer = Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), OVERVIEW.getValue());
        if (mContainer == null) {
            btnUp.setVisibility(GONE);
            btnDown.setVisibility(GONE);
            return;
        }
        Text overview = (Text) mContainer;
        if (overview.getData() != null && overview.getData().size() == 1) {
            TextData containerText = overview.getData().get(0);
            if (containerText == null)
                return;
            if (containerText.getText() != null) {
                String overviewTxt = containerText.getText();
                overviewTxt = overviewTxt.replace("\n", "<br>");
                text.setText(overviewTxt);
                if ("".equals(overviewTxt)) {
                    btnUp.setVisibility(GONE);
                    btnDown.setVisibility(GONE);
                }
            }
            manageSource(containerText);
            checkScrollAndButtons();
        }
        if (tvCardDetailListener != null && tvCardDetailListener.getGenericStyles() != null){
            genericStyles = tvCardDetailListener.getGenericStyles();
            container.setBackground(Utils.makeSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("backgroundColor").getValue()));
        }

    }
}
