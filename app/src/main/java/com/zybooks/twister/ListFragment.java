package com.zybooks.twister;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;

public class ListFragment extends Fragment {

    // For the activity to implement
    public interface OnTwistSelectedListener {
        void onTwistSelected(int twistId);
    }


    // Reference to the activity
    private OnTwistSelectedListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.band_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //Send bands to recycler view
        TwistAdapter adapter = new TwistAdapter(TwistDatabase.get(getContext()).getTwists());
        recyclerView.setAdapter(adapter);

        return view;
    }

    private class TwistHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Twist mTwist;

        private TextView mNameTextView;
        private TextView mTwistTextView;
        private ImageView mProfilePic;

        public TwistHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_band, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = (TextView) itemView.findViewById(R.id.bandName);
            mTwistTextView = (TextView) itemView.findViewById(R.id.genre);
            mProfilePic = (ImageView) itemView.findViewById(R.id.profilePicture);
        }

        public void bind(Twist twist) {
            mTwist = twist;
            mNameTextView.setText(mTwist.getName());
            mTwistTextView.setText(mTwist.getDescription());
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
            mListener.onTwistSelected(mTwist.getId());
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

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Notify activity of band selection
            String twistId = (String) view.getTag();
            mListener.onTwistSelected(Integer.parseInt(twistId));
        }
    };
}


