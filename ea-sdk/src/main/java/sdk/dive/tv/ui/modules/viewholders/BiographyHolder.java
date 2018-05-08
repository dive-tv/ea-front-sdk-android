package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
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
import static com.touchvie.sdk.model.Text.ContentTypeEnum.BIOGRAPHY;

/**
 * Created by Tagsonomy S.L. on 10/10/2016.
 */

public class BiographyHolder extends TextHolder {
    private HashMap<String, ModuleStyleData> genericStyles;

    public BiographyHolder(View view, String simpleName) {
        super(view);
        this.moduleName = simpleName;
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, final TvCardDetailListener tvCardDetailListener, SectionListener sectionListener) {

        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);
        title.setText(context.getResources().getString(R.string.CARD_MODULE_TITLE_BIOGRAPHY));
        title.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR));

        this.source_cont.setPadding(0, 0, 0, 0);

        CardContainer mContainer = Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), BIOGRAPHY.getValue());
        if (mContainer == null) {
            btnUp.setVisibility(GONE);
            btnDown.setVisibility(GONE);
            return;
        }
        Text biography = (Text) mContainer;
        if (biography.getData() != null && biography.getData().size() == 1) {
            TextData containerText = biography.getData().get(0);
            if (containerText == null)
                return;
            if (containerText.getText() != null) {
                String containerTxt = containerText.getText();
                containerTxt = containerTxt.replace("\n", "<br>");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    text.setText(Html.fromHtml(containerTxt, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    text.setText(Html.fromHtml(containerTxt));
                }
                if ("".equals(containerTxt)) {
                    btnUp.setVisibility(GONE);
                    btnDown.setVisibility(GONE);
                }
            }
            manageSource(containerText);
            checkScrollAndButtons();
        }
        if (tvCardDetailListener != null && tvCardDetailListener.getGenericStyles() != null){
            genericStyles = tvCardDetailListener.getGenericStyles();
            container.setBackground(Utils.makeSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("backgroundModuleColor").getValue())));
        }

    }
}
