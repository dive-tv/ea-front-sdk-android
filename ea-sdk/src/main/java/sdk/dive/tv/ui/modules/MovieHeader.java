package sdk.dive.tv.ui.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.CardContainer;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.modules.viewholders.MovieHeaderHolder;
import sdk.dive.tv.ui.views.Module;

import static com.touchvie.sdk.model.Catalog.ContentTypeEnum.CHAPTER;
import static com.touchvie.sdk.model.Catalog.ContentTypeEnum.MOVIE;
import static com.touchvie.sdk.model.Catalog.ContentTypeEnum.SERIE;

/**
 * Created by Tagsonomy S.L. on 04/10/2016.
 */

public class MovieHeader extends Module {
    /**
     * Default constructor
     */
    public MovieHeader() {
        super();
        setLayout(R.layout.tv_module_movie_header);
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(this.layout, group, false);
        MovieHeaderHolder holder = new MovieHeaderHolder(viewGroup, getClass().getSimpleName());
        return holder;
    }

    @Override
    public boolean validate(Card cardData, boolean isCarousel) {
        CardContainer cont = null;
        if (getContainer(cardData, MOVIE.getValue()) != null)
            cont = getContainer(cardData, MOVIE.getValue());
        else if (getContainer(cardData, SERIE.getValue()) != null) {
            cont = getContainer(cardData, SERIE.getValue());
        } else if (getContainer(cardData, CHAPTER.getValue()) != null) {
            cont = getContainer(cardData, CHAPTER.getValue());
        }

        if (cont == null || isCarousel) {
            return false;
        }

        return true;
    }
}
