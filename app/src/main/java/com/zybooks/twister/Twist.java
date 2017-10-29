package com.zybooks.twister;

import java.net.URI;

public class Twist {
    private int mId;
    private String mName;
    private String mDescription;
    private String mTimeAgo;
    private URI mProfilePicture;// add getter and setter

    public Twist() {}

    public Twist(int id, String name, String description, String genre) {
        mId = id;
        mName = name;
        mDescription = description;
        mTimeAgo = genre;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getmTimeAgo() {
        return mTimeAgo;
    }

    public void setmTimeAgo(String mTimeAgo) {
        this.mTimeAgo = mTimeAgo;
    }

    public URI getmProfilePicture() {
        return mProfilePicture;
    }

    public void setmProfilePicture(URI mProfilePicture) {
        this.mProfilePicture = mProfilePicture;
    }
}
