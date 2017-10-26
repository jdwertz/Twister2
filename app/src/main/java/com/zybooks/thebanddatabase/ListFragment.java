package com.zybooks.thebanddatabase;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListFragment extends Fragment {

    // For the activity to implement
    public interface OnBandSelectedListener {
        void onBandSelected(int bandId);
    }

    // Reference to the activity
    private OnBandSelectedListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.band_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //Send bands to recycler view
        //TwistAdapter adapter = new TwistAdapter(BandDatabase.get(getContext()).getBands());
        //recyclerView.setAdapter(adapter);

        return view;
    }

    private class BandHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Twist mTwist;

        private TextView mNameTextView;
        private TextView mGenreTextView;

        public BandHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_band, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = (TextView) itemView.findViewById(R.id.bandName);
            mGenreTextView = (TextView) itemView.findViewById(R.id.genre);
        }

        public void bind(Twist twist) {
            mTwist = twist;
            mNameTextView.setText(mTwist.getName());
            //mGenreTextView.setText(mTwist.getGenre());
        }

        @Override
        public void onClick(View view) {
            // Tell ListActivity what band was clicked
            mListener.onBandSelected(mTwist.getId());
        }
    }

    private class TwistAdapter extends RecyclerView.Adapter<BandHolder> {

        private List<Twist> mTwists;

        public TwistAdapter(List<Twist> twists) {
            mTwists = twists;
        }

        @Override
        public BandHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new BandHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(BandHolder holder, int position) {
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
        if (context instanceof OnBandSelectedListener) {
            mListener = (OnBandSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBandSelectedListener");
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
            String bandId = (String) view.getTag();
            mListener.onBandSelected(Integer.parseInt(bandId));
        }
    };
}
