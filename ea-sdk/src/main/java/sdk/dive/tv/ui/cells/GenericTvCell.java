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
import com.touchvie.sdk.model.Text;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.utils.CropSquareTransformation2;
import sdk.dive.tv.ui.views.CarouselTVCellButton;
import sdk.dive.tv.ui.views.CarouselTVCellLinear;

import static com.touchvie.sdk.model.CardContainer.TypeEnum.TEXT;

/**
 * Created by Tagsonomy S.L. on 04/07/2017.
 */

public class GenericTvCell extends CarouselTvCell {

    public GenericTvCell() {

    }

    @Override
    public LinearLayout getView(final Context context) {
        Typeface latoSemi = Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD);
        Typeface latoRegular = Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR);
        final Picasso mPicasso = Picasso.with(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View baseView = inflater.inflate(R.layout.tv_cell_generic, null);

        cellLayout = (CarouselTVCellLinear) baseView.findViewById(R.id.generic_celltv_container);

        final ImageView image = (ImageView) baseView.findViewById(R.id.generic_tv_cell_image);
        final ImageView noImage = (ImageView) baseView.findViewById(R.id.generic_tv_cell_noimage);
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
            noImage.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
        }
        mImageBorder = (FrameLayout) baseView.findViewById(R.id.generic_tv_cell_image_border);

        mTypeBox = (TextView) baseView.findViewById(R.id.generic_tv_cell_type);
        mTypeBox.setTypeface(latoSemi);
        if (getCard().getType() != null) {
            mTypeBox.setText(Utils.getType(getCard().getType().getValue(), context));
        } else {
            mTypeBox.setText("");
        }

        mInfoContainer = (RelativeLayout) baseView.findViewById(R.id.generic_celltv_info_container);
        openWhenFocused = new OpenWhenFocused(context, mInfoContainer);
        mInfoBorder = (FrameLayout) baseView.findViewById(R.id.generic_cell_info_border);

        TextView infoTitle = (TextView) baseView.findViewById(R.id.generic_celltv_info_title);
        infoTitle.setTypeface(latoSemi);
        if (getCard().getTitle() != null) {
            infoTitle.setText(getCard().getTitle());
        } else {
            infoTitle.setText("");
        }

        TextView infoTxt = (TextView) baseView.findViewById(R.id.generic_celltv_info_text);
        infoTxt.setTypeface(latoRegular);
        if (getCard().getInfo()!=null) {
            CardContainer container = getContainer(TEXT.getValue(), getCard().getInfo().toArray(new CardContainer[getCard().getInfo().size()]));
            if (container != null) {
                infoTxt.setText(((Text) container).getData().get(0).getText());
            } else {
                infoTxt.setText("");
            }
        }


        mSeeMoreContainer = (CarouselTVCellButton) baseView.findViewById(R.id.generic_cell_info_seemore);
        mSeeMoreContainer.setTypeface(latoSemi);
        mSeeMoreContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card card = getCard();
                mActivityListener.onCallCardDetail(card.getCardId(), card.getVersion(), Card.TypeEnum.valueOf((card.getType().name())));
                if (cellLayout != null && mCarouselInterface != null)
                    mCarouselInterface.clickedView(cellLayout);
            }
        });

      /*  mLikeContainer = (CarouselTVCellLinear) baseView.findViewById(R.id.generic_cell_info_liked);
        mLikeIcon = (ImageView) baseView.findViewById(R.id.generic_cell_info_liked_icon);
        mLikeTxt = (TextView) baseView.findViewById(R.id.generic_cell_info_liked_txt);
        mLikeTxt.setTypeface(latoSemi);

        if (getCard().getUser() != null && getCard().getUser().getIsLiked()) {
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
                        if (isLiked) {
                            mLikeIcon.setImageResource(R.drawable.ico_cell_like_mass);
                            mLikeTxt.setText(context.getString(R.string.song_cell_saved));
                            getCard().getMiniCard().getUser().setIsLiked(true);
                        } else {
                            mLikeIcon.setImageResource(R.drawable.ico_cell_like);
                            mLikeTxt.setText(context.getString(R.string.song_cell_save_for_later));
                            getCard().getMiniCard().getUser().setIsLiked(false);
                            if (mPocketInterface != null)
                                mPocketInterface.setCardDeleted(getCard().getMiniCard().getCardId());
                        }
                        HashMap<String, Boolean> likesHashMap = new HashMap<String, Boolean>();
                        likesHashMap.put(getCard().getMiniCard().getCardId(), isLiked);
                        if (mCarouselInterface != null)
                            mCarouselInterface.setCardsAreLiked(likesHashMap);
                    }


                    @Override
                    public void onSetLikeError(NetworkData response) {

                    }
                };
                if (getCard().getMiniCard().getUser() != null && getCard().getMiniCard().getUser().isLiked()) {
                    RestManager.getInstance().deleteLike(getCard().getMiniCard().getCardId(), mListener);
                } else if (getCard().getMiniCard().getUser() != null && !getCard().getMiniCard().getUser().isLiked()) {
                    RestManager.getInstance().setLike(getCard().getMiniCard().getCardId(), mListener);

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
                        onCellUnFocusedColors(context);
                        onCellUnfocused(context);
                        noImage.setColorFilter(Utils.getColor(context, R.color.warm_grey));
                    }
                }
            }
        });

        mSeeMoreContainer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, final boolean hasfocus) {
                isSeeMoreFocused = hasfocus;
                if (hasfocus) {
                    cellLayout.setFocusable(false);
                    cellLayout.setFocusableInTouchMode(false);

                    onCellFocusedSetColors(context);
                    noImage.setColorFilter(Utils.getColor(context, R.color.white)); // White Tint

                    mInfoContainer.setVisibility(View.VISIBLE);
                    mInfoContainer.animate()
                            .translationX(0);
                } else if (!isLikeBtnFocused) {
                    cellLayout.setFocusable(true);
                    cellLayout.setFocusableInTouchMode(true);

                    onCellUnFocusedColors(context);
                    onCellUnfocused(context);
                    noImage.setColorFilter(Utils.getColor(context, R.color.warm_grey)); // White Tint
                }
            }
        });

/*
        mLikeContainer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, final boolean hasfocus) {
                isLikeBtnFocused = hasfocus;
                if (hasfocus) {
                    cellLayout.setFocusable(false);
                    cellLayout.setFocusableInTouchMode(false);

                    onCellFocusedSetColors(context);
                    noImage.setColorFilter(Utils.getColor(context, R.color.white)); // White Tint

                    mInfoContainer.setVisibility(View.VISIBLE);
                    mInfoContainer.animate()
                            .translationX(0);
                } else if (!isSeeMoreFocused) {
                    cellLayout.setFocusable(true);
                    cellLayout.setFocusableInTouchMode(true);
                    onCellUnfocused(context);
                    noImage.setColorFilter(Utils.getColor(context, R.color.warm_grey)); // White Tint
                }
            }
        });
*/

        return cellLayout;
    }

}
