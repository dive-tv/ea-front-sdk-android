package sdk.dive.tv.ui.modules.data;

import java.io.Serializable;

/**
 * Created by Tagsonomy S.L. on 15/11/2016.
 */

public class SongData implements Serializable {

    String title;
    String subtitle;
    String cardId;
    String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



}
