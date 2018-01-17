package sdk.dive.tv.ui.modules.data;

import com.touchvie.sdk.model.ImageData;

import java.io.Serializable;

/**
 * Created by Tagsonomy S.L. on 07/11/2016.
 */

public class SquareImageRowData implements Serializable {

    private ImageData image;
    private String title;
    private String subtitle;
    private String cardId;
    private String cardVersion;
    private String cardType;
    private boolean hasContent;

    public ImageData getImage() {
        return image;
    }

    public void setImage(ImageData image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardVersion() {
        return cardVersion;
    }

    public void setCardVersion(String cardVersion) {
        this.cardVersion = cardVersion;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public boolean hasContent() {
        return hasContent;
    }

    public void setHasContent(boolean clickable) {
        hasContent = clickable;
    }
}
