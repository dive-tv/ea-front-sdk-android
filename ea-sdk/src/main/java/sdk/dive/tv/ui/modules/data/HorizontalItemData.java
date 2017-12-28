package sdk.dive.tv.ui.modules.data;

import com.touchvie.sdk.model.ImageData;

import java.io.Serializable;

/**
 * Created by Tagsonomy S.L. on 14/11/2016.
 */

public class HorizontalItemData implements Serializable {

    private String text;
    private String cardId;
    private String cardType;
    private ImageData image;
    private boolean hasContent;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public boolean isHasContent() {
        return hasContent;
    }

    public void setHasContent(boolean hasContent) {
        this.hasContent = hasContent;
    }

    public ImageData getImage() {
        return image;
    }

    public void setImage(ImageData image) {
        this.image = image;
    }
}
