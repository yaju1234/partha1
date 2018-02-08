package com.oxilo.oioindia.viewmodal;

/**
 * Created by root on 8/2/18.
 */

public class FaviouriteItem {
    private String product_id;
    private String name;
    private String address1;
    private String address2;
    private String phonenumber1;
    private String phonenumber2;
    private String image;


    public FaviouriteItem(String product_id, String name, String address1, String address2, String phonenumber1, String phonenumber2,String image) {
        this.product_id = product_id;
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.phonenumber1 = phonenumber1;
        this.phonenumber2 = phonenumber2;
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

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPhonenumber1() {
        return phonenumber1;
    }

    public void setPhonenumber1(String phonenumber1) {
        this.phonenumber1 = phonenumber1;
    }

    public String getPhonenumber2() {
        return phonenumber2;
    }

    public void setPhonenumber2(String phonenumber2) {
        this.phonenumber2 = phonenumber2;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
