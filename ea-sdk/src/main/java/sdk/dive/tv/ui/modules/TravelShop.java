package sdk.dive.tv.ui.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.Product;

import sdk.dive.tv.ui.modules.viewholders.TravelShopHolder;

/**
 * Created by Tagsonomy S.L. on 17/11/2016.
 */

public class TravelShop extends HorizontalListModule {

    public TravelShop() {
        super();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(this.layout, group, false);
        TravelShopHolder holder = new TravelShopHolder(viewGroup, getClass().getSimpleName());
        return holder;
    }

    @Override
    public boolean validate(Card cardData, boolean isCarousel) {

        if (cardData.getProducts() == null || cardData.getProducts().size() <= 0) {
            return false;
        }

        for (Product product : cardData.getProducts()) {
            if (product.getCategory() != null && product.getCategory().equals(Product.CategoryEnum.TRAVEL)) {
                return true;
            }
        }

        return false;
    }
}
