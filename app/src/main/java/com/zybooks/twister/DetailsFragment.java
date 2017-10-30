package com.zybooks.twister;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

    private Twist mTwist;

    public static DetailsFragment newInstance(int bandId) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("twistId", bandId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the band ID from the intent that started DetailsActivity
        int twistId = 1;
        if (getArguments() != null) {
            twistId = getArguments().getInt("twistId");
            Log.d("twistID", Integer.toString(twistId));
        }
        Log.d("Josh", "twistId" + twistId);
        mTwist = TwistDatabase.get(getContext()).getTwist(twistId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        TextView nameTextView = (TextView) view.findViewById(R.id.bandName);
        Log.d("Josh", "I think we crash here?");
        nameTextView.setText(mTwist.getName());

        TextView descriptionTextView = (TextView) view.findViewById(R.id.bandDescription);
        descriptionTextView.setText(mTwist.getDescription());

        return view;
    }
}
