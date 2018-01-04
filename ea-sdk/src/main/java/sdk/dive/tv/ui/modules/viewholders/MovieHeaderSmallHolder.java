package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.CardContainer;
import com.touchvie.sdk.model.Catalog;
import com.touchvie.sdk.model.CatalogData;
import com.touchvie.sdk.model.RelationModule;
import com.touchvie.sdk.model.Single;

import sdk.dive.tv.R;
import sdk.dive.tv.eventbus.EventBusManager;
import sdk.dive.tv.eventbus.EventBusIds;
import sdk.dive.tv.eventbus.OpenCard;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.other.CustomTypefaceSpan;

import static com.touchvie.sdk.model.CardContainer.TypeEnum.CATALOG;
import static com.touchvie.sdk.model.Catalog.ContentTypeEnum.CHAPTER;
import static com.touchvie.sdk.model.Catalog.ContentTypeEnum.MOVIE;
import static com.touchvie.sdk.model.Catalog.ContentTypeEnum.SERIE;
import static com.touchvie.sdk.model.Single.ContentTypeEnum.DIRECTORS;

/**
 * Created by Tagsonomy S.L. on 05/10/2016.
 */

public class MovieHeaderSmallHolder extends TvModuleHolder {

    protected ImageView mPoster;
    protected TextView mTitle;
    protected TextView mYear;
    protected TextView mDirector;
    protected TextView mGenres;
    protected LinearLayout mContainer;

    /**
     * Default constructor
     *
     * @param itemView
     * @param simpleName
     */
    public MovieHeaderSmallHolder(View itemView, String simpleName) {
        super(itemView);
        this.moduleName = simpleName;
        mPoster = (ImageView) itemView.findViewById(R.id.imgv_mheaders_poster);
        mTitle = (TextView) itemView.findViewById(R.id.txtv_title);
        mYear = (TextView) itemView.findViewById(R.id.txtv_year);
        mDirector = (TextView) itemView.findViewById(R.id.txtv_director);
        mGenres = (TextView) itemView.findViewById(R.id.tvtv_genres);
        mContainer = (LinearLayout) itemView.findViewById(R.id.llay_container);
    }

    @Override
    public void configure(final Card cardData, Picasso picasso, Context context, final TvCardDetailListener tvCardDetailListener, SectionListener sectionListener) {

        MovieHeaderHolder.TypeOfHeader headerType = MovieHeaderHolder.TypeOfHeader.MOVIE;

        CardContainer cont = null;
        if (Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), MOVIE.getValue()) != null) {
            cont = Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), MOVIE.getValue());
            headerType = MovieHeaderHolder.TypeOfHeader.MOVIE;
        } else if (Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), SERIE.getValue()) != null) {
            cont = Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), SERIE.getValue());
            headerType = MovieHeaderHolder.TypeOfHeader.SERIE;
        } else if (Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), CHAPTER.getValue()) != null) {
            cont = Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), CHAPTER.getValue());
            headerType = MovieHeaderHolder.TypeOfHeader.CHAPTER;
        }

        if (cardData.getImage() != null && cardData.getImage().getThumb() != null) {
            picasso.load(cardData.getImage().getThumb())
                    .into(mPoster);
        } else {
            mPoster.setVisibility(View.GONE);
        }

        if (cardData.getTitle() != null && headerType != MovieHeaderHolder.TypeOfHeader.CHAPTER) {
            mTitle.setText(cardData.getTitle());
            mTitle.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD));
        }

        if (cont != null) {
            if (CATALOG.getValue().equals(cont.getType())) {
                Catalog container = (Catalog) cont;
                if (container.getData() != null && container.getData().size() >= 1) {
                    CatalogData containerCatalog = container.getData().get(0);
                    if (containerCatalog == null)
                        return;

                    if (cardData.getTitle() != null && headerType == MovieHeaderHolder.TypeOfHeader.CHAPTER) {
                        String chapterseason = Utils.getChapterAndSeason(containerCatalog.getChapterIndex(), containerCatalog.getSeasonIndex());
                        SpannableString spannableString = new SpannableString(cardData.getTitle() + " " + chapterseason);
                        Typeface latoLight = Utils.getFont(context, Utils.TypeFaces.LATO_LIGHT);
                        spannableString.setSpan(new CustomTypefaceSpan(latoLight), cardData.getTitle().length(), spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        mTitle.setText(spannableString);
                    }

                    RelationModule relation = Utils.getRelation(cardData, DIRECTORS.getValue());
                    if (relation != null && ((Single) relation).getData() != null && ((Single) relation).getData().size() > 0) {
                        String directorNames = "";
                        for (int i = 0; i < ((Single) relation).getData().size(); i++) {
                            directorNames = directorNames + ((Single) relation).getData().toArray(new Card[((Single) relation).getData().size()])[i].getTitle();
                            if (i < (((Single) relation).getData().size()) - 1) {
                                directorNames += ", ";
                            }
                        }

                        SpannableString spannableString = new SpannableString(directorNames);

                        for (Card relData : ((Single) relation).getData()) {
                            spannableString.setSpan(new sdk.dive.tv.ui.modules.viewholders.MovieHeaderSmallHolder.ClickableDirector(relData.getCardId(), relData.getType().getValue()), spannableString.toString().indexOf(relData.getTitle()),
                                    spannableString.toString().indexOf(relData.getTitle()) + relData.getTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }

                        mDirector.setText(spannableString);
                        if (Build.VERSION.SDK_INT < 23) {
                            mDirector.setTextAppearance(context, R.style.MinicardTitleLink);
                        } else {
                            mDirector.setTextAppearance(R.style.MinicardTitleLink);
                        }
                        mDirector.setMovementMethod(LinkMovementMethod.getInstance());
                        mDirector.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD));
                    } else if (containerCatalog.getDirector() != null) {
                        mDirector.setText(containerCatalog.getDirector());
                        if (Build.VERSION.SDK_INT < 23) {
                            mDirector.setTextAppearance(context, R.style.CardMovieTime);
                        } else {
                            mDirector.setTextAppearance(R.style.CardMovieTime);
                        }
                        mDirector.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD));
                    }

                    if (containerCatalog.getGenres() != null) {
                        StringBuilder sb = new StringBuilder();
                        boolean first = true;
                        for (String genre : containerCatalog.getGenres()) {
                            String mGenre = Utils.getGenre(genre, context);
                            if (!first) {
                                sb.append(", ");
                            }
                            first = false;
                            sb.append(mGenre);
                        }
                        mGenres.setText(sb.toString());
                        mGenres.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR));
                    }
                    if (containerCatalog.getYear() > 0 && headerType != MovieHeaderHolder.TypeOfHeader.CHAPTER) {
                        mYear.setText(String.valueOf(containerCatalog.getYear()));
                        mYear.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_LIGHT));
                    }
                }
            }
        }
    }

    private class ClickableDirector extends ClickableSpan {
        String id;
        String type;

        ClickableDirector(String id, String type) {
            super();
            this.id = id;
            this.type = type;
        }

        public void onClick(View tv) {
            OpenCard openCard = new OpenCard(EventBusIds.OPEN_CARD.getName(), id, type);
            EventBusManager.getInstance().post(openCard);
        }

        public void updateDrawState(TextPaint ds) {// override updateDrawState
            ds.setUnderlineText(false); // set to false to remove underline
        }
    }

}
