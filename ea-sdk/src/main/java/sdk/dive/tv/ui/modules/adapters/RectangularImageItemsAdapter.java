package sdk.dive.tv.ui.modules.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;

import java.util.ArrayList;

import sdk.dive.tv.R;
import sdk.dive.tv.eventbus.EventBusManager;
import sdk.dive.tv.eventbus.EventBusIds;
import sdk.dive.tv.eventbus.OpenCard;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.data.RectangularImageRowData;
import sdk.dive.tv.ui.other.CustomTypefaceSpan;


/**
 * Created by Tagsonomy S.L. on 14/10/2016.
 */

public class RectangularImageItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<RectangularImageRowData> rows;
    private boolean imageTextSpace = false;
    private Picasso mPicasso;
    private Typeface latoRegular;
    private Typeface latoSemibold;
    private Context context;
    private String parentId;
    private String parentType;
    private final TvCardDetailListener tvCardDetailListener;

    public RectangularImageItemsAdapter(Context context, ArrayList<RectangularImageRowData> rows, String cardId, TvCardDetailListener tvCardDetailListener) {
        super();
        this.rows = rows;
        this.context = context;
        this.parentId = cardId;
        this.tvCardDetailListener = tvCardDetailListener;
        mPicasso = Picasso.with(context);
        latoRegular = Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR);
        latoSemibold = Utils.getFont(context, Utils.TypeFaces.LATO_SEMIBOLD);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rectangular_image_item_row, parent, false);
        return new RectangularImageItemsItemHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final RectangularImageRowData rowItem = rows.get(holder.getAdapterPosition());

        mPicasso.load(rowItem.getImage())
                .into(((RectangularImageItemsItemHolder) holder).image);

        if (rowItem.getTitle() != null) {
            String title = "";
            for (int i = 0; i < rowItem.getTitle().size(); i++) {
                if (rowItem.getTitle().get(i).getText() != null && rowItem.getTitle().get(i).getText().length() > 0)
                    title = title + rowItem.getTitle().get(i).getText();
            }

            Spannable spannableTitle = new SpannableString(title);
            ((RectangularImageItemsItemHolder) holder).title.setText(spannableTitle.toString());
//            ((RectangularImageItemsItemHolder) holder).title.setOnTouchListener(new TouchTextView(spannableTitle));
            ((RectangularImageItemsItemHolder) holder).title.setTypeface(latoRegular);
        }

        if (rowItem.getSubtitle() != null) {

            if (rowItem.getSubtitle().size() > 0 && !"".equals(rowItem.getSubtitle().get(0).getText())) {
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < rowItem.getSubtitle().size(); i++) {
                    sb.append(rowItem.getSubtitle().get(i).getText());
                }

                SpannableString spannableString = new SpannableString(sb.toString());

                spannableString.setSpan(new CustomTypefaceSpan(latoRegular), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                for (int i = 0; i < rowItem.getSubtitle().size(); i++) {
                    if (rowItem.getSubtitle().get(i).getCardId() == null || !rowItem.getSubtitle().get(i).isHasContent()) {
                        ((RectangularImageItemsItemHolder) holder).subtitle.setTextColor(context.getResources().getColor(R.color.warmGrey));
                        continue;
                    }
                    spannableString.setSpan(new sdk.dive.tv.ui.modules.adapters.RectangularImageItemsAdapter.ClickableElement(rowItem.getSubtitle().get(i).getCardId(), rowItem.getSubtitle().get(i).getCardVersion(), rowItem.getSubtitle().get(i).getCardType()), spannableString.toString().indexOf(rowItem.getSubtitle().get(i).getText()),
                            spannableString.toString().indexOf(rowItem.getSubtitle().get(i).getText()) + rowItem.getSubtitle().get(i).getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new ForegroundColorSpan(Utils.getColor(context, R.color.seafoamBlue)), spannableString.toString().indexOf(rowItem.getSubtitle().get(i).getText()),
                            spannableString.toString().indexOf(rowItem.getSubtitle().get(i).getText()) + rowItem.getSubtitle().get(i).getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
//                ((RectangularImageItemsItemHolder) holder).subtitle.setMovementMethod(LinkMovementMethod.getInstance());
                ((RectangularImageItemsItemHolder) holder).subtitle.setOnTouchListener(new TouchTextView(spannableString));
                ((RectangularImageItemsItemHolder) holder).subtitle.setText(spannableString);
            } else {
                ((RectangularImageItemsItemHolder) holder).subtitle.setText("");
            }
        } else {
            ((RectangularImageItemsItemHolder) holder).subtitle.setText("");
        }

        ((RectangularImageItemsItemHolder) holder).image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rowItem.isHasContent()) {
//                    OpenCard openCard = new OpenCard(EventBusIds.OPEN_CARD.getName(), rowItem.getCardId(), rowItem.getType());
//                    EventBusManager.getInstance().post(openCard);
                    tvCardDetailListener.onCallCardDetail(rowItem.getCardId(), rowItem.getCardVersion(), Card.TypeEnum.fromValue(rowItem.getType()));
                }
            }
        });

    }

    static class TouchTextView implements View.OnTouchListener {
        Spannable spannable;

        public TouchTextView(Spannable spannable) {
            this.spannable = spannable;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            if (!(v instanceof TextView)) {
                return false;
            }
            TextView textView = (TextView) v;
            if (action == MotionEvent.ACTION_UP ||
                    action == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= textView.getTotalPaddingLeft();
                y -= textView.getTotalPaddingTop();

                x += textView.getScrollX();
                y += textView.getScrollY();

                Layout layout = textView.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                ClickableSpan[] link = spannable.getSpans(off, off, ClickableSpan.class);

                if (link.length != 0) {
                    if (action == MotionEvent.ACTION_UP) {
                        link[0].onClick(textView);
                    } else if (action == MotionEvent.ACTION_DOWN) {
                        Selection.setSelection(spannable,
                                spannable.getSpanStart(link[0]),
                                spannable.getSpanEnd(link[0]));
                    }

                    return true;
                } else {
                    Selection.removeSelection(spannable);
                }
            }

            return false;
        }
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    public void setImageTextSpace(boolean space) {

        this.imageTextSpace = space;
    }

    public static class RectangularImageItemsItemHolder extends RecyclerView.ViewHolder {

        ImageView image;

        TextView title;

        TextView subtitle;

        public RectangularImageItemsItemHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.rimageitems_image);
            title = (TextView) v.findViewById(R.id.rimageitems_title);
            subtitle = (TextView) v.findViewById(R.id.rimageitems_subtitle);
        }
    }

    private class ClickableElement extends ClickableSpan {
        String clicked;
        String version;
        String cardType;

        public ClickableElement(String string, String version, String type) {
            super();
            clicked = string;
            version = version;
            cardType = type;
        }

        public void onClick(View tv) {
//            OpenCard openCard = new OpenCard(EventBusIds.OPEN_CARD.getName(), clicked, cardType);
//            EventBusManager.getInstance().post(openCard);
            tvCardDetailListener.onCallCardDetail(clicked, version, Card.TypeEnum.fromValue(cardType));
        }

        public void updateDrawState(TextPaint ds) {// override updateDrawState
            ds.setUnderlineText(false); // set to false to remove underline
        }
    }


}


