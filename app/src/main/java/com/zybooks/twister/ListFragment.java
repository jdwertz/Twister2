package com.zybooks.twister;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.android.volley.VolleyError;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.ocpsoft.prettytime.PrettyTime;

public class ListFragment extends Fragment {

    // For the activity to implement
    public interface OnTwistSelectedListener {
        void onTwistSelected(Twist twist);
    }


    // Reference to the activity
    private OnTwistSelectedListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.band_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        //Send bands to recycler view
        if(isConnected == false) // check if network, else... look up function to add
        {
            TwistAdapter adapter = new TwistAdapter(TwistDatabase.get(getContext()).getTwists());
            recyclerView.setAdapter(adapter);
        }
        else
        {
            DataFetcher fetcher = new DataFetcher(this.getContext());
            fetcher.getData("/twist/", new DataFetcher.OnTwistsReceivedListener() {
                @Override
                public void onTwistsReceived(ArrayList<Twist> twists) {
                    Log.d("Josh5", "Something");
                    TwistDatabase db = TwistDatabase.get(getContext());
                    TwistAdapter adapter = new TwistAdapter(twists);
                    db.clearAllTwists();
                    recyclerView.setAdapter(adapter);
                    Log.d("Josh5", Integer.toString(twists.size()));
                    for (int i = 0; i < twists.size(); i++) {
                        Log.d("Josh5", Integer.toString(i));;
                        db.addTwist(twists.get(i));
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Josh", error.toString());
                }
            });
        }




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
            }
            catch (Exception j)
            {
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
            Log.d("Josh", "Twist selected ID= "+ mTwist.getId());
            mListener.onTwistSelected(mTwist);
        }
    }

    private class TwistAdapter extends RecyclerView.Adapter<TwistHolder> {

        private List<Twist> mTwists;

        public TwistAdapter(List<Twist> twists) {
            mTwists = twists;
        }

        @Override
        public TwistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TwistHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TwistHolder holder, int position) {
            Twist twist = mTwists.get(position);

            Log.d("Josh", "ID: " + Integer.toString(twist.getId()));

            holder.bind(twist);
        }

        @Override
        public int getItemCount() {
            return mTwists.size();
        }
    }
    //OnAttach
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTwistSelectedListener) {
            mListener = (OnTwistSelectedListener) context;
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


