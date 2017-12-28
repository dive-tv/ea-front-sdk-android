package sdk.dive.tv.ui.modules.data;


import com.touchvie.sdk.model.SeasonsChapters;

import java.util.ArrayList;

/**
 * Created by Noemi on 30/10/2016.
 */

public class SeasonRowData {

    private String image;
    private int year;
    private String season;
    private String seasonNr;
    private ArrayList<SeasonsChapters> chapters;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public ArrayList<SeasonsChapters> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<SeasonsChapters> chapters) {
        this.chapters = chapters;
    }

    public String getSeasonNr() {
        return seasonNr;
    }

    public void setSeasonNr(String seasonNr) {
        this.seasonNr = seasonNr;
    }
}
