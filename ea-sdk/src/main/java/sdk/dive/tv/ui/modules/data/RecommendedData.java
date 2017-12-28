package sdk.dive.tv.ui.modules.data;

import com.touchvie.sdk.model.ImageData;

/**
 * Created by Tagsonomy S.L. on 15/11/2016.
 */

public class RecommendedData {

    private ImageData image;
    private String cardId;
    private String type;
    private boolean hasContent;

    public ImageData getImage() {
        return image;
    }

    public void setImage(ImageData image) {
        this.image = image;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isHasContent() {
        return hasContent;
    }

    public void setHasContent(boolean hasContent) {
        this.hasContent = hasContent;
    }
}
