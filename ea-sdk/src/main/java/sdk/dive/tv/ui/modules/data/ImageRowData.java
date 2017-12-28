package sdk.dive.tv.ui.modules.data;

import java.util.ArrayList;

/**
 * Created by Tagsonomy S.L. on 14/10/2016.
 */

public class ImageRowData {

    private String image;
    private ArrayList<TextData> title;
    private ArrayList<TextData> subtitle;
    private String cardId;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<TextData> getTitle() {
        return title;
    }

    public void setTitle(ArrayList<TextData> title) {
        this.title = title;
    }

    public ArrayList<TextData> getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(ArrayList<TextData> subtitle) {
        this.subtitle = subtitle;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

}
