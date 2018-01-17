package sdk.dive.tv.ui.modules.data;


import com.touchvie.sdk.model.ImageData;

import java.io.Serializable;

/**
 * Created by Noemi on 31/10/2016.
 */

public class CastLocationRowData implements Serializable {


    private ImageData image;
    private String realItem;
    private String fictionalItem;
    private String realItemCardId;
    private String realItemCardVersion;
    private boolean realItemHasContent;
    private String fictionalItemCardId;
    private String fictionalItemType;
    private boolean fictionalItemHasContent;
    private String realItemType;
    private String relation;

    public ImageData getImage() {
        return image;
    }

    public void setImage(ImageData image) {
        this.image = image;
    }

    public String getRealItem() {
        return realItem;
    }

    public void setRealItem(String realItem) {
        this.realItem = realItem;
    }

    public String getFictionalItem() {
        return fictionalItem;
    }

    public void setFictionalItem(String fictionalItem) {
        this.fictionalItem = fictionalItem;
    }

    public String getRealItemCardId() {
        return realItemCardId;
    }

    public void setRealItemCardId(String realItemCardId) {
        this.realItemCardId = realItemCardId;
    }

    public String getRealItemCardVersion() {
        return realItemCardVersion;
    }

    public void setRealItemCardVersion(String realItemCardVersion) {
        this.realItemCardVersion = realItemCardVersion;
    }

    public boolean isRealItemHasContent() {
        return realItemHasContent;
    }

    public void setRealItemHasContent(boolean realItemHasContent) {
        this.realItemHasContent = realItemHasContent;
    }

    public String getFictionalItemCardId() {
        return fictionalItemCardId;
    }

    public void setFictionalItemCardId(String fictionalItemCardId) {
        this.fictionalItemCardId = fictionalItemCardId;
    }

    public String getFictionalItemType() {
        return fictionalItemType;
    }

    public void setFictionalItemType(String fictionalItemType) {
        this.fictionalItemType = fictionalItemType;
    }

    public String getRealItemType() {
        return realItemType;
    }

    public void setRealItemType(String realItemType) {
        this.realItemType = realItemType;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public boolean isFictionalItemHasContent() {
        return fictionalItemHasContent;
    }

    public void setFictionalItemHasContent(boolean fictionalItemHasContent) {
        this.fictionalItemHasContent = fictionalItemHasContent;
    }
}
