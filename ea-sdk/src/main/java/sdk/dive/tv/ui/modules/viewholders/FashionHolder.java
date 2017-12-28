package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.RelationModule;
import com.touchvie.sdk.model.Single;

import java.util.ArrayList;
import java.util.Arrays;

import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;

import static android.view.View.GONE;
import static com.touchvie.sdk.model.Single.ContentTypeEnum.FASHION_SET;

/**
 * Created by Tagsonomy S.L. on 18/11/2016.
 */

public class FashionHolder extends GroupedModuleHolder {

    public FashionHolder(View itemView, String simpleName) {
        super(itemView);
        this.moduleName = simpleName;
    }

    @Override
    public void configure(final Card cardData, Picasso picasso, final Context context, TvCardDetailListener tvCardDetailListener, final SectionListener sectionListener) {

        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);

        mTitle.setVisibility(GONE);

        RelationModule rel = Utils.getRelation(cardData, FASHION_SET.getValue());
        if (rel == null) {
            return;
        }

        if (((Single) rel).getData().size() <= 0) {
            return;
        }

        super.setData(new ArrayList<>(((Single) rel).getData()), "Fashion");
    }
}
