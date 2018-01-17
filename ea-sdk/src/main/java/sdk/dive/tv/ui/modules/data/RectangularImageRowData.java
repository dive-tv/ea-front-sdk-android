package sdk.dive.tv.ui.modules.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Tagsonomy S.L. on 14/11/2016.
 */

public class RectangularImageRowData implements Serializable {

    private String image;
    private ArrayList<sdk.dive.tv.ui.modules.data.CardTextData> title;
    private ArrayList<sdk.dive.tv.ui.modules.data.CardTextData> subtitle;
    private String cardId;
    private String cardVersion;
    private String type;
    private boolean hasContent;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<sdk.dive.tv.ui.modules.data.CardTextData> getTitle() {
        return title;
    }

    public void setTitle(ArrayList<sdk.dive.tv.ui.modules.data.CardTextData> title) {
        this.title = title;
    }

    public ArrayList<sdk.dive.tv.ui.modules.data.CardTextData> getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(ArrayList<CardTextData> subtitle) {
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
