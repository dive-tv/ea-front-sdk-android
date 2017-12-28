package sdk.dive.tv.ui.modules.viewholders;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.touchvie.sdk.model.Card;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.listeners.SectionListener;
import sdk.dive.tv.ui.listeners.TvCardDetailListener;
import sdk.dive.tv.ui.modules.adapters.TVScrollAdapter;
import sdk.dive.tv.ui.modules.adapters.TravelShopAdapter;
import sdk.dive.tv.ui.views.ItemHorizontalOffsetDecoration;

import static android.view.View.VISIBLE;

/**
 * Created by Tagsonomy S.L. on 05/10/2016.
 */

public abstract class HorizontalListHolder extends TvModuleHolder {

    protected TextView mTitle;
    private RecyclerView mList;
    private FrameLayout btnBack;
    private FrameLayout btnNext;
    private int itemCount = 0;
    private int currentItem = 0;
    private int itemThreshold = 2;
    private boolean hasItemDecoration = false;

    public HorizontalListHolder(View itemView) {
        super(itemView);
        this.mTitle = (TextView) itemView.findViewById(R.id.txtv_tv_title);
        this.mList = (RecyclerView) itemView.findViewById(R.id.rv_carousel_list);
        this.btnBack = (FrameLayout) itemView.findViewById(R.id.tv_module_text_btn_back);
        this.btnNext = (FrameLayout) itemView.findViewById(R.id.tv_module_text_btn_next);
    }

    @Override
    public void configure(Card cardData, Picasso picasso, Context context, TvCardDetailListener cardDetailListener, SectionListener sectionListener) {
        mTitle.setTypeface(Utils.getFont(context, Utils.TypeFaces.LATO_REGULAR));
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };

        mList.setLayoutManager(horizontalLayoutManager);

        if (!hasItemDecoration) {
            hasItemDecoration = true;
            mList.addItemDecoration(new ItemHorizontalOffsetDecoration(context.getResources().getDimensionPixelOffset(R.dimen.module_horizontal_list_item_separation)));
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentItem > 0) {
                    currentItem = ((TVScrollAdapter) mList.getAdapter()).stepBack();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentItem < itemCount - itemThreshold) {
                    currentItem = ((TVScrollAdapter) mList.getAdapter()).stepNext();
                }
                if (btnBack.getVisibility() != VISIBLE) {
                    btnBack.setVisibility(VISIBLE);
                }
            }
        });

    }


    protected void setAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, int size) {
        mList.setAdapter(adapter);
        this.itemCount = size;

        if (adapter instanceof TravelShopAdapter)
            itemThreshold = 1;

        if (itemCount <= itemThreshold) {
            btnBack.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
        }
    }

}
