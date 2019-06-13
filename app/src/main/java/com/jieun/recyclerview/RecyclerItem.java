package com.jieun.recyclerview;

public class RecyclerItem {

    private String title;
    private String imageUrl;
    private String address;
    private String price;
    private String pageNum;


    public RecyclerItem(String title, String address, String price, String imageUrl, String pageNum) {
        this.title = title;
        this.address = address;
        this.price = price;
        this.imageUrl = imageUrl;
        this.pageNum =  pageNum;
    }

    public String getTitle() {
        return title;
    }

    public String getPageNum() { return pageNum; }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }


}
