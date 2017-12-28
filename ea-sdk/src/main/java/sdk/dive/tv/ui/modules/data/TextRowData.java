package sdk.dive.tv.ui.modules.data;

import java.io.Serializable;

/**
 * Created by Tagsonomy S.L. on 14/10/2016.
 */

public class TextRowData implements Serializable {

    protected String text;
    protected String cardId;
    protected String cardType;
    protected boolean hasContent = true;

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
