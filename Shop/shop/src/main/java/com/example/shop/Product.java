package com.example.shop;

public class Product {
    private int id;
    private String name;
    private String category;
    private String description;
    private double price;
    private double originalPrice;
    private String imageUrl;
    private int stock;
    private int sales;
    private double rating;
    private String brand;
    private String[] tags;

    public Product() {}

    // 全参数构造函数
    public Product(int id, String name, String category, String description,
                   double price, double originalPrice, String imageUrl,
                   int stock, int sales, double rating, String brand, String[] tags) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.originalPrice = originalPrice;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.sales = sales;
        this.rating = rating;
        this.brand = brand;
        this.tags = tags;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(double originalPrice) { this.originalPrice = originalPrice; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public int getSales() { return sales; }
    public void setSales(int sales) { this.sales = sales; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String[] getTags() { return tags; }
    public void setTags(String[] tags) { this.tags = tags; }

    // 计算折扣
    public int getDiscount() {
        if (originalPrice > 0 && price < originalPrice) {
            return (int) ((1 - price / originalPrice) * 100);
        }
        return 0;
    }

    // 判断是否缺货
    public boolean isOutOfStock() {
        return stock <= 0;
    }

    // 获取标签字符串
    public String getTagsString() {
        if (tags == null || tags.length == 0) return "";
        return String.join(", ", tags);
    }
}