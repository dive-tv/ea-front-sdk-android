package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Awards;
import com.touchvie.sdk.model.AwardsData;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.CardContainer;

import java.util.ArrayList;
import java.util.HashMap;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.data.ModuleStyleData;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.adapters.AwardsAdapter;

import static com.touchvie.sdk.model.CardContainer.TypeEnum.AWARDS;

/**
 * Created by Tagsonomy S.L. on 05/10/2016.
 */

public class AwardsHolder extends VerticalListHolder {

    protected AwardsAdapter adapter;
    protected ArrayList<AwardsData> data;
    protected ScrollView scroll;
    protected FrameLayout btnDown, btnUp;
    private HashMap<String, ModuleStyleData> genericStyles;

    /**
     * Default constructor
     *
     * @param itemView
     * @param simpleName
     */
    public AwardsHolder(View itemView, String simpleName) {
        super(itemView);
        this.moduleName = simpleName;
    }

    @Override
    public void configure(Card cardData, Picasso picasso, final Context context, final TvCardDetailListener tvCardDetailListener, final SectionListener sectionListener) {

        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);

        mTitle.setText(context.getResources().getString(R.string.CARD_MODULE_TITLE_AWARDS));

        data = new ArrayList<>();

        final CardContainer container = Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), AWARDS.getValue());
        if (container == null) {
            return;
        }

        FrameLayout separator = new FrameLayout(context);
        FrameLayout.LayoutParams layoutparams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.stroke));
        layoutparams.topMargin = context.getResources().getDimensionPixelOffset(R.dimen.module_horizontal_list_item_separation);
        layoutparams.bottomMargin = context.getResources().getDimensionPixelOffset(R.dimen.module_horizontal_list_item_separation);
//        layoutparams.bottomMargin = 100;
        separator.setBackgroundColor(Utils.getColor(context, R.color.warm_grey));
        separator.setLayoutParams(layoutparams);

        adapter = new AwardsAdapter(context, ((Awards) container).getData(), true, sectionListener.isCarousel(), tvCardDetailListener);
        super.setAdapter(adapter, true, separator);
        if (tvCardDetailListener != null && tvCardDetailListener.getGenericStyles() != null){
            genericStyles = tvCardDetailListener.getGenericStyles();
            mContainer.setBackground(Utils.makeSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("backgroundModuleColor").getValue()), genericStyles.get("backgroundModuleColor").getValue()));
            mList.setBackground(Utils.makeSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("backgroundModuleColor").getValue()), genericStyles.get("backgroundModuleColor").getValue()));
            btnDown.setBackground(Utils.makeButtonSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("selectedColor").getValue()));
            btnUp.setBackground(Utils.makeButtonSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("selectedColor").getValue()));
        }

    }
}
