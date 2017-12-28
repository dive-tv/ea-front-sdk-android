package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.Product;

import java.util.ArrayList;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.adapters.TravelShopAdapter;

/**
 * Created by Tagsonomy S.L. on 17/11/2016.
 */

public class TravelShopHolder extends HorizontalListHolder {

    public TravelShopHolder(View itemView, String simpleName) {
        super(itemView);
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, final TvCardDetailListener tvCardDetailListener, final SectionListener sectionListener) {

        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);

        mTitle.setText(context.getResources().getString(R.string.CARD_MODULE_TRAVEL));

        if (cardData.getProducts() == null || cardData.getProducts().size() <= 0) {
            return;
        }

        final ArrayList<Product> rows = new ArrayList<>();
        for (Product product : cardData.getProducts()) {
            if (product.getCategory() == null || !product.getCategory().equals(Product.CategoryEnum.TRAVEL)) {
                continue;
            }
            rows.add(product);
        }

        TravelShopAdapter adapter = new TravelShopAdapter(context, rows, tvCardDetailListener);
        super.setAdapter(adapter, rows.size());

    }
}
