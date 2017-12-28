package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.CardContainer;
import com.touchvie.sdk.model.Text;
import com.touchvie.sdk.model.TextData;

import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;

import static android.view.View.GONE;
import static com.touchvie.sdk.model.Text.ContentTypeEnum.REFERENCE;

/**
 * Created by Noemi on 13/11/2016.
 */

public class ReferenceHolder extends TextHolder {

    protected FrameLayout mContainer;
    protected TextView mTitle;
    protected TextView mText;

    /**
     * Default constructor
     *
     * @param itemView
     * @param simpleName
     */

    public ReferenceHolder(View itemView, String simpleName) {

        super(itemView);
        this.moduleName = simpleName;
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, TvCardDetailListener tvCardDetailListener, SectionListener sectionListener) {

        title.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_BOLD));
        text.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR));


        if (cardData.getTitle() != null) {
            title.setText(cardData.getTitle());
        } else {
            title.setVisibility(View.GONE);
        }

        CardContainer container = Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), REFERENCE.getValue());
        if (container == null) {
            btnUp.setVisibility(GONE);
            btnDown.setVisibility(GONE);
            return;
        }
        Text reference = (Text) container;
        if (((Text) container).getData() != null && ((Text) container).getData().size() == 1) {
            TextData containerText = reference.getData().get(0);


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
                if ("".equals(containerText)) {
                    btnUp.setVisibility(GONE);
                    btnDown.setVisibility(GONE);
                }
            }
            manageSource(containerText);
            checkScrollAndButtons();
        }
    }

}
