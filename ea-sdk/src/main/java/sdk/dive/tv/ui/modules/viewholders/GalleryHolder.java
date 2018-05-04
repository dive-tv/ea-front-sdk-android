package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.CardContainer;
import com.touchvie.sdk.model.Image;
import com.touchvie.sdk.model.ImageData;

import java.util.ArrayList;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.adapters.GalleryAdapter;

import static com.touchvie.sdk.model.Image.ContentTypeEnum.GALLERY;

/**
 * Created by Tagsonomy S.L. on 05/10/2016.
 */

public class GalleryHolder extends HorizontalListHolder {
    /**
     * Default constructor
     *
     * @param itemView
     * @param simpleName
     */
    public GalleryHolder(View itemView, String simpleName) {
        super(itemView);
        this.moduleName = simpleName;
    }

    @Override
    public void configure(Card cardData, Picasso picasso, final Context context, final TvCardDetailListener cardDetailListener, SectionListener sectionListener) {
        super.configure(cardData, picasso, context, cardDetailListener, sectionListener);
        
        mTitle.setText(context.getResources().getString(R.string.CARD_MODULE_TITLE_GALLERY));

        CardContainer container = Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), GALLERY.getValue());
        if (container == null) {
            return;
        }

        final ArrayList<ImageData> images = new ArrayList<>();
        images.addAll(((Image) container).getData());

        GalleryAdapter adapter = new GalleryAdapter(context, images, cardDetailListener);
        super.setAdapter(adapter, images.size());
    }
}