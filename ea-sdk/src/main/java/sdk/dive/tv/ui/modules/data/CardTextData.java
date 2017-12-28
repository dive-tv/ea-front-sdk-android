package sdk.dive.tv.ui.modules.data;

import java.io.Serializable;

/**
 * Created by Tagsonomy S.L. on 14/11/2016.
 */

public class CardTextData implements Serializable{

    String text;
    String cardId;
    String cardType;
    boolean hasContent;

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
}
