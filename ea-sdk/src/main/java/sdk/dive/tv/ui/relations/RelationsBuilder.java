package sdk.dive.tv.ui.relations;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.Duple;
import com.touchvie.sdk.model.DupleData;
import com.touchvie.sdk.model.RelationModule;
import com.touchvie.sdk.model.Single;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Noemi on 28/10/2016.
 */

public class RelationsBuilder {


    public static void setRelations(Card carouselCard, RelationModule[] relations){

        switch(Card.TypeEnum.valueOf(carouselCard.getType().getValue())){

            case MOVIE:
                buildMovieRelations(carouselCard, relations);
                break;
            case SERIE:
                buildSerieRelations(carouselCard, relations);
                break;
            case PERSON:
//                buildPersonRelations(carouselCard, relations);
                break;
            case CHARACTER:
//                buildCharacterRelations(carouselCard, relations);
                break;
            case VEHICLE:
                buildVehicleRelations(carouselCard, relations);
                break;
            case FASHION:
                buildFashionRelations(carouselCard, relations);
                break;
            case LOCATION:
                buildLocationRelations(carouselCard, relations);
                break;
            case HISTORIC:
                buildHistoricRelations(carouselCard, relations);
                break;
            case TRIVIA:
                buildTriviaRelations(carouselCard, relations);
                break;
            case OST:
                buildOstRelations(carouselCard, relations);
                break;
            case HOME:
//                buildHomeRelations(carouselCard, relations);
                break;
            case TECHNOLOGY:
                buildTechnologyRelations(carouselCard, relations);
                break;
            case ART:
                buildArtRelations(carouselCard, relations);
                break;
            case SONG:
                buildSongRelations(carouselCard, relations);
                break;
            case LOOK:
                buildLookRelations(carouselCard, relations);
                break;
            case WEAPON:
                buildWeaponRelations(carouselCard, relations);
                break;
            case LEISURE_SPORT:
                buildLeisureSportRelations(carouselCard, relations);
                break;
            case HEALTH_BEAUTY:
                buildHealthBeautyRelations(carouselCard, relations);
                break;
            case FOOD_DRINK:
                buildFoodDrinkRelations(carouselCard, relations);
                break;
            case FAUNA_FLORA:
                buildFaunaFloraRelations(carouselCard, relations);
                break;
            case BUSINESS:
                buildBusinessRelations(carouselCard, relations);
                break;
            case CHAPTER:
                buildChapterRelations(carouselCard, relations);
                break;
        }

    }

    private static HashMap<String,HashMap<String, ArrayList<Card>>> getClassifiedRelations(RelationModule[] relations){
        HashMap<String,HashMap<String, ArrayList<Card>>> relTypeCardTypeCards= new HashMap<>();

        for(RelationModule relation: relations){

            if(relation==null)
                continue;

            if(relTypeCardTypeCards.get(relation.getType())==null){

                HashMap<String, ArrayList<Card>> hashMap=new HashMap<>();
                relTypeCardTypeCards.put(relation.getType().getValue(),hashMap );

                ArrayList<Card> arrayList= new ArrayList<>();
                if (relation.getType().equals(RelationModule.TypeEnum.SINGLE)) {
                    for (int i=0;i<((Single) relation).getData().size();i++) {
                        arrayList.add(((Single) relation).getData().get(i));
                    }
                    hashMap.put(((Single)relation).getData().get(0).getType().getValue(), arrayList);
                } else if (relation.getType().equals(RelationModule.TypeEnum.DUPLE)) {
                    for (int i=0;i<((Duple) relation).getData().size();i++) {
                        arrayList.add(((Duple) relation).getData().get(i).getTo());
                    }
                    hashMap.put(((Duple)relation).getData().get(0).getRelType().getValue(), arrayList);
                }
            }else{

                if(relTypeCardTypeCards.get(relation.getType()).get(relation.getType().equals(RelationModule.TypeEnum.SINGLE)?((Single)relation).getData():((Duple)relation).getData())==null){

                    ArrayList<Card> arrayList= new ArrayList<>();
                    if (relation.getType().equals(RelationModule.TypeEnum.SINGLE)) {
                        for (int i=0;i<((Single) relation).getData().size();i++) {
                            arrayList.add(((Single) relation).getData().get(i));
                        }
                    } else if (relation.getType().equals(RelationModule.TypeEnum.DUPLE)) {
                        for (int i=0;i<((Duple) relation).getData().size();i++) {
                            arrayList.add(((Duple) relation).getData().get(i).getTo());
                        }
                    }
                    relTypeCardTypeCards.get(relation.getType()).put(relation.getType().getValue(), arrayList );

                }else{
                    if (relation.getType().equals(RelationModule.TypeEnum.SINGLE)) {
                        for (int i=0;i<((Single) relation).getData().size();i++) {
                            relTypeCardTypeCards.get(relation.getType()).get(((Single)relation).getData().get(i).getType()).add(((Single)relation).getData().get(i));
                        }
                    } else if (relation.getType().equals(RelationModule.TypeEnum.DUPLE)) {
                        for (int i=0;i<((Duple) relation).getData().size();i++) {
                            relTypeCardTypeCards.get(relation.getType()).get(((Duple)relation).getData().get(i).getTo().getType()).add(((Duple)relation).getData().get(i).getTo());
                        }
                    }
                }
            }
        }
        return relTypeCardTypeCards;
    }

/*
    private static HashMap<String,HashMap<String, ArrayList<RelationModule>>> getCustomClassifiedRelations(RelationModule[] relations){
        HashMap<String,HashMap<String, ArrayList<RelationModule>>> relTypeCardTypeCards= new HashMap<>();

        for(RelationModule relation: relations){

            if(relation==null)
                continue;

            if(relTypeCardTypeCards.get(relation.getRel_type())==null){

                HashMap<String, ArrayList<RelationsData>> hashMap=new HashMap<>();
                relTypeCardTypeCards.put(relation.getRel_type(),hashMap );

                ArrayList<RelationsData> arrayList= new ArrayList<>();
                arrayList.add(relation);
                hashMap.put(relation.getData().getType(), arrayList );
            }else{

                if(relTypeCardTypeCards.get(relation.getRel_type()).get(relation.getData().getType())==null){

                    ArrayList<RelationsData> arrayList= new ArrayList<>();
                    arrayList.add(relation);
                    relTypeCardTypeCards.get(relation.getRel_type()).put(relation.getData().getType(), arrayList );

                }else{
                    relTypeCardTypeCards.get(relation.getRel_type()).get(relation.getData().getType()).add(relation);
                }
            }
        }
        return relTypeCardTypeCards;
    }
*/

    private static void addRelationsToCard(Card carouselCard, List<RelationModule> newRelations){
        carouselCard.setRelations(newRelations);
    }

    private static RelationModule getItemOnMovie(HashMap<String,HashMap<String, ArrayList<Card>>> relTypeCardTypeCards, String contentType, String relType, String cardType){

        //Looking for item_on_movie
        if(relTypeCardTypeCards.get(relType) !=null && relTypeCardTypeCards.get(relType).get(cardType) !=null ){

            int relationsLenght=relTypeCardTypeCards.get(relType).get(cardType).size();
            Single relation= new Single();
            relation.setType(RelationModule.TypeEnum.fromValue("single"));
            relation.setContentType(Single.ContentTypeEnum.fromValue(contentType));
//            relation.setData(new Card[relationsLenght]);

            for(int i=0; i<relationsLenght;i++){

                Card minicard= relTypeCardTypeCards.get(relType).get(cardType).get(i);
                relation.getData().add(minicard);
            }

            return relation;
        }
        return null;
    }

    private static RelationModule getItemTrivias(HashMap<String,HashMap<String, ArrayList<Card>>> relTypeCardTypeCards){
        //Looking for item_trivias
        if((relTypeCardTypeCards.get("owns_on_movie") !=null && relTypeCardTypeCards.get("owns_on_movie").get("trivia") !=null )|| (relTypeCardTypeCards.get("owns_off_movie") !=null && relTypeCardTypeCards.get("owns_off_movie").get("trivia") !=null )){

            int onLenght=relTypeCardTypeCards.get("owns_on_movie")!=null?(relTypeCardTypeCards.get("owns_on_movie").get("trivia") !=null?relTypeCardTypeCards.get("owns_on_movie").get("trivia").size():0):0;
            int offLenght=relTypeCardTypeCards.get("owns_off_movie")!=null?(relTypeCardTypeCards.get("owns_off_movie").get("trivia") !=null?relTypeCardTypeCards.get("owns_off_movie").get("trivia").size():0):0;

            int relationsLenght=onLenght+offLenght;
            Single relation= new Single();
            relation.setType(RelationModule.TypeEnum.fromValue("single"));
            relation.setContentType(Single.ContentTypeEnum.fromValue("trivias"));
//            relation.setData(new Card[relationsLenght]);

            for(int i=0; i<onLenght;i++){

                Card minicard= relTypeCardTypeCards.get("owns_on_movie").get("trivia").get(i);
                relation.getData().add(minicard);
            }
            for(int i=0; i<offLenght;i++){

                Card minicard= relTypeCardTypeCards.get("owns_off_movie").get("trivia").get(i);
                relation.getData().add(minicard);
            }
            return relation;
        }

        return null;
    }

    private static void buildMovieRelations(Card carouselCard, RelationModule[] relations){

        HashMap<String,HashMap<String, ArrayList<Card>>> relTypeCardTypeCards= getClassifiedRelations(relations);

        List<RelationModule> newRelations= new ArrayList<>();


        //TODO: change names to rel types in sdk library
        //Looking for movie_vehicles
        if(relTypeCardTypeCards.get("features_vehicle") !=null && relTypeCardTypeCards.get("features_vehicle").get("vehicle") !=null ){

            int relationsLenght=relTypeCardTypeCards.get("features_vehicle").get("vehicle").size();
            Single relation= new Single();
            relation.setType(RelationModule.TypeEnum.fromValue("single"));
            relation.setContentType(Single.ContentTypeEnum.fromValue("movie_vehicles"));

            for(int i=0; i<relationsLenght;i++){

                Card minicard= relTypeCardTypeCards.get("features_vehicle").get("vehicle").get(i);
                relation.getData().add(minicard);
            }
            newRelations.add(relation);
        }

        //Looking for movie_songs
        if(relTypeCardTypeCards.get("features_song") !=null && relTypeCardTypeCards.get("features_trivia").get("song") !=null ){

            int relationsLenght=relTypeCardTypeCards.get("features_song").get("song").size();
            Single relation= new Single();
            relation.setType(RelationModule.TypeEnum.fromValue("single"));
            relation.setContentType(Single.ContentTypeEnum.fromValue("movie_songs"));

            for(int i=0; i<relationsLenght;i++){

                Card minicard= relTypeCardTypeCards.get("features_song").get("song").get(i);
                relation.getData().add(minicard);
            }
            newRelations.add(relation);
        }
        //Looking for movie_highlighted
        if((relTypeCardTypeCards.get("features_tech") !=null && relTypeCardTypeCards.get("features_tech").get("tech") !=null )|| (relTypeCardTypeCards.get("features_art") !=null && relTypeCardTypeCards.get("features_art").get("art") !=null )){

            int techLenght=relTypeCardTypeCards.get("features_tech")!=null?(relTypeCardTypeCards.get("features_tech").get("tech") !=null?relTypeCardTypeCards.get("features_tech").get("tech").size():0):0;
            int artLenght=relTypeCardTypeCards.get("features_art")!=null?(relTypeCardTypeCards.get("features_art").get("art") !=null?relTypeCardTypeCards.get("features_art").get("art").size():0):0;

            int relationsLenght=techLenght+artLenght;
            Single relation= new Single();
            relation.setType(RelationModule.TypeEnum.fromValue("single"));
            relation.setContentType(Single.ContentTypeEnum.fromValue("highlighted"));

            for(int i=0; i<techLenght;i++){

                Card minicard= relTypeCardTypeCards.get("features_tech").get("tech").get(i);
                relation.getData().add(minicard);
            }
            for(int i=0; i<artLenght;i++){

                Card minicard= relTypeCardTypeCards.get("features_art").get("art").get(i);
                relation.getData().add(minicard);
            }
            newRelations.add(relation);
        }

        addRelationsToCard(carouselCard,newRelations);

    }
    private static void buildSerieRelations(Card carouselCard, RelationModule[] relations){

        //All are off movie.
    }

    private static void buildChapterRelations(Card carouselCard, RelationModule[] relations){
        //All are off movie.
    }

    private static void buildVehicleRelations(Card carouselCard, RelationModule[] relations){

        HashMap<String,HashMap<String, ArrayList<Card>>> relTypeCardTypeCards= getClassifiedRelations(relations);

        List<RelationModule> newRelations= new ArrayList<>();

        //TODO: change names to rel types in sdk library

        //Looking for vechicle_appears_in

        RelationModule itemRelationOnMovie=getItemOnMovie(relTypeCardTypeCards,"appears_in", "vehicle_on_movie","movie"  );
        if(itemRelationOnMovie!=null){
            newRelations.add(itemRelationOnMovie);
        }
        //Looking for item trivias
        RelationModule itemRelation= getItemTrivias(relTypeCardTypeCards);
        if(itemRelation!=null){
            newRelations.add(itemRelation);
        }

        addRelationsToCard(carouselCard,newRelations);
    }

    private static void buildFashionRelations(Card carouselCard, RelationModule[] relations){
        HashMap<String,HashMap<String, ArrayList<Card>>> relTypeCardTypeCards= getClassifiedRelations(relations);

        List<RelationModule> newRelations= new ArrayList<>();

        //TODO: change names to rel types in sdk library

        //Looking for fashion_appears_in
        RelationModule itemRelationOnMovie=getItemOnMovie(relTypeCardTypeCards,"appears_in", "fashion_on_movie","movie");
        if(itemRelationOnMovie!=null){
            newRelations.add(itemRelationOnMovie);
        }
        //Looking for item trivias
        RelationModule itemRelation= getItemTrivias(relTypeCardTypeCards);
        if(itemRelation!=null){
            newRelations.add(itemRelation);
        }

        addRelationsToCard(carouselCard,newRelations);
    }

    private static void buildTechnologyRelations(Card carouselCard, RelationModule[] relations){
        HashMap<String,HashMap<String, ArrayList<Card>>> relTypeCardTypeCards= getClassifiedRelations(relations);

        List<RelationModule> newRelations= new ArrayList<>();

        //TODO: change names to rel types in sdk library

        //Looking for tech_appears_in
        RelationModule itemRelationOnMovie=getItemOnMovie(relTypeCardTypeCards,"appears_in", "tech_on_movie","movie");
        if(itemRelationOnMovie!=null){
            newRelations.add(itemRelationOnMovie);
        }

        addRelationsToCard(carouselCard,newRelations);
    }
    private static void buildArtRelations(Card carouselCard, RelationModule[] relations){
        HashMap<String,HashMap<String, ArrayList<Card>>> relTypeCardTypeCards= getClassifiedRelations(relations);

        List<RelationModule> newRelations= new ArrayList<>();

        //TODO: change names to rel types in sdk library

        //Looking for art_appears_in
        RelationModule itemRelationOnMovie=getItemOnMovie(relTypeCardTypeCards,"appears_in", "art_on_movie","movie");
        if(itemRelationOnMovie!=null){
            newRelations.add(itemRelationOnMovie);
        }
        //Looking for item trivias
        RelationModule itemRelation= getItemTrivias(relTypeCardTypeCards);
        if(itemRelation!=null){
            newRelations.add(itemRelation);
        }

        addRelationsToCard(carouselCard,newRelations);
    }

    private static void buildLookRelations(Card carouselCard, RelationModule[] relations){
        HashMap<String,HashMap<String, ArrayList<Card>>> relTypeCardTypeCards= getClassifiedRelations(relations);

        List<RelationModule> newRelations= new ArrayList<>();

        //TODO: change names to rel types in sdk library

        //Looking for worn_by
        if(relTypeCardTypeCards.get("worn_by") !=null && relTypeCardTypeCards.get("worn_by").get("character") !=null ){

            int relationsLenght=relTypeCardTypeCards.get("worn_by").get("character").size();
            Duple relation= new Duple();
            relation.setType(RelationModule.TypeEnum.fromValue("duple"));
            relation.setContentType(Duple.ContentTypeEnum.fromValue("worn_by"));

            for(int i=0; i<relationsLenght;i++){

                Card minicard= relTypeCardTypeCards.get("worn_by").get("character").get(i);
                DupleData dupleRelation= new DupleData();
                dupleRelation.setTo(minicard);
                dupleRelation.setRelType(DupleData.RelTypeEnum.fromValue("worn_by"));
                dupleRelation.setFrom(null);
                relation.getData().add(dupleRelation);
            }
            newRelations.add(relation);
        }

        //Looking for item trivias
        RelationModule itemRelation= getItemTrivias(relTypeCardTypeCards);
        if(itemRelation!=null){
            newRelations.add(itemRelation);
        }

        addRelationsToCard(carouselCard,newRelations);
    }
    private static void buildWeaponRelations(Card carouselCard, RelationModule[] relations){
        HashMap<String,HashMap<String, ArrayList<Card>>> relTypeCardTypeCards= getClassifiedRelations(relations);

        List<RelationModule> newRelations= new ArrayList<>();

        //TODO: change names to rel types in sdk library

        //Looking for weapon_appears_in
        RelationModule itemRelationOnMovie=getItemOnMovie(relTypeCardTypeCards,"appears_in", "weapon_on_movie","movie");
        if(itemRelationOnMovie!=null){
            newRelations.add(itemRelationOnMovie);
        }
        //Looking for item trivias
        RelationModule itemRelation= getItemTrivias(relTypeCardTypeCards);
        if(itemRelation!=null){
            newRelations.add(itemRelation);
        }

        addRelationsToCard(carouselCard,newRelations);
    }
    private static void buildLeisureSportRelations(Card carouselCard, RelationModule[] relations){
        HashMap<String,HashMap<String, ArrayList<Card>>> relTypeCardTypeCards= getClassifiedRelations(relations);

        List<RelationModule> newRelations= new ArrayList<>();

        //TODO: change names to rel types in sdk library

        //Looking for leisure_sport_appears_in
        RelationModule itemRelationOnMovie=getItemOnMovie(relTypeCardTypeCards,"appears_in", "leisure_sport_on_movie","movie");
        if(itemRelationOnMovie!=null){
            newRelations.add(itemRelationOnMovie);
        }
        //Looking for item trivias
        RelationModule itemRelation= getItemTrivias(relTypeCardTypeCards);
        if(itemRelation!=null){
            newRelations.add(itemRelation);
        }

        addRelationsToCard(carouselCard,newRelations);
    }
    private static void buildHealthBeautyRelations(Card carouselCard, RelationModule[] relations){
        HashMap<String,HashMap<String, ArrayList<Card>>> relTypeCardTypeCards= getClassifiedRelations(relations);

        List<RelationModule> newRelations= new ArrayList<>();

        //TODO: change names to rel types in sdk library

        //Looking for health_beauty_appears_in
        RelationModule itemRelationOnMovie=getItemOnMovie(relTypeCardTypeCards,"appears_in", "health_beauty_on_movie","movie");
        if(itemRelationOnMovie!=null){
            newRelations.add(itemRelationOnMovie);
        }
        //Looking for item trivias
        RelationModule itemRelation= getItemTrivias(relTypeCardTypeCards);
        if(itemRelation!=null){
            newRelations.add(itemRelation);
        }

        addRelationsToCard(carouselCard,newRelations);
    }
    private static void buildFoodDrinkRelations(Card carouselCard, RelationModule[] relations){

        HashMap<String,HashMap<String, ArrayList<Card>>> relTypeCardTypeCards= getClassifiedRelations(relations);

        List<RelationModule> newRelations= new ArrayList<>();

        //TODO: change names to rel types in sdk library

        //Looking for food_drink_appears_in
        RelationModule itemRelationOnMovie=getItemOnMovie(relTypeCardTypeCards,"appears_in", "food_drink_on_movie","movie");
        if(itemRelationOnMovie!=null){
            newRelations.add(itemRelationOnMovie);
        }
        //Looking for item trivias
        RelationModule itemRelation= getItemTrivias(relTypeCardTypeCards);
        if(itemRelation!=null){
            newRelations.add(itemRelation);
        }

        addRelationsToCard(carouselCard,newRelations);
    }
    private static void buildFaunaFloraRelations(Card carouselCard, RelationModule[] relations){
        HashMap<String,HashMap<String, ArrayList<Card>>> relTypeCardTypeCards= getClassifiedRelations(relations);

        List<RelationModule> newRelations= new ArrayList<>();

        //TODO: change names to rel types in sdk library

        //Looking for fauna_flora_appears_in
        RelationModule itemRelationOnMovie=getItemOnMovie(relTypeCardTypeCards,"appears_in", "fauna_flora_on_movie","movie");
        if(itemRelationOnMovie!=null){
            newRelations.add(itemRelationOnMovie);
        }
        //Looking for item trivias
        RelationModule itemRelation= getItemTrivias(relTypeCardTypeCards);
        if(itemRelation!=null){
            newRelations.add(itemRelation);
        }

        addRelationsToCard(carouselCard,newRelations);
    }
    private static void buildBusinessRelations(Card carouselCard, RelationModule[] relations){
        HashMap<String,HashMap<String, ArrayList<Card>>> relTypeCardTypeCards= getClassifiedRelations(relations);

        List<RelationModule> newRelations= new ArrayList<>();

        //TODO: change names to rel types in sdk library

        //Looking for business_appears_in
        RelationModule itemRelationOnMovie=getItemOnMovie(relTypeCardTypeCards,"appears_in", "business_on_movie","movie");
        if(itemRelationOnMovie!=null){
            newRelations.add(itemRelationOnMovie);
        }
        //Looking for item trivias
        RelationModule itemRelation= getItemTrivias(relTypeCardTypeCards);
        if(itemRelation!=null){
            newRelations.add(itemRelation);
        }

        addRelationsToCard(carouselCard,newRelations);
    }

    private static void buildLocationRelations(Card carouselCard, RelationModule[] relations){
        HashMap<String,HashMap<String, ArrayList<Card>>> relTypeCardTypeCards= getClassifiedRelations(relations);

        List<RelationModule> newRelations= new ArrayList<>();

        //TODO: change names to rel types in sdk library
        //Looking for featured_in
        if( (relTypeCardTypeCards.get("filmed_in") !=null && relTypeCardTypeCards.get("filmed_in").get("location") !=null) || (relTypeCardTypeCards.get("represents") !=null && relTypeCardTypeCards.get("represents").get("location") !=null) || (relTypeCardTypeCards.get("location_on_movie") !=null && relTypeCardTypeCards.get("location_on_movie").get("location") !=null)  ) {

            int filmedInLenght=relTypeCardTypeCards.get("filmed_in")!=null?(relTypeCardTypeCards.get("filmed_in").get("location") !=null?relTypeCardTypeCards.get("filmed_in").get("location").size():0):0;
            int representsLenght=relTypeCardTypeCards.get("represents")!=null?(relTypeCardTypeCards.get("represents").get("location") !=null?relTypeCardTypeCards.get("represents").get("location").size():0):0;
            int locationonLenght=relTypeCardTypeCards.get("owns_on_movie")!=null?(relTypeCardTypeCards.get("owns_on_movie").get("location") !=null?relTypeCardTypeCards.get("owns_on_movie").get("location").size():0):0;

            int relationsLenght=filmedInLenght+representsLenght+locationonLenght;
            Duple relation= new Duple();
            relation.setType(RelationModule.TypeEnum.fromValue("duple"));
            relation.setContentType(Duple.ContentTypeEnum.fromValue("featured_in"));

            for(int i=0; i<filmedInLenght;i++){

                Card minicard= relTypeCardTypeCards.get("filmed_in").get("location").get(i);
                DupleData dupleRelation= new DupleData();
                dupleRelation.setTo(minicard);
                dupleRelation.setRelType(DupleData.RelTypeEnum.fromValue("filmed_in"));
                relation.getData().add(dupleRelation);
            }
            for(int i=0; i<representsLenght;i++){
                Card minicard= relTypeCardTypeCards.get("represents").get("location").get(i);
                DupleData dupleRelation= new DupleData();
                dupleRelation.setTo(minicard);
                dupleRelation.setRelType(DupleData.RelTypeEnum.fromValue("represents"));
                Card miniCard2 = new Card();
                dupleRelation.setFrom(miniCard2);
                relation.getData().add(dupleRelation);
            }
            for(int i=0; i<locationonLenght;i++){
                Card minicard= relTypeCardTypeCards.get("owns_on_movie").get("location").get(i);
                DupleData dupleRelation= new DupleData();
                dupleRelation.setTo(minicard);
                dupleRelation.setRelType(DupleData.RelTypeEnum.fromValue("owns_on_movie"));
                Card miniCard2 = new Card();
                dupleRelation.setFrom(miniCard2);
                relation.getData().add(dupleRelation);
            }
            newRelations.add(relation);
        }

        //Looking for item trivias
        RelationModule itemRelation= getItemTrivias(relTypeCardTypeCards);
        if(itemRelation!=null){
            newRelations.add(itemRelation);
        }

        addRelationsToCard(carouselCard,newRelations);
    }
    private static void buildHistoricRelations(Card carouselCard, RelationModule[] relations){
        HashMap<String,HashMap<String, ArrayList<Card>>> relTypeCardTypeCards= getClassifiedRelations(relations);

        List<RelationModule> newRelations= new ArrayList<>();

        //TODO: change names to rel types in sdk library

        //Looking for historic_appears_in
        RelationModule itemRelationOnMovie=getItemOnMovie(relTypeCardTypeCards,"appears_in", "historic_on_movie","movie");
        if(itemRelationOnMovie!=null){
            newRelations.add(itemRelationOnMovie);
        }
        //Looking for item trivias
        RelationModule itemRelation= getItemTrivias(relTypeCardTypeCards);
        if(itemRelation!=null){
            newRelations.add(itemRelation);
        }

        addRelationsToCard(carouselCard,newRelations);
    }
    private static void buildOstRelations(Card carouselCard, RelationModule[] relations){

        HashMap<String,HashMap<String, ArrayList<Card>>> relTypeCardTypeCards= getClassifiedRelations(relations);

        List<RelationModule> newRelations= new ArrayList<>();

        //TODO: change names to rel types in sdk library

        //Looking for sounds_in
        RelationModule itemRelationOnMovie=getItemOnMovie(relTypeCardTypeCards,"sounds_in", "ost_on_movie","movie");
        if(itemRelationOnMovie!=null){
            newRelations.add(itemRelationOnMovie);
        }


        addRelationsToCard(carouselCard,newRelations);
    }
    private static void buildSongRelations(Card carouselCard, RelationModule[] relations){
        HashMap<String,HashMap<String, ArrayList<Card>>> relTypeCardTypeCards= getClassifiedRelations(relations);

        List<RelationModule> newRelations= new ArrayList<>();

        //TODO: change names to rel types in sdk library

        //Looking for item trivias
        RelationModule itemRelation= getItemTrivias(relTypeCardTypeCards);
        if(itemRelation!=null){
            newRelations.add(itemRelation);
        }

        addRelationsToCard(carouselCard,newRelations);
    }

    private static void buildTriviaRelations(Card carouselCard, RelationModule[] relations){
        HashMap<String,HashMap<String, ArrayList<Card>>> relTypeCardTypeCards= getClassifiedRelations(relations);

        List<RelationModule> newRelations= new ArrayList<>();

        //TODO: change names to rel types in sdk library

        //Looking for trivia_appears_in
        RelationModule itemRelationOnMovie=getItemOnMovie(relTypeCardTypeCards,"appears_in", "trivia_on_movie","movie");
        if(itemRelationOnMovie!=null){
            newRelations.add(itemRelationOnMovie);
        }


        addRelationsToCard(carouselCard,newRelations);
    }


}
