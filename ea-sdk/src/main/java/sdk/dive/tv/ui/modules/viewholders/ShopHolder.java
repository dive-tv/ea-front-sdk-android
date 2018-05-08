package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.ImageData;
import com.touchvie.sdk.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.data.ModuleStyleData;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.adapters.HorizontalListAdapter;
import sdk.dive.tv.ui.modules.data.HorizontalItemData;

/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public class ShopHolder extends HorizontalListHolder {
    private HashMap<String, ModuleStyleData> genericStyles;

    public ShopHolder(View itemView, String simpleName) {
        super(itemView);
        this.moduleName = simpleName;
    }

    @Override
    public void configure(final Card cardData, Picasso picasso, final Context context, final TvCardDetailListener tvCardDetailListener, final SectionListener sectionListener) {

        super.configure(cardData, picasso, context, tvCardDetailListener, sectionListener);

        mTitle.setText(context.getResources().getString(R.string.CARD_MODULE_TITLE_SHOP));

        if (cardData.getProducts() == null || cardData.getProducts().size() <= 0) {
            return;
        }

        final ArrayList<HorizontalItemData> rows = new ArrayList<>();
        for (Product product : cardData.getProducts()) {
            HorizontalItemData row = new HorizontalItemData();
            if (product.getCategory() == null || product.getCategory().equals(Product.CategoryEnum.TRAVEL)) {
                continue;
            }
            ImageData image = new ImageData();
            image.setThumb(product.getImage());
            image.setFull(product.getImage());
            image.setAnchorX(BigDecimal.valueOf(50));
            image.setAnchorY(BigDecimal.valueOf(50));
            row.setImage(image);
            String price = (product.getPrice() != null ? product.getPrice() : "") + (product.getCurrency() != null ? Currency.getInstance(product.getCurrency()).getSymbol() : "");
            row.setText(price);
            row.setCardId(product.getProductId());
            row.setCardType(product.getCategory().getValue());
            rows.add(row);
        }

        HorizontalListAdapter adapter = new HorizontalListAdapter(context, rows, tvCardDetailListener);
        super.setAdapter(adapter, rows.size());
        if (tvCardDetailListener != null && tvCardDetailListener.getGenericStyles() != null){
            genericStyles = tvCardDetailListener.getGenericStyles();
            btnBack.setBackground(Utils.makeButtonSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("selectedColor").getValue()));
            btnNext.setBackground(Utils.makeButtonSelector(Color.parseColor(genericStyles.get("selectedColor").getValue()),Color.parseColor(genericStyles.get("unselectedColor").getValue()), genericStyles.get("selectedColor").getValue()));
        }

    }
}
