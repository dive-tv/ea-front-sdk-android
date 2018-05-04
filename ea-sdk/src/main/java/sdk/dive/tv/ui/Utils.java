package sdk.dive.tv.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.touchvie.sdk.model.Awards;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.CardContainer;
import com.touchvie.sdk.model.Catalog;
import com.touchvie.sdk.model.Duple;
import com.touchvie.sdk.model.DupleData;
import com.touchvie.sdk.model.Image;
import com.touchvie.sdk.model.ImageData;
import com.touchvie.sdk.model.Link;
import com.touchvie.sdk.model.Listing;
import com.touchvie.sdk.model.Rating;
import com.touchvie.sdk.model.Map;
import com.touchvie.sdk.model.RelationModule;
import com.touchvie.sdk.model.Seasons;
import com.touchvie.sdk.model.Single;
import com.touchvie.sdk.model.Text;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.datawrappers.CardTypes;
import sdk.dive.tv.ui.datawrappers.GenreTypes;
import sdk.dive.tv.ui.relations.LocalContentTypes;
import sdk.dive.tv.ui.relations.RelationsBuilder;
import sdk.dive.tv.ui.relations.RelationsToProcess;

/**
 * Created by Tagsonomy S.L. on 26/05/2017.
 */

public class Utils {

    private static Typeface latoRegular;
    private static Typeface latoSemibold;
    private static Typeface latoBold;
    private static Typeface latoBlack;
    private static Typeface latoMedium;
    private static Typeface zonaProRegular;
    private static Typeface zonaProBold;
    private static Typeface zonaProExtraBold;
    private static Typeface zonaProSemiBold;



    public final static int PUSH = 1;
    public final static int SEND_CARDLISTENER = 2;
    public final static int GO_TO_END_MOVIE = 3;
    public final static int GO_TO_OFF_MOVIE = 4;
    public final static int GO_TO_READY_MOVIE = 5;
    public final static int GO_TO_PLAYING_MOVIE = 6;
    public final static int GO_TO_PAUSE_MOVIE = 7;
    public final static int MOVIE_SCENE_NR_RECEIVED = 8;
    public final static int UPDATE_CURRENT_TIME = 9;
    public final static int SEND_FEEDBACK = 10;
    public final static int SHOW_CAROUSEL_ERROR = 11;
    public final static int SEND_MENU_CARDS = 12;
    public final static int SEND_INITIAL_FEEDBACK = 13;
    public final static int SEND_EMPTY_SCENE = 14;
    public final static int MSG_CHECKED_CATEGORY = 15;
    public final static int SCENE_START_TIME = 16;
    public final static int CLOSE_CAROUSEL_ACTIVITY = 17;
    public final static int GO_TO_NEW_SCENE = 18;

    public final static int MSG_PUSH_CARD = 102;
    public final static int MSG_PAINT_CAROUSELCARDS = 103;
    public final static int MSG_PRELOAD_CAROUSELCARDS = 104;
    public final static int MSG_PAUSE_RECEIVED = 105;
    public final static int MSG_READY_RECEIVED = 106;
    public final static int MSG_OFF_RECEIVED = 107;
    public final static int MSG_END_RECEIVED = 108;
    public final static int MSG_PLAYING_RECEIVED = 109;
    public final static int SEND_HANDLER = 110;
    public final static int CHUNK_RECEIVED = 111;
    public final static int MSG_UPDATE_CURRENT_TIME = 112;
    public final static int MSG_SEND_FEEDBACK = 113;
    public final static int SHOW_ERROR = 114;
    public final static int MSG_MENU_CARDS = 115;
    public final static int INITIAL_FEEDBACK = 116;
    public final static int EMPTY_SCENE = 117;
    public final static int CHECKED_CATEGORY = 118;
    public final static int ALL_READY = 119;
    public final static int GET_SCENE = 1110;
    public final static int GET_SCENE_START_TIME = 1111;
    public final static int MSG_CLOSE_CAROUSEL_ACTIVITY = 227;

    public static final String CARD_DATA = "CARD_DATA";
    public static final String CONFIG_SECTION = "CONFIG_MODULES";
    public static final String SECTION_TYPE = "SECTION_TYPE";
    public static final String CARD_DETAIL_LISTENER = "CARD_DETAIL_LISTENER";
    public static final String IS_CAROUSEL="IS_CAROUSEL";


    public final static String MANAGER = "manager";
    public final static String API_KEY = "api_key";
    public final static String DEVICE_ID = "device_id";
    public final static String MOVIE_ID = "movie_id";
    public final static String MOVIE_TIMESTAMP = "movie_timestamp";
    public final static String STYLE = "style";
    public final static String IS_MOVIE = "is_movie";
    public final static String CHANNEL_ID = "channel_id";
    public final static String PREVIOUS_SCREEN = "previous_screen";
    public final static String MOVIE_NAME = "movie_name";
    public final static String SDK_CLIENT = "sdk_client";

    public final static String CAROUSEL_CARD = "carousel_card";

    public final static String TV_MODE = "tv_mode";

    public final static String URL = "url";


    public enum FragmentNames {
        FRAGMENT_ERROR, CAROUSEL, DIVE,
        SEE_MORE_RELATIONS, CARD_DETAIL, PRODUCT
    }

    public static enum TypeFaces {LATO_REGULAR, LATO_SEMIBOLD, LATO_BLACK, LATO_BOLD, LATO_LIGHT, ZONAPRO, ZONAPRO_REGULAR, ZONAPRO_BOLD, ZONAPRO_SEMIBOLD, ZONAPRO_EXTRABOLD, LATO_HEAVY, LATO_MEDIUM}

    public static String ERROR_TYPE = "error_type";
    public static final int NETWORK_ERROR = 0;
    public static final int TEMPORARY_REDIRECT = 1;
    public static final int PERMANENT_REDIRECT = 2;
    public static final int GENERIC_ERROR = 3;
    public static final int RE_SCAN_CHANNELS = 4;


    /**
     * Constructs the font of a given Typeface from a fonts file.
     *
     * @param context the context
     * @param font    the font
     * @return the font
     * @returns {Typeface} type. The constructed font type.
     */
    public static Typeface getFont(Context context, TypeFaces font) {

        Typeface type;
        switch (font) {
            case LATO_REGULAR:
                if (latoRegular == null)
                    latoRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Regular.ttf");
                type = latoRegular;
                break;
            case LATO_SEMIBOLD:
                if (latoSemibold == null)
                    latoSemibold = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Semibold.ttf");
                type = latoSemibold;
                break;
            case LATO_BOLD:
                if (latoBold == null)
                    latoBold = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf");
                type = latoBold;
                break;
            case LATO_BLACK:
                if (latoBlack == null)
                    latoBlack = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Black.ttf");
                type = latoBlack;
                break;
            case LATO_MEDIUM:
                if (latoMedium == null)
                    latoMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Medium.ttf");
                type = latoMedium;
                break;
            case LATO_LIGHT:
                if (latoBold == null)
                    latoBold = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Light.ttf");
                type = latoBold;
                break;
            case LATO_HEAVY:
                if (latoBold == null)
                    latoBold = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Heavy.ttf");
                type = latoBold;
                break;
            case ZONAPRO:
            case ZONAPRO_REGULAR:
                if (zonaProRegular == null)
                    zonaProRegular = Typeface.createFromAsset(context.getAssets(), "fonts/ZonaPro-Regular.otf");
                type = zonaProRegular;
                break;
            case ZONAPRO_BOLD:
                if (zonaProBold == null)
                    zonaProBold = Typeface.createFromAsset(context.getAssets(), "fonts/ZonaPro-Bold.otf");
                type = zonaProBold;
                break;
            case ZONAPRO_SEMIBOLD:
                if (zonaProSemiBold == null)
                    zonaProSemiBold = Typeface.createFromAsset(context.getAssets(), "fonts/ZonaPro-SemiBold.otf");
                type = zonaProSemiBold;
                break;
            case ZONAPRO_EXTRABOLD:
                if (zonaProExtraBold == null)
                    zonaProExtraBold = Typeface.createFromAsset(context.getAssets(), "fonts/ZonaPro-ExtraBold.otf");
                type = zonaProExtraBold;
                break;
            default:
                if (latoRegular == null)
                    latoRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Regular.ttf");
                type = latoRegular;
        }
        return type;
    }

    public static JSONArray getCardDetailStyleconfiguration(Context context) {
        String jsonString = null;

        int configFile = R.raw.carousel_styles_sdk;

        try {
            Resources res = context.getResources();
            InputStream in_s = res.openRawResource(configFile);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            jsonString = new String(b);
        } catch (Exception e) {
            return null;
        }

        JSONArray configJson = null;
        try {
            configJson = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return configJson;

    }

    public static StateListDrawable makeSelector(int colorFocused, int colorUnfocused) {
        StateListDrawable res = new StateListDrawable();
        res.addState(new int[]{android.R.attr.state_pressed}, makeShape(colorFocused,2));
        res.addState(new int[]{android.R.attr.state_focused}, makeShape(colorFocused,2));
        res.addState(new int[]{}, makeShape(colorUnfocused,1));
        return res;
    }

    public static GradientDrawable makeShape(int borderColor, int width) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
//        shape.setCornerRadii(new float[] { 8, 8, 8, 8, 0, 0, 0, 0 });
        shape.setColor(Color.parseColor("#00000000"));
        shape.setStroke(3, borderColor);
        return shape;
    }



    /**
     * Returns a color.
     *
     * @param context the context
     * @param color   the color
     * @return the color using different methods depending on the API level
     */
    public static int getColor(Context context, int color) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, color);
        } else {
            return context.getResources().getColor(color);
        }
    }

    public static float getDimension(Context context, int dimension) {

        return context.getResources().getDimension(dimension);
    }

    /**
     * Convert time in milliseconds to a HH:MM format
     *
     * @param millis
     * @return
     */
    public static String convertMillisToHHMM(long millis) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        df.setTimeZone(TimeZone.getDefault());
        return df.format(millis);
    }

    public static String getType(String type, Context context) {
        String typeString = "";
        switch (type.toUpperCase()) {
            case CardTypes.ACTION_EMOTION:
                typeString = context.getResources().getString(R.string.ACTION_EMOTION);
                break;
            case CardTypes.ART:
                typeString = context.getResources().getString(R.string.ART);
                break;
            case CardTypes.BUSINESS:
                typeString = context.getResources().getString(R.string.BUSINESS);
                break;
            case CardTypes.CHAPTER:
                typeString = context.getResources().getString(R.string.CHAPTER);
                break;
            case CardTypes.CHARACTER:
                typeString = context.getResources().getString(R.string.CHARACTER);
                break;
            case CardTypes.FASHION:
                typeString = context.getResources().getString(R.string.FASHION);
                break;
            case CardTypes.FAUNA_FLORA:
                typeString = context.getResources().getString(R.string.FAUNA_FLORA);
                break;
            case CardTypes.FOOD_DRINK:
                typeString = context.getResources().getString(R.string.FOOD_DRINK);
                break;
            case CardTypes.HEALTH_BEAUTY:
                typeString = context.getResources().getString(R.string.HEALTH_BEAUTY);
                break;
            case CardTypes.HISTORIC:
                typeString = context.getResources().getString(R.string.HISTORIC);
                break;
            case CardTypes.HOME:
                typeString = context.getResources().getString(R.string.HOME);
                break;
            case CardTypes.LEISURE_SPORT:
                typeString = context.getResources().getString(R.string.LEISURE_SPORT);
                break;
            case CardTypes.LOCATION:
                typeString = context.getResources().getString(R.string.LOCATION);
                break;
            case CardTypes.LOOK:
                typeString = context.getResources().getString(R.string.LOOK);
                break;
            case CardTypes.MOVIE:
                typeString = context.getResources().getString(R.string.MOVIE);
                break;
            case CardTypes.OST:
                typeString = context.getResources().getString(R.string.OST);
                break;
            case CardTypes.PERSON:
                typeString = context.getResources().getString(R.string.PERSON);
                break;
            case CardTypes.QUOTE:
                typeString = context.getResources().getString(R.string.QUOTE);
                break;
            case CardTypes.REFERENCE:
                typeString = context.getResources().getString(R.string.REFERENCE);
                break;
            case CardTypes.SERIE:
                typeString = context.getResources().getString(R.string.SERIE);
                break;
            case CardTypes.SONG:
                typeString = context.getResources().getString(R.string.SONG);
                break;
            case CardTypes.TECHNOLOGY:
                typeString = context.getResources().getString(R.string.TECHNOLOGY);
                break;
            case CardTypes.TRAILER:
                typeString = context.getResources().getString(R.string.TRAILER);
                break;
            case CardTypes.TRIVIA:
                typeString = context.getResources().getString(R.string.TRIVIA);
                break;
            case CardTypes.VEHICLE:
                typeString = context.getResources().getString(R.string.VEHICLE);
                break;
            case CardTypes.VIDEOCLIP:
                typeString = context.getResources().getString(R.string.VIDEOCLIP);
                break;
            case CardTypes.WEAPON:
                typeString = context.getResources().getString(R.string.WEAPON);
                break;
            default:
                break;
        }

        return typeString;
    }


    public static String getGenre(String genre, Context context) {
        String genreString = "";
        switch (genre) {
            case GenreTypes.ACTION:
                genreString = context.getResources().getString(R.string.ACTION);
                break;
            case GenreTypes.ADVENTURE:
                genreString = context.getResources().getString(R.string.ADVENTURE);
                break;
            case GenreTypes.ANIMATION:
                genreString = context.getResources().getString(R.string.ANIMATION);
                break;
            case GenreTypes.BIOGRAPHY:
                genreString = context.getResources().getString(R.string.BIOGRAPHY);
                break;
            case GenreTypes.COMEDY:
                genreString = context.getResources().getString(R.string.COMEDY);
                break;
            case GenreTypes.CRIME:
                genreString = context.getResources().getString(R.string.CRIME);
                break;
            case GenreTypes.DOCUMENTARY:
                genreString = context.getResources().getString(R.string.DOCUMENTARY);
                break;
            case GenreTypes.DRAMA:
                genreString = context.getResources().getString(R.string.DRAMA);
                break;
            case GenreTypes.FAMILY:
                genreString = context.getResources().getString(R.string.FAMILY);
                break;
            case GenreTypes.FANTASY:
                genreString = context.getResources().getString(R.string.FANTASY);
                break;
            case GenreTypes.FILM_NOIR:
                genreString = context.getResources().getString(R.string.FILM_NOIR);
                break;
            case GenreTypes.HISTORY:
                genreString = context.getResources().getString(R.string.HISTORY);
                break;
            case GenreTypes.KIDS:
                genreString = context.getResources().getString(R.string.KIDS);
                break;
            case GenreTypes.MISTERY:
                genreString = context.getResources().getString(R.string.MISTERY);
                break;
            case GenreTypes.MUSIC:
                genreString = context.getResources().getString(R.string.MUSIC);
                break;
            case GenreTypes.MUSICAL:
                genreString = context.getResources().getString(R.string.MUSICAL);
                break;
            case GenreTypes.NEWS:
                genreString = context.getResources().getString(R.string.NEWS);
                break;
            case GenreTypes.ROMANCE:
                genreString = context.getResources().getString(R.string.ROMANCE);
                break;
            case GenreTypes.SCI_FI:
                genreString = context.getResources().getString(R.string.SCI_FI);
                break;
            case GenreTypes.SPORT:
                genreString = context.getResources().getString(R.string.SPORT);
                break;
            case GenreTypes.TALK_SHOW:
                genreString = context.getResources().getString(R.string.TALK_SHOW);
                break;
            case GenreTypes.TERROR:
                genreString = context.getResources().getString(R.string.TERROR);
                break;
            case GenreTypes.THRILLER:
                genreString = context.getResources().getString(R.string.THRILLER);
                break;
            case GenreTypes.WAR:
                genreString = context.getResources().getString(R.string.WAR);
                break;
            case GenreTypes.WESTERN:
                genreString = context.getResources().getString(R.string.WESTERN);
                break;
            default:
                break;
        }
        return genreString;
    }

    public static String convertSecondstoHHMMSS(long seconds) {
        return String.format("%02d:%02d:%02d", TimeUnit.SECONDS.toHours(seconds),
                TimeUnit.SECONDS.toMinutes(seconds) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.SECONDS.toSeconds(seconds) % TimeUnit.MINUTES.toSeconds(1));
    }

    public static String getChapterAndSeason(int chapter, int season){
        StringBuilder sb= new StringBuilder();

        switch (season){
            case 1:
                sb.append("S01");
                break;
            case 2:
                sb.append("S02");
                break;
            case 3:
                sb.append("S03");
                break;
            case 4:
                sb.append("S04");
                break;
            case 5:
                sb.append("S05");
                break;
            case 6:
                sb.append("S06");
                break;
            case 7:
                sb.append("S07");
                break;
            case 8:
                sb.append("S08");
                break;
            case 9:
                sb.append("S09");
                break;
            default:
                sb.append("S");
                sb.append(Integer.toString(season));
                break;
        }

        switch (chapter){
            case 1:
                sb.append("E01");
                break;
            case 2:
                sb.append("E02");
                break;
            case 3:
                sb.append("E03");
                break;
            case 4:
                sb.append("E04");
                break;
            case 5:
                sb.append("E05");
                break;
            case 6:
                sb.append("E06");
                break;
            case 7:
                sb.append("E07");
                break;
            case 8:
                sb.append("E08");
                break;
            case 9:
                sb.append("E09");
                break;
            default:
                sb.append("E");
                sb.append(Integer.toString(chapter));
                break;
        }


        return sb.toString();
    }

    public ArrayList<Card> TransformCarouselCards(ArrayList<Card> cards){
        ArrayList<Card> carouselCards = new ArrayList<>();
        for (Card card : cards) {

            Card carouselCard = new Card();
            String cardId = card.getCardId();
            carouselCard.setCardId(cardId);

            ImageData image = new ImageData();
            image.setThumb(card.getImage().getThumb());
            image.setAnchorX(card.getImage().getAnchorX());
            image.setAnchorY(card.getImage().getAnchorY());
            carouselCard.setImage(image);
            if (card.getRelations() != null && card.getRelations().size() > 0) { //Warning , relations must be set after setting carousel card minicard, because there are relations that need that minicard,and also to distinguish between card types.
                RelationsBuilder.setRelations(carouselCard, card.getRelations().toArray(new RelationModule[card.getRelations().size()]));
            }
            carouselCards.add(carouselCard);
        }


        return carouselCards;
    }

    public static RelationModule getRelation(Card card, String contentType) {
        HashMap<String, RelationModule> contentTypeRelations = null;


        if (card.getRelations() == null || card.getRelations().size() == 0) {
            return null;
        }
        if (contentTypeRelations == null) {
            contentTypeRelations = new HashMap<>();
            for (RelationModule relation : card.getRelations()) {
                if (contentTypeRelations.get(relation.getType()) == null) {
                    contentTypeRelations.put(relation.getType().equals(RelationModule.TypeEnum.SINGLE)? ((Single)relation).getContentType().getValue():((Duple)relation).getContentType().name(), relation);
                }
            }
        }

        return contentTypeRelations.get(contentType.toUpperCase());
    }

    public static List<RelationModule> getRels(Card card, boolean onlyRelationsToShowInCarousel) {

        // initialize the list of relation modules
        List<RelationModule> result = new ArrayList();
        // check if card has relations
        if (card.getRelations() == null || (card.getRelations() != null && card.getRelations().size() == 0))
            return result;

        // check if exist relations to process at card
        List<RelationsToProcess> relationsToProcess;
        if(onlyRelationsToShowInCarousel)
            relationsToProcess = RelationsToProcess.fromCardTypeToShowInCarousel(card.getType());
        else
            relationsToProcess = RelationsToProcess.fromCardType(card.getType());

        if (relationsToProcess.size() == 0)
            return result;

        for (RelationModule relation : card.getRelations()) {
            for (RelationsToProcess relationToProcess : relationsToProcess) {
                // check if relation is an instance of Duple Class and relationToProcess is of Duple type
                if (relation instanceof Duple && relationToProcess.getRelationModuleType() == Duple.class) {
                    Duple duple = ((Duple) relation);
                    // check if contentType of Duple relation is equals to relationToProcess contentType
                    if (duple.getContentType() != null && duple.getContentType().getValue().equals(relationToProcess.getContentType())) {
                        // if this relation is the last relation to process
                        if (relationToProcess.isLastRelationLevelToProcess()) {
                            result.add(relation);
                        } else {
                            for (DupleData dupleData : duple.getData()) {
                                // check if relationType of Duple Data is equals to relationToProcess relationType
                                if(dupleData.getRelType() == relationToProcess.getDupleRelationType()) {
                                    // get the methods to retrieve the cards of relation (TO, FROM or BOTH)
                                    List<Method> methodsToInvoke = relationToProcess.getDupleRelationContent().getMethodToInvoke();
                                    Object[] args = new Object[]{};
                                    for (Method method : methodsToInvoke){
                                        try {
                                            // invoke to method of Duple Data to retrieve the relation card
                                            Card relatedCard = (Card) method.invoke(dupleData, args);
                                            if(relatedCard != null) {
                                                // call to this method again (recursion) to retrieve relations of related card
                                                result.addAll(getRels(relatedCard, onlyRelationsToShowInCarousel));
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // check if relation is an instance of Single Class and relationToProcess is of Single type
                } else if (relation instanceof Single && relationToProcess.getRelationModuleType() == Single.class) {
                    Single single = ((Single) relation);
                    // check if contentType of Single relation is equals to relationToProcess contentType
                    if (single.getContentType() != null && single.getContentType().getValue().equals(relationToProcess.getContentType())) {
                        // if this relation is the last relation to process
                        if (relationToProcess.isLastRelationLevelToProcess()) {
                            result.add(relation);
                        } else {
                            for (Card relatedCard : single.getData()) {
                                // call to this method again (recursion) to retrieve relations of related card
                                result.addAll(getRels(relatedCard, onlyRelationsToShowInCarousel));
                            }
                        }
                    }
                }
            }
        }

        return result;

    }

    public static RelationModule getRelPlayedBy(List<RelationModule> relations) {
        for(RelationModule relation : relations) {
            if (relation instanceof Single) {
                Single single = (Single) relation;
                if (single.getContentType() == Single.ContentTypeEnum.PLAYED_BY){
                    return relation;
                }
            }
        }
        return null;
    }

    public static ImageData getImageOfActor(Card character){
        RelationModule playedByRelationModule = getRelPlayedBy(character.getRelations());
        if (playedByRelationModule != null){
            Single playedBy = (Single) playedByRelationModule;
            if (playedBy.getData() != null && playedBy.getData().size() > 0) {
                Card person = playedBy.getData().get(0);
                return person.getImage();
            }
        }
        return null;
    }

    public static CardContainer getContainer(CardContainer[] info, String contentType) {
        HashMap<String, CardContainer> contentTypeContainer = null;
        if (info == null || info.length == 0) {
            return null;
        }
        if (contentTypeContainer == null) {
            contentTypeContainer = new HashMap<>();
            for (CardContainer container : info) {
                contentTypeContainer.put(getContentType(container), container);
            }
        }

        return contentTypeContainer.get(contentType);
    }

    public static String getContentType(CardContainer container){
        String contentType = null;
        switch (container.getType()){
            case TEXT:
                contentType = ((Text)container).getContentType().getValue();
                break;
            case LISTING:
                contentType =  ((Listing)container).getContentType().getValue();
                break;
            case RATING:
                contentType =  ((Rating)container).getContentType().getValue();
                break;
            case MAP:
                contentType =  ((com.touchvie.sdk.model.Map)container).getContentType().getValue();
                break;
            case LINK:
                contentType =  ((Link)container).getContentType().getValue();
                break;
            case AWARDS:
                contentType =  ((Awards)container).getContentType().getValue();
                break;
            case CATALOG:
                contentType =  ((Catalog)container).getContentType().getValue();
                break;
            case SEASONS:
                contentType =  ((Seasons)container).getContentType().getValue();
                break;
            case IMAGE:
                contentType =  ((Image)container).getContentType().getValue();
                break;
            default:
                break;
        }
        return contentType;
    }


    public static String getTimeFromMinutes(long time, Context context){

        if (TimeUnit.MINUTES.toHours(time)==0){
            return String.format("%02d " + context.getResources().getString(R.string.MINUTES_SHORT),
                    TimeUnit.MINUTES.toMinutes(time) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(time)));
        } else {
            return String.format("%2d " + context.getString(R.string.HOURS_SHORT) + " %02d " + context.getResources().getString(R.string.MINUTES_SHORT),
                    TimeUnit.MINUTES.toHours(time),
                    TimeUnit.MINUTES.toMinutes(time) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(time)));
        }
    }

}
