package sdk.dive.tv.eventbus;

/**
 * Created by Tagsonomy S.L. on 02/12/2015.
 */
public class OpenCard {
    /**
     * The Id.
     */
    private final String id;
    private final String version;
    private final String cardId;
    private final String type;
    private String title;
    private String image;
    private boolean isMovie ;
    private String channelId;
    private String channelName;

    private int sceneNr;

    /**
     * Instantiates a new Refresh event.
     *
     * @param id the id
     */
    public OpenCard(String id, String cardId, String version, String type) {
        this.id = id;
        this.cardId=cardId;
        this.version = version;
        this.type = type;
        this.sceneNr = -1;
    }

    /**
     * Gets .
     *
     * @return the
     */
    public String getid() {
        return id;
    }

    public String getCardId() {
        return cardId;
    }

    public String getCardVersion() {
        return version;
    }

    public String getType() {
        return type;
    }

    public int getSceneNr(){
        return sceneNr;
    }

    public void setSceneNr(int sceneNr){
        this.sceneNr = sceneNr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isMovie() {
        return isMovie;
    }

    public void setIsMovie(boolean isMovie) {
        this.isMovie = isMovie;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
