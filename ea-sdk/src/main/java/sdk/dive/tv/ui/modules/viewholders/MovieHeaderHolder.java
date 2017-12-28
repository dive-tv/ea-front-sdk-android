package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.CardContainer;
import com.touchvie.sdk.model.Catalog;
import com.touchvie.sdk.model.CatalogData;
import com.touchvie.sdk.model.RelationModule;
import com.touchvie.sdk.model.Single;

import java.util.ArrayList;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.other.CustomTypefaceSpan;

import static com.touchvie.sdk.model.Catalog.ContentTypeEnum.CHAPTER;
import static com.touchvie.sdk.model.Catalog.ContentTypeEnum.MOVIE;
import static com.touchvie.sdk.model.Catalog.ContentTypeEnum.SERIE;
import static com.touchvie.sdk.model.Single.ContentTypeEnum.DIRECTORS;

/**
 * Created by Tagsonomy S.L. on 05/10/2016.
 */

public class MovieHeaderHolder extends TvModuleHolder {

    protected RelativeLayout container;
    protected TextView title;
    protected TextView director;
    private TextView genre;
    private LinearLayout timeLay;
    protected TextView time;
    protected TextView type;
    private FrameLayout imageBorder;
    protected ImageView image;
    private ImageView noimage;
    private LinearLayout mLikeContainer;
    private TextView mLikeTxt;
    private ImageView mLikeIcon;

    enum TypeOfHeader {MOVIE, SERIE, CHAPTER}


    /**
     * Default constructor
     *
     * @param itemView
     * @param simpleName
     */
    public MovieHeaderHolder(View itemView, String simpleName) {
        super(itemView);
        this.moduleName = simpleName;
        container = (RelativeLayout) itemView.findViewById(R.id.tv_module_movie_header_container_full);
        title = (TextView) itemView.findViewById(R.id.tv_module_movie_header_info_title);
        director = (TextView) itemView.findViewById(R.id.tv_module_movie_header_info_director);
        genre = (TextView) itemView.findViewById(R.id.tv_module_movie_header_info_genre);
        timeLay = (LinearLayout) itemView.findViewById(R.id.tv_module_movie_header_time_container);
        time = (TextView) itemView.findViewById(R.id.tv_module_movie_header_info_time);
        type = (TextView) itemView.findViewById(R.id.tv_module_movie_header_type);
        imageBorder = (FrameLayout) itemView.findViewById(R.id.tv_module_movie_header_image_border);
        image = (ImageView) itemView.findViewById(R.id.tv_module_movie_header_image);
        noimage = (ImageView) itemView.findViewById(R.id.tv_module_movie_header_noimage);
    }

    @Override
    public void configure(final Card cardData, final Picasso picasso, final Context context, final TvCardDetailListener tvCardDetailListener, SectionListener sectionListener) {

        TypeOfHeader headerType = TypeOfHeader.MOVIE;

        if (cardData.getImage() != null && cardData.getImage().getThumb() != null) {
            noimage.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            image.post(new Runnable() {
                @Override
                public void run() {
                    picasso.load(cardData.getImage().getThumb())
                            .priority(Picasso.Priority.HIGH)
                            .into(image);
                }
            });
        } else {
            noimage.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
        }

        CardContainer cont = null;
        if (Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), MOVIE.getValue()) != null) {
            cont = Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), MOVIE.getValue());
            headerType = TypeOfHeader.MOVIE;
        } else if (Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), SERIE.getValue()) != null) {
            cont = Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), SERIE.getValue());
            headerType = TypeOfHeader.SERIE;
        } else if (Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), CHAPTER.getValue()) != null) {
            cont = Utils.getContainer(cardData.getInfo().toArray(new CardContainer[cardData.getInfo().size()]), CHAPTER.getValue());
            headerType = TypeOfHeader.CHAPTER;
        }
        if (cardData.getTitle() != null) {
            if (headerType != TypeOfHeader.CHAPTER) {
                title.setText(cardData.getTitle());
                title.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_BOLD));
            }
        }

        if (cardData.getType() != null) {
            type.setText(sdk.dive.tv.ui.Utils.getType(cardData.getType().getValue(), context));
        } else {
            type.setText("");
        }

        if (cont == null) {
            time.setVisibility(View.GONE);
        } else {
            Catalog container = (Catalog) cont;
            if (container.getData() != null && container.getData().size() >= 1) {

                final CatalogData containerCatalog = container.getData().get(0);
                if (containerCatalog == null)
                    return;

                if (cardData.getTitle() != null) {
                    if (headerType == TypeOfHeader.CHAPTER) {
                        String chapterseason = Utils.getChapterAndSeason(containerCatalog.getChapterIndex(), containerCatalog.getSeasonIndex());
                        SpannableString spannableString = new SpannableString(cardData.getTitle() + " " + chapterseason);
                        Typeface latoLight = Utils.getFont(context, Utils.TypeFaces.LATO_LIGHT);
                        spannableString.setSpan(new CustomTypefaceSpan(latoLight), cardData.getTitle().length(), spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        title.setText(spannableString);
                    } else {
                        if (containerCatalog.getYear() > 0) {
                            String titleYear = cardData.getTitle() + " (" + String.valueOf(containerCatalog.getYear() + ")");
                            title.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_LIGHT));
                            SpannableString spannableString = new SpannableString(titleYear);
                            spannableString.setSpan(new CustomTypefaceSpan(Utils.getFont(context, Utils.TypeFaces.LATO_BOLD)),
                                    spannableString.toString().indexOf(cardData.getTitle()),
                                    spannableString.toString().indexOf(cardData.getTitle()) + cardData.getTitle().length(),
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            title.setText(spannableString);
                        } else {
                            title.setText(cardData.getTitle());
                            title.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_BOLD));
                        }

                    }
                } else {
                    title.setText("");
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

                    director.setText(spannableString);
                    director.setMovementMethod(LinkMovementMethod.getInstance());
                    director.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD));
                } else if (containerCatalog.getDirector() != null) {
                    director.setText(containerCatalog.getDirector());
                    director.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD));
                }

                if (containerCatalog.getRuntime() > 0) {
                    timeLay.setVisibility(View.VISIBLE);
                    time.setText(Utils.getTimeFromMinutes(containerCatalog.getRuntime(), context));
                    time.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR));
                } else {
                    timeLay.setVisibility(View.GONE);
                }

                if (containerCatalog.getSync() != null && containerCatalog.getSync().getIsSynchronizable() && headerType != TypeOfHeader.SERIE) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        image.setElevation(context.getResources().getDimension(R.dimen.mheader_elevation));
                    }
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
                    genre.setText(sb.toString());
                    genre.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR));
                }
            }
        }
    }
}
