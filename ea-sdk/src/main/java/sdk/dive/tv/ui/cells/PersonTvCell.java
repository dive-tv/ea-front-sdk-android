package sdk.dive.tv.ui.cells;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.CardContainer;
import com.touchvie.sdk.model.CardUser;
import com.touchvie.sdk.model.Duple;
import com.touchvie.sdk.model.DupleData;
import com.touchvie.sdk.model.ImageData;
import com.touchvie.sdk.model.RelationModule;
import com.touchvie.sdk.model.Single;

import java.util.List;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.utils.CropSquareTransformation2;
import sdk.dive.tv.ui.views.CarouselTVCellButton;
import sdk.dive.tv.ui.views.CarouselTVCellLinear;

import static com.touchvie.sdk.model.CardContainer.TypeEnum.TEXT;
import static com.touchvie.sdk.model.Duple.ContentTypeEnum.PLAYED_BY;

/**
 * Created by Tagsonomy S.L. on 04/07/2017.
 */

public class PersonTvCell extends CarouselTvCell {

    public PersonTvCell() {

    }

    @Override
    public LinearLayout getView(final Context context) {
        Typeface latoSemi = Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD);
        Typeface latoRegular = Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR);
        final Picasso mPicasso = Picasso.with(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View baseView = inflater.inflate(R.layout.tv_cell_person, null);

        cellLayout = (CarouselTVCellLinear) baseView.findViewById(R.id.person_celltv_container);

        final ImageView image = (ImageView) baseView.findViewById(R.id.person_tv_cell_image);
        final ImageView noImage = (ImageView) baseView.findViewById(R.id.person_tv_cell_noimage);
        if (getCard().getImage() != null && getCard().getImage().getThumb() != null) {
            noImage.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            image.post(new Runnable() {
                @Override
                public void run() {
                    mPicasso.load(getCard().getImage().getThumb())
                            .transform(new CropSquareTransformation2((int) context.getResources().getDimension(R.dimen.genericcell_image_width), image.getMeasuredHeight(),
                                    getCard().getImage().getAnchorX().intValueExact(), getCard().getImage().getAnchorY().intValueExact()))
                            .priority(Picasso.Priority.HIGH)
                            .into(image);
                }
            });
        } else {
            final ImageData actorImage = Utils.getImageOfActor(getCard());
            if (actorImage != null && actorImage.getThumb() != null){
                noImage.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                image.post(new Runnable() {
                    @Override
                    public void run() {
                        mPicasso.load(actorImage.getThumb())
                                .transform(new CropSquareTransformation2((int) context.getResources().getDimension(R.dimen.genericcell_image_width), image.getMeasuredHeight(),
                                        actorImage.getAnchorX().intValueExact(), actorImage.getAnchorY().intValueExact()))
                                .priority(Picasso.Priority.HIGH)
                                .into(image);
                    }
                });
            } else {
                noImage.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
            }
        }

        mImageBorder = (FrameLayout) baseView.findViewById(R.id.person_tv_cell_image_border);

        mTypeBox = (TextView) baseView.findViewById(R.id.person_tv_cell_type);
        mTypeBox.setTypeface(latoSemi);
        if (getCard().getType() != null) {
            mTypeBox.setText(Utils.getType(getCard().getType().getValue(), context));
        } else {
            mTypeBox.setText("");
        }

        mInfoContainer = (RelativeLayout) baseView.findViewById(R.id.person_celltv_info_container);
        openWhenFocused = new OpenWhenFocused(context, mInfoContainer);
        mInfoBorder = (FrameLayout) baseView.findViewById(R.id.person_cell_info_border);

        //Actor
        TextView infoTitle = (TextView) baseView.findViewById(R.id.person_celltv_info_title);
        infoTitle.setTypeface(latoSemi);
        RelationModule relation = null;
        String actorID = null;
        String actorVersion = null;
        String actorType = null;
        CardUser actorUser = null;
        TextView infoTxt = (TextView) baseView.findViewById(R.id.person_celltv_info_text);
        infoTxt.setTypeface(latoRegular);
        infoTxt.setText("");

        List<RelationModule> relationModules = Utils.getRels(getCard(), false);
        if (relationModules.size() > 0) {
            Single relationPlayedBy = (Single) Utils.getRelPlayedBy(relationModules);
            if (relationPlayedBy != null && relationPlayedBy.getData().size() > 0){
                Card personCard = relationPlayedBy.getData().get(0);
                actorID = personCard.getCardId();
                actorVersion = personCard.getVersion();
                actorType = personCard.getType().getValue();
                actorUser = personCard.getUser();

                if (personCard.getTitle() != null && personCard.getTitle().length() > 0) {
                    infoTitle.setText(personCard.getTitle());
                } else {
                    infoTitle.setText("");
                }

                if (actorType != null && actorType.length() > 0) {
                    mTypeBox.setText(Utils.getType(actorType, context));
                } else {
                    mTypeBox.setText("");
                }
                if(personCard.getInfo() != null) {
                    CardContainer[] info = personCard.getInfo().toArray(new CardContainer[personCard.getInfo().size()]);
                    if (info != null && info.length > 0) {
                        for (CardContainer container : info) {
                            if (TEXT.getValue().equals(container.getType())) {
                                infoTxt.setText(((com.touchvie.sdk.model.Text) container).getData().get(0).getText());
                                break;
                            }
                        }
                    }
                }
            }
        } else {
            infoTitle.setText("");
            infoTxt.setText("");
        }

        //Character
        TextView infoSubtitle = (TextView) baseView.findViewById(R.id.person_celltv_info_subtitle);
        infoSubtitle.setTypeface(latoSemi);
        if (getCard().getTitle() != null) {
            if (infoTitle.getText() == null || infoTitle.getText().length() == 0 || "".equals(infoTitle.getText()))
                infoTitle.setText(getCard().getTitle());
            else
                infoSubtitle.setText(context.getString(R.string.person_cell_as, getCard().getTitle()));
        } else {
            if (infoTitle.getText() == null || infoTitle.getText().length() == 0 || "".equals(infoTitle.getText()))
                infoTitle.setText("");
            else
                infoSubtitle.setText("");
        }

        mSeeMoreContainer = (CarouselTVCellButton) baseView.findViewById(R.id.person_cell_info_seemore);
        final String finalActorID = actorID;
        final String finalActorVersion = actorVersion;
        final String finalActorType = actorType;
        final CardUser finalActorUser = actorUser;
        mSeeMoreContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalActorID != null && finalActorID.length() > 0) {
                    if (cellLayout != null && mCarouselInterface != null)
                        mCarouselInterface.clickedView(cellLayout);
                    mActivityListener.onCallCardDetail(finalActorID, finalActorVersion, Card.TypeEnum.fromValue(finalActorType));
                } else if (getCard().getCardId() != null && getCard().getCardId().length() > 0) {
                    if (cellLayout != null && mCarouselInterface != null)
                        mCarouselInterface.clickedView(cellLayout);
                    Card card = getCard();
                    mActivityListener.onCallCardDetail(card.getCardId(), card.getVersion(), Card.TypeEnum.fromValue(finalActorType));
                }
            }
        });

/*        mLikeContainer = (CarouselTVCellLinear) baseView.findViewById(R.id.person_cell_info_liked);
        mLikeIcon = (ImageView) baseView.findViewById(R.id.person_cell_info_liked_icon);
        mLikeTxt = (TextView) baseView.findViewById(R.id.person_cell_info_liked_txt);
        mLikeTxt.setTypeface(latoSemi);

        if (finalActorUser != null && finalActorUser.isLiked()) {
            mLikeIcon.setImageResource(R.drawable.ico_cell_like_mass);
            mLikeTxt.setText(context.getString(R.string.song_cell_saved));
        } else if (getCard().getUser() != null && getCard().getUser().isLiked()) {
            mLikeIcon.setImageResource(R.drawable.ico_cell_like_mass);
            mLikeTxt.setText(context.getString(R.string.song_cell_saved));
        } else {
            mLikeIcon.setImageResource(R.drawable.ico_cell_like);
            mLikeTxt.setText(context.getString(R.string.song_cell_save_for_later));
        }

        mLikeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikesListener mListener = new LikesListener() {
                    @Override
                    public void onLikesReceived(MiniCard[] miniCards, String paginateKey) {

                    }

                    @Override
                    public void onLikesError(NetworkData response) {

                    }

                    @Override
                    public void onsetLike(boolean isLiked) {
                        HashMap<String, Boolean> likesHashMap = new HashMap<String, Boolean>();
                        if (isLiked) {
                            mLikeIcon.setImageResource(R.drawable.ico_cell_like_mass);
                            mLikeTxt.setText(context.getString(R.string.song_cell_saved));
                            if (finalActorUser != null) {
                                likesHashMap.put(finalActorID, true);
                                finalActorUser.setIsLiked(true);
                            } else {
                                likesHashMap.put(getCard().getMiniCard().getCardId(), true);
                                getCard().getMiniCard().getUser().setIsLiked(true);
                            }
                        } else {
                            mLikeIcon.setImageResource(R.drawable.ico_cell_like);
                            mLikeTxt.setText(context.getString(R.string.song_cell_save_for_later));
                            if (finalActorUser != null) {
                                likesHashMap.put(finalActorID, false);
                                finalActorUser.setIsLiked(false);
                            } else {
                                likesHashMap.put(getCard().getMiniCard().getCardId(), false);
                                getCard().getMiniCard().getUser().setIsLiked(false);
                            }
                            if (mPocketInterface != null)
                                mPocketInterface.setCardDeleted(getCard().getMiniCard().getCardId());
                        }

                        if (mCarouselInterface != null)
                            mCarouselInterface.setCardsAreLiked(likesHashMap);
                    }

                    @Override
                    public void onSetLikeError(NetworkData response) {

                    }
                };
                if (finalActorUser != null) {
                    if (finalActorUser.isLiked()) {
                        RestManager.getInstance().deleteLike(finalActorID, mListener);
                    } else {
                        RestManager.getInstance().setLike(finalActorID, mListener);
                    }
                } else if (getCard().getMiniCard().getUser() != null) {
                    if (getCard().getMiniCard().getUser().isLiked()) {
                        RestManager.getInstance().deleteLike(getCard().getMiniCard().getCardId(), mListener);
                    } else {
                        RestManager.getInstance().setLike(getCard().getMiniCard().getCardId(), mListener);
                    }
                }
            }
        });*/

        cellLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    onCellFocusedSetColors(context);
                    onCellFocused();
                    noImage.setColorFilter(Utils.getColor(context, R.color.white));
                } else {
                    if (!isSeeMoreFocused && !isLikeBtnFocused) {
                        onCellUnfocused(context);
                        noImage.setColorFilter(Utils.getColor(context, R.color.warm_grey));
                    }
                }
            }
        });

        mSeeMoreContainer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                isSeeMoreFocused = hasfocus;
                if (hasfocus) {
                    cellLayout.setFocusable(false);
                    cellLayout.setFocusableInTouchMode(false);
                    onCellFocusedSetColors(context);
                    noImage.setColorFilter(Utils.getColor(context, R.color.white));
                    mInfoContainer.setVisibility(View.VISIBLE);
                    mInfoContainer.animate()
                            .translationX(0);
                } else if (!isLikeBtnFocused) {
                    cellLayout.setFocusable(true);
                    cellLayout.setFocusableInTouchMode(true);
                    onCellUnfocused(context);
                    noImage.setColorFilter(Utils.getColor(context, R.color.warm_grey));
                }
            }
        });

        return cellLayout;
    }

}
