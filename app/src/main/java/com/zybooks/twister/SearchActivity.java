package com.zybooks.twister;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import org.ocpsoft.prettytime.PrettyTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    // For the activity to implement
    public interface OnTwistSelectedListener {
        void onTwistSelected(Twist twist);
    }

    // Reference to the activity
    private OnTwistSelectedListener mListener;
    private EditText mSearchText;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mSearchText = (EditText) findViewById(R.id.inputSearchText);
        mContext = this;

        // Display "UP" button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void onSearchClicked(View view){
        ArrayList<Twist> twists = new ArrayList<>();
        String searchString = mSearchText.getText().toString();
        twists = TwistDatabase.get(this).search(searchString);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TwistAdapter adapter = new TwistAdapter(twists);
        recyclerView.setAdapter(adapter);
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
        }

        @Override
        public void onClick(View view) {
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
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            return new TwistHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TwistHolder holder, int position) {
            Twist twist = mTwists.get(position);
            holder.bind(twist);
        }

        @Override
        public int getItemCount() {
            return mTwists.size();
        }
    }

    //public void onAttach(Context context) {
    //    onAttach(context);
    //    if (context instanceof SearchActivity.OnTwistSelectedListener) {
    //        mListener = (SearchActivity.OnTwistSelectedListener) context;
    //    } else {
    //        throw new RuntimeException(context.toString()
    //                + " must implement OnTwistSelectedListener");
    //    }
    //}

    //public void onDetach() {
    //    onDetach();
    //    mListener = null;
    //}
}
