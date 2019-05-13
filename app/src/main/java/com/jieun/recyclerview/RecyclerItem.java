package com.jieun.recyclerview;

public class RecyclerItem {

    private String title;
    private String imageUrl;
    private String address;
    private String price;
    private Boolean flag;

    public RecyclerItem(String title, String address, String price, String imageUrl) {
        this.title = title;
        this.address = address;
        this.price = price;
        this.imageUrl = imageUrl;
        this.flag = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address= address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price= price;
    }

    public Boolean getFlag() { return flag;}

    public void setFlag(Boolean flag) { this.flag = flag;}

}
