package com.example.customview;


import android.graphics.drawable.Drawable;

public class regionData {

    private String region;
    Drawable regionImgID;


    public regionData(String region, Drawable regionImgID) {

        this.region = region;
        this.regionImgID = regionImgID;
    }

    public String getregion() {
        return region;
    }

    public void setregion(String region){

        this.region = region;
    }



    public Drawable getregionImgID(){

        return regionImgID;
    }

    public void setregionImgID(Drawable regionImgID){

        this.regionImgID = regionImgID;
    }

}