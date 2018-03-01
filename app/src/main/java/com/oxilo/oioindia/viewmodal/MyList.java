package com.oxilo.oioindia.viewmodal;

/**
 * Created by root on 8/2/18.
 */

public class MyList {
    private String product_id;
    private String name;
   private String image;


    public MyList(String product_id, String name, String image) {
        this.product_id = product_id;
        this.name = name;
        this.image = image;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
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
