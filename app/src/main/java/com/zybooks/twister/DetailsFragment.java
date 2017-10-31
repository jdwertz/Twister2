package com.zybooks.twister;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailsFragment extends Fragment {

    public interface OnTwistSelectedListener {
        void onTwistSelected(int twistId);
    }

    private Twist mTwist;
    private TwistDatabase mDb;
    // Reference to the activity
    private DetailsFragment.OnTwistSelectedListener mListener;

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
        mDb = TwistDatabase.get(getContext());

        // Get the band ID from the intent that started DetailsActivity
        int twistId = 1;
        if (getArguments() != null) {
            twistId = getArguments().getInt("twistId");
            Log.d("twistID", Integer.toString(twistId));
        }
        Log.d("Josh", "twistId" + twistId);
        mTwist = mDb.getTwist(twistId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String username = mTwist.getName();
        User user = mDb.getUser(username);
        String imageURL = "http://cs.harding.edu/fmccown/twister/images/"
                + username + ".jpg";


        View view = inflater.inflate(R.layout.fragment_details, container, false);
        TextView aboutTextView = (TextView) view.findViewById(R.id.aboutUser);
        TextView nameTextView = (TextView) view.findViewById(R.id.username);
        ImageView profilePicture = (ImageView) view.findViewById(R.id.profilePicture);


        new DownloadImageTask(profilePicture)
                .execute(imageURL);
        aboutTextView.setText(user.getAbout());
        nameTextView.setText(mTwist.getName());


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.details_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Send bands to recycler view
        ArrayList<Twist> twists = mDb.getTwists();
        ArrayList<Twist> matchingTwists = new ArrayList<>();

        /*for(int i = 0; i < twists.size(); i++){
            if(twists.get(i).getName().equals(username)){
                matchingTwists.add(twists.get(i));
            }
        }*/
        DetailsFragment.TwistAdapter adapter = new DetailsFragment.TwistAdapter(/*matchingTwists*/ twists);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private class TwistHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Twist mTwist;

        private TextView mNameTextView;
        private TextView mTwistTextView;
        private ImageView mProfilePic;
        private TextView mTimeAgo;

        public TwistHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_band, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = (TextView) itemView.findViewById(R.id.username);
            mTwistTextView = (TextView) itemView.findViewById(R.id.genre);
            mProfilePic = (ImageView) itemView.findViewById(R.id.profilePicture);
            mTimeAgo = (TextView) itemView.findViewById(R.id.timeAgo);
        }

        public void bind(Twist twist) {
            mTwist = twist;
            mNameTextView.setText(mTwist.getName());
            mTwistTextView.setText(mTwist.getDescription());

            String timeAgo = mTwist.getmTimeAgo();

            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date past = format.parse(timeAgo);

                PrettyTime prettyTime = new PrettyTime();
                String formattedTime = prettyTime.format(past);
                Log.d("prettyTime", formattedTime);
                mTimeAgo.setText(formattedTime);
            } catch (Exception j) {
                j.printStackTrace();
            }

            String imageURL = "http://cs.harding.edu/fmccown/twister/images/"
                    + mTwist.getName() + ".jpg";
            new DownloadImageTask(mProfilePic)
                    .execute(imageURL);

            Log.d("Josh", "End of bind(Twist)");
        }

        @Override
        public void onClick(View view) {
            Log.d("Josh", "onClick");
            // Tell ListActivity what band was clicked
            //String whatever = mTwist.getDescription();
            Log.d("Josh", "Twist selected ID= " + mTwist.getId());
            mListener.onTwistSelected(mTwist.getId());
        }
    }

    private class TwistAdapter extends RecyclerView.Adapter<DetailsFragment.TwistHolder> {

        private List<Twist> mTwists;

        public TwistAdapter(List<Twist> twists) {
            mTwists = twists;
        }

        @Override
        public DetailsFragment.TwistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DetailsFragment.TwistHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(DetailsFragment.TwistHolder holder, int position) {
            Twist twist = mTwists.get(position);

            Log.d("Josh", "ID: " + Integer.toString(twist.getId()));

            holder.bind(twist);
        }

        @Override
        public int getItemCount() {
            return mTwists.size();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DetailsFragment.OnTwistSelectedListener) {
            mListener = (DetailsFragment.OnTwistSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTwistSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}

