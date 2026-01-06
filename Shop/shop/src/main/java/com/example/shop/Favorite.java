package com.example.shop;

public class Favorite {
    private int id;
    private int userId;
    private int productId;
    private Product product; // 关联商品

    public Favorite() {}
    public Favorite(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}