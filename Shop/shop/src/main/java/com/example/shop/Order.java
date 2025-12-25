package com.example.shop;

import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private String orderNumber;
    private int userId;
    private double totalAmount;
    private String status;
    private String paymentMethod;
    private String shippingAddress;
    private String contactName;
    private String contactPhone;
    private String note;
    private Date createdAt;
    private Date updatedAt;
    private List<OrderItem> items; // 订单商品列表
    private User user; // 关联的用户信息

    public Order() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    // 获取状态中文描述
    public String getStatusText() {
        switch (status) {
            case "pending": return "待付款";
            case "paid": return "已付款";
            case "shipped": return "已发货";
            case "delivered": return "已收货";
            case "cancelled": return "已取消";
            default: return status;
        }
    }

    // 获取状态对应的CSS类
    public String getStatusClass() {
        switch (status) {
            case "pending": return "status-pending";
            case "paid": return "status-paid";
            case "shipped": return "status-shipped";
            case "delivered": return "status-delivered";
            case "cancelled": return "status-cancelled";
            default: return "";
        }
    }
}