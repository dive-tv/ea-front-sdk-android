package sdk.dive.tv.eventbus;

/**
 * Created by Tagsonomy S.L. on 15/11/2016.
 */

public class OpenWeb {

    private final String id;
    private boolean openInChrome;
    private final String url;

    /**
     * Instantiates a new Refresh event.
     *
     * @param id the id
     */
    public OpenWeb(String id, String url) {
        this.id = id;
        this.url=url;
        this.openInChrome = false;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public boolean openInChrome() {
        return openInChrome;
    }

    public void setOpenInChrome(boolean openInChrome) {
        this.openInChrome = openInChrome;
    }
}
