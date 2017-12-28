package sdk.dive.tv.ui.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import sdk.dive.tv.R;
import sdk.dive.tv.ui.Utils;

/**
 * Created by Tagsonomy S.L. on 28/06/2017.
 */

public class FragmentError extends Fragment {
    private FragmentError instance;
    private int errorType;
    private Button mButtonOK;

    private OnFragmentErrorInteractionListener mListener;

    public FragmentError() {

    }

    public static FragmentError newInstance() {
        FragmentError fragment = new FragmentError();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Typeface zonaProSemibold = Utils.getFont(getContext(), Utils.TypeFaces.ZONAPRO_SEMIBOLD);
        Typeface latoSemi = Utils.getFont(getContext(), Utils.TypeFaces.LATO_SEMIBOLD);

        Bundle extras = getArguments();
        if (extras != null) {
            errorType = extras.getInt(Utils.ERROR_TYPE);
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_error, container, false);

        TextView title = (TextView) view.findViewById(R.id.fragment_network_error_title);
        title.setTypeface(zonaProSemibold);
        TextView subTitle = (TextView) view.findViewById(R.id.fragment_network_error_subtitle);
        subTitle.setTypeface(latoSemi);

        mButtonOK = (Button) view.findViewById(R.id.fragment_network_error_button_ok);
        mButtonOK.setTypeface(latoSemi);

        switch (errorType) {
            case Utils.NETWORK_ERROR:
                title.setText(getContext().getResources().getString(R.string.NETWORK_ERROR_TITLE));
                subTitle.setText(getContext().getResources().getString(R.string.NETWORK_ERROR_TEXT));
                break;
            case Utils.TEMPORARY_REDIRECT:
                title.setText(getContext().getResources().getString(R.string.VERSION_OUTDATED_TITLE));
                subTitle.setText(getString(R.string.VERSION_OUTDATED_TEXT));
                break;
            case Utils.PERMANENT_REDIRECT:
                title.setText(getContext().getResources().getString(R.string.VERSION_DEPRECATED_TITLE));
                subTitle.setText(getContext().getResources().getString(R.string.VERSION_DEPRECATED_TEXT));
                break;
            case Utils.GENERIC_ERROR:
                title.setText(getContext().getResources().getString(R.string.GENERIC_ERROR_TITLE));
                subTitle.setText(getContext().getResources().getString(R.string.GENERIC_ERROR_TEXT));
                break;
            case Utils.RE_SCAN_CHANNELS:
                title.setText(getContext().getResources().getString(R.string.ERROR_SCAN_CHANNELS_TITLE));
                subTitle.setText(getContext().getResources().getString(R.string.ERROR_SCAN_CHANNELS_TEXT));
                mButtonOK.setText(getContext().getResources().getString(R.string.ERROR_SCAN_CHANNELS_BTN_SCAN));
                break;
        }

        mButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(errorType!=Utils.RE_SCAN_CHANNELS) {
                    mListener.onFragmentErrorClose();
                }
                else{
                    //TODO SCAN CHANNELS
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mButtonOK.requestFocus();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentErrorInteractionListener) {
            mListener = (OnFragmentErrorInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentErrorInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentErrorInteractionListener {
        void onFragmentErrorClose();
    }

    public void getFocus(){
        if(!isAdded())
            return;

        mButtonOK.requestFocus();
    }
}
