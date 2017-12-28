package sdk.dive.tv.ui.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.CardContainer;

import sdk.dive.tv.ui.modules.viewholders.GalleryHolder;

import static com.touchvie.sdk.model.Image.ContentTypeEnum.GALLERY;

/**
 * Created by Tagsonomy S.L. on 04/10/2016.
 */

public class Gallery extends HorizontalListModule {
    /**
     * Default constructor
     */
    public Gallery() {
        super();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(this.layout, group, false);
        GalleryHolder holder = new GalleryHolder(viewGroup, getClass().getSimpleName());
        return holder;
    }

    @Override
    public boolean validate(Card cardData, boolean isCarousel) {

        CardContainer container = getContainer(cardData, GALLERY.getValue());
        if (container == null) {
            return false;
        }
        return true;
    }
}
