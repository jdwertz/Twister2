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
import android.widget.Toast;
import com.android.volley.VolleyError;
import org.ocpsoft.prettytime.PrettyTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailsFragment extends Fragment {

    private String mUsername;

    public interface OnTwistSelectedListener {
        void onTwistSelected(int twistId);
    }

    private TwistDatabase mDb;
    // Reference to the activity
    private DetailsFragment.OnTwistSelectedListener mListener;

    public static DetailsFragment newInstance(String username) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString("username", username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDb = TwistDatabase.get(getContext());

        String username;
        if (getArguments() != null) {
            mUsername = getArguments().getString("username");
            Log.d("username", mUsername);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String imageURL = "http://cs.harding.edu/fmccown/twister/images/"
                + mUsername + ".jpg";
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        final TextView aboutTextView = (TextView) view.findViewById(R.id.aboutUser);
        final TextView nameTextView = (TextView) view.findViewById(R.id.username);
        ImageView profilePicture = (ImageView) view.findViewById(R.id.profilePicture);
        new DownloadImageTask(profilePicture)
                .execute(imageURL);

        UserDataFetcher fetcher = new UserDataFetcher(this.getContext());
        fetcher.getData("/user/" + mUsername, new UserDataFetcher.OnUserReceivedListener() {
            @Override
            public void onUserReceived(User user) {
                aboutTextView.setText(user.getAbout());
                nameTextView.setText(user.getUsername());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailsFragment.this.getContext(), "Problem getting username!", Toast.LENGTH_LONG).show();
                Log.e("error", error.getMessage());
            }
        });

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.details_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DataFetcher dataFetcher = new DataFetcher(this.getContext());
        dataFetcher.getData("/twist/" + mUsername, new DataFetcher.OnTwistsReceivedListener() {
            @Override
            public void onTwistsReceived(ArrayList<Twist> twists) {
                DetailsFragment.TwistAdapter adapter = new DetailsFragment.TwistAdapter(twists);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Josh", error.toString());
            }
        });

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
            mTwistTextView = (TextView) itemView.findViewById(R.id.aboutText);
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
        }

        @Override
        public void onClick(View view) {
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

