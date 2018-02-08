package com.oxilo.oioindia.viewmodal;

/**
 * Created by root on 8/2/18.
 */

public class FaviouriteItem {
    String name;
    String image;

    public FaviouriteItem(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
