package sdk.dive.tv.ui.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.touchvie.sdk.model.Awards;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.CardContainer;
import com.touchvie.sdk.model.Catalog;
import com.touchvie.sdk.model.Image;
import com.touchvie.sdk.model.Link;
import com.touchvie.sdk.model.Listing;
import com.touchvie.sdk.model.Rating;
import com.touchvie.sdk.model.Seasons;
import com.touchvie.sdk.model.Single;
import com.touchvie.sdk.model.Text;

import java.util.HashMap;

/**
 *
 */
public abstract class Module {

    /**
     * Type of module
     */
    protected String type;

    /**
     * XML layout to inflate the viewholder of this module.
     */
    protected Integer layout;

    private HashMap<String, CardContainer> contentTypeContainer = null;

    /**
     * Default constructor
     */
    public Module() {

    }

    /**
     * Sets the module type
     * @param modType type of the Module
     */
    public void setType(String modType) {
        this.type = modType;
    }

    /**
     * Gets the module type
     * @return type the type of the module
     */
    public String getType() {
        return type;
    }


    public Integer getLayout() {
        return layout;
    }

    public void setLayout(Integer layout) {
        this.layout = layout;
    }

    public CardContainer getContainer(Card card, String contentType) {
        if (card.getInfo() == null || card.getInfo().size() == 0) {
            return null;
        }
        if (contentTypeContainer == null) {
            contentTypeContainer = new HashMap<>();
            for (CardContainer container : card.getInfo()) {
                if (container instanceof Text)
                    contentTypeContainer.put(((Text) container).getContentType().getValue(), container);
                else if (container instanceof Listing)
                    contentTypeContainer.put(((Listing) container).getContentType().getValue(), container);
                else if (container instanceof Rating)
                    contentTypeContainer.put(((Rating) container).getContentType().getValue(), container);
                else if (container instanceof com.touchvie.sdk.model.Map)
                    contentTypeContainer.put(((com.touchvie.sdk.model.Map) container).getContentType().getValue(), container);
                else if (container instanceof Link)
                    contentTypeContainer.put(((Link) container).getContentType().getValue(), container);
                else if (container instanceof Awards)
                    contentTypeContainer.put(((Awards) container).getContentType().getValue(), container);
                else if (container instanceof Catalog)
                    contentTypeContainer.put(((Catalog) container).getContentType().getValue(), container);
                else if (container instanceof Seasons)
                    contentTypeContainer.put(((Seasons) container).getContentType().getValue(), container);
                else if (container instanceof Image)
                    contentTypeContainer.put(((Image) container).getContentType().getValue(), container);
            }
        }

        return contentTypeContainer.get(contentType);
    }


    public abstract RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group);

    public abstract boolean validate(Card cardData, boolean isCarousel);
}