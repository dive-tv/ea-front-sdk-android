package sdk.dive.tv.ui.relations;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.Duple;
import com.touchvie.sdk.model.DupleData;
import com.touchvie.sdk.model.RelationModule;
import com.touchvie.sdk.model.Single;

import java.util.ArrayList;
import java.util.List;


public enum RelationsToProcess {

    PLAYED_BY(Card.TypeEnum.CHARACTER, Single.class, "played_by", null, null, true, false),
    WEARS(Card.TypeEnum.CHARACTER, Single.class, "wears", null, null, false, true),
    LOOK_FASHION(Card.TypeEnum.LOOK , Single.class, "look_fashion", null, null, true, true),
    HOME_DECO(Card.TypeEnum.HOME, Single.class, "home_deco", null, null, true, true),
    LOCATION_FILMED_IN(Card.TypeEnum.LOCATION, Single.class, "filmed_in", null, null, true, true),
    LOCATION_FEATURED_IN_FILMED_IN(Card.TypeEnum.LOCATION, Duple.class, "featured_in", DupleData.RelTypeEnum.FILMED_IN, DupleRelationContent.TO, true, true),
    LOCATION_REPRESENTS(Card.TypeEnum.LOCATION, Single.class, "represents", null, null, true, true),
    LOCATION_FEATURED_IN_REPRESENTS(Card.TypeEnum.LOCATION, Duple.class, "featured_in", DupleData.RelTypeEnum.REPRESENTS, DupleRelationContent.TO, true, true),
    LOCATION_LOCATION_ON_MOVIE(Card.TypeEnum.LOCATION, Duple.class, "featured_in", DupleData.RelTypeEnum.LOCATION_ON_MOVIE, DupleRelationContent.TO, true, true);


    private final Card.TypeEnum cardType;
    private final Class<? extends RelationModule> relationModuleType;
    private final String contentType;
    private final DupleData.RelTypeEnum dupleRelationType;
    private final DupleRelationContent dupleRelationContent;
    private final boolean lastRelationLevelToProcess;
    private final boolean relationToShowInCarousel;

    RelationsToProcess(Card.TypeEnum cardType, Class<? extends RelationModule> relationModuleType, String contentType, DupleData.RelTypeEnum dupleRelationType, DupleRelationContent dupleRelationContent, boolean lastRelationLevelToProcess, boolean relationToShowInCarousel) {
        this.cardType = cardType;
        this.relationModuleType = relationModuleType;
        this.contentType = contentType;
        this.dupleRelationType = dupleRelationType;
        this.dupleRelationContent =dupleRelationContent;
        this.lastRelationLevelToProcess = lastRelationLevelToProcess;
        this.relationToShowInCarousel = relationToShowInCarousel;
    }

    public Card.TypeEnum getCardType() {
        return cardType;
    }

    public Class<? extends RelationModule> getRelationModuleType() {
        return relationModuleType;
    }

    public String getContentType() {
        return contentType;
    }

    public DupleData.RelTypeEnum getDupleRelationType() {
        return dupleRelationType;
    }

    public DupleRelationContent getDupleRelationContent() {
        return dupleRelationContent;
    }

    public boolean isLastRelationLevelToProcess() {
        return lastRelationLevelToProcess;
    }

    public boolean isRelationToShowInCarousel() {
        return relationToShowInCarousel;
    }

    public static List<RelationsToProcess> fromCardType(Card.TypeEnum cardType) {
        List<RelationsToProcess> relations = new ArrayList<>();
        for (RelationsToProcess rel : RelationsToProcess.values())
            if (rel.getCardType() == cardType)
                relations.add(rel);
        return relations;
    }

    public static List<RelationsToProcess> fromCardTypeToShowInCarousel(Card.TypeEnum cardType) {
        List<RelationsToProcess> relations = new ArrayList<>();
        for (RelationsToProcess rel : RelationsToProcess.values())
            if (rel.isRelationToShowInCarousel() && rel.getCardType() == cardType)
                relations.add(rel);
        return relations;
    }

}