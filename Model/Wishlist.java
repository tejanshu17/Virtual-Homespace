package com.example.login2.Model;

public class Wishlist {
    private String pid, pname, price, discount, image;

    public Wishlist() {

    }

    public Wishlist(String pid, String pname, String price, String discount, String image) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.discount = discount;
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}