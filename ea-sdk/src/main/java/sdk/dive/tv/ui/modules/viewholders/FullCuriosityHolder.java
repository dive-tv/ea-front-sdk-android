package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

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
import static com.touchvie.sdk.model.Text.ContentTypeEnum.CURIOSITY;

/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public class FullCuriosityHolder extends TextHolder {

    protected FrameLayout mContainer;
    protected TextView mTitle;
    protected TextView mText;
    private HashMap<String, ModuleStyleData> genericStyles;

    /**
     * Default constructor
     *
     * @param itemView
     * @param simpleName
     */

    public FullCuriosityHolder(View itemView, String simpleName) {

        super(itemView);
        this.moduleName = simpleName;
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, TvCardDetailListener tvCardDetailListener, SectionListener sectionListener) {

        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);
        title.setText(context.getResources().getString(R.string.CARD_MODULE_TITLE_MOVIE_TRIVIAS));
        title.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_BOLD));
        text.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR));

        this.source_cont.setPadding(0, 0, 0, 0);

        CardContainer mContainer = Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), CURIOSITY.getValue());
        if (mContainer == null) {
            btnUp.setVisibility(GONE);
            btnDown.setVisibility(GONE);
            return;
        }
        Text curiosity = (Text) mContainer;
        if (((Text) mContainer).getData() != null && ((Text) mContainer).getData().size() == 1) {
            TextData containerText = curiosity.getData().get(0);
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
            container.setBackground(Utils.makeSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor("#00000000"), genericStyles.get("backgroundModuleColor").getValue()));
            btnDown.setBackground(Utils.makeButtonSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("selectedColor").getValue()));
            btnUp.setBackground(Utils.makeButtonSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("selectedColor").getValue()));
        }

    }
}

