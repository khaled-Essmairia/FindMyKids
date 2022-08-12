package com.example.firebaseapp;

import android.os.Parcel;
import android.os.Parcelable;

public class ChildRVModal implements Parcelable {
    // creating variables for our different fields.
    private String childName;
    private String childDescription;
    private String childdesc2;
    private String bestSuitedFor;
    private String childImg;
    private String childLink;
    private String childId;


    public String getchildId() {
        return childId;
    }

    public void setchildId(String childId) {
        this.childId = childId;
    }


    // creating an empty constructor.
    public ChildRVModal() {

    }

    protected ChildRVModal(Parcel in) {
        childName = in.readString();
        childId = in.readString();
        childDescription = in.readString();
        childdesc2 = in.readString();
        bestSuitedFor = in.readString();
        childImg = in.readString();
        childLink = in.readString();
    }

    public static final Creator<ChildRVModal> CREATOR = new Creator<ChildRVModal>() {
        @Override
        public ChildRVModal createFromParcel(Parcel in) {
            return new ChildRVModal(in);
        }

        @Override
        public ChildRVModal[] newArray(int size) {
            return new ChildRVModal[size];
        }
    };

    // creating getter and setter methods.
    public String getchildName() {
        return childName;
    }

    public void setchildName(String childName) {
        this.childName = childName;
    }

    public String getchildDescription() {
        return childDescription;
    }

    public void setchildDescription(String childDescription) {
        this.childDescription = childDescription;
    }

    public String getchilddesc2() {
        return childdesc2;
    }

    public void setchilddesc2(String childdesc2) {
        this.childdesc2 = childdesc2;
    }

    public String getBestSuitedFor() {
        return bestSuitedFor;
    }

    public void setBestSuitedFor(String bestSuitedFor) {
        this.bestSuitedFor = bestSuitedFor;
    }

    public String getchildImg() {
        return childImg;
    }

    public void setchildImg(String childImg) {
        this.childImg = childImg;
    }

    public String getchildLink() {
        return childLink;
    }

    public void setchildLink(String childLink) {
        this.childLink = childLink;
    }


    public ChildRVModal(String childId, String childName, String childDescription, String childdesc2, String bestSuitedFor, String childImg, String childLink) {
        this.childName = childName;
        this.childId = childId;
        this.childDescription = childDescription;
        this.childdesc2 = childdesc2;
        this.bestSuitedFor = bestSuitedFor;
        this.childImg = childImg;
        this.childLink = childLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(childName);
        dest.writeString(childId);
        dest.writeString(childDescription);
        dest.writeString(childdesc2);
        dest.writeString(bestSuitedFor);
        dest.writeString(childImg);
        dest.writeString(childLink);
    }
}
