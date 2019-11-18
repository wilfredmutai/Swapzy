package com.example.swapzy;

public class Upload {
    private String name;
    private String mImageUrl;
    private String key;

    public Upload(String name, String mImageUrl, String key) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        this.name = name;
        this.mImageUrl = mImageUrl;
        this.key = key;
    }

    public Upload() {
    }

    public String getName() {
        return name;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getKey() {
        return key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public void setKey(String key) {
        this.key = key;
    }
}


