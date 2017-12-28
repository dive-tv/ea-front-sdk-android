package sdk.dive.tv.ui.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.CardContainer;
import com.touchvie.sdk.model.Text;
import com.touchvie.sdk.model.TextData;

import sdk.dive.tv.ui.modules.viewholders.ReferenceHolder;

import static com.touchvie.sdk.model.Text.ContentTypeEnum.REFERENCE;

/**
 * Created by Tagsonomy S.L. on 04/10/2016.
 */

public class Reference extends TextModule {
    /**
     * Default constructor
     */
    public Reference() {
        super();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(this.layout, group, false);
        ReferenceHolder holder = new ReferenceHolder(viewGroup, getClass().getSimpleName());
        return holder;
    }

    @Override
    public boolean validate(Card cardData, boolean isCarousel) {
        CardContainer container = getContainer(cardData, REFERENCE.getValue());
        if (container == null || cardData.getTitle() == null)
            return false;

        Text description = (Text) container;
        if (description.getData() != null && description.getData().size() == 1) {
            TextData containerText = description.getData().get(0);
            if (containerText == null || containerText.getText() == null || containerText.getText().length() == 0)
                return false;
        }

        return true;
    }
}
