package com.zybooks.twister;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;

import java.util.ArrayList;

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
        /*UserDataFetcher fetcher = new UserDataFetcher(getActivity().getApplicationContext());
        fetcher.getData("/twist/", new UserDataFetcher.OnUserReceivedListener() {
            @Override
            public void onUserReceived(ArrayList<Twist> twists) {
                mTwists = twists;
                for (int i = 0; i < mTwists.size(); i++) {
                    mDbHelper.addTwist(mTwists.get(i));
                }
            }
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Josh", error.toString());
            }
        });*/

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        TextView descriptionTextView = (TextView) view.findViewById(R.id.bandDescription);
        descriptionTextView.setText(DetailsActivity.mUsers.get(0).getAbout());

        TextView nameTextView = (TextView) view.findViewById(R.id.bandName);
        nameTextView.setText(mTwist.getName());

        return view;
    }
}
