package sdk.dive.tv.ui.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.Product;

import sdk.dive.tv.ui.modules.viewholders.ShopHolder;

/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public class Shop extends HorizontalListModule {

    public Shop() {
        super();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(this.layout, group, false);
        ShopHolder holder = new ShopHolder(viewGroup, getClass().getSimpleName());
        return holder;
    }

    @Override
    public boolean validate(Card cardData, boolean isCarousel) {

        if (cardData.getProducts() == null || cardData.getProducts().size() <= 0) {
            return false;
        }

        for (Product product : cardData.getProducts()) {
            if (product.getCategory() != null && !product.getCategory().equals(Product.CategoryEnum.TRAVEL)) {
                return true;
            }
        }
        return false;
    }
}
