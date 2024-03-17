package com.v5foradnroid.userapp.post;

public class Post {
    private int id;
    private String title, details, code, price, StockOrSell, image1, image2, image3 , totalcount , category;

    public Post() {
        // Default constructor required for Firebase
    }

    public Post(int id, String title, String details, String code, String price, String stockOrSell, String image1, String image2, String image3, String totalcount, String category) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.code = code;
        this.price = price;
        StockOrSell = stockOrSell;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.totalcount = totalcount;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStockOrSell() {
        return StockOrSell;
    }

    public void setStockOrSell(String stockOrSell) {
        StockOrSell = stockOrSell;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

