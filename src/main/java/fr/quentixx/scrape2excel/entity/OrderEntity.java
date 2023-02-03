package fr.quentixx.scrape2excel.entity;

import com.github.crab2died.annotation.ExcelField;

public class OrderEntity {

    @ExcelField(title = "NUMERO COMMANDE", order = 1)
    private long orderID;

    @ExcelField(title = "STORE NAME", order = 2)
    private String storeName;

    @ExcelField(title = "LASTNAME", order = 3)
    private String lastName;

    @ExcelField(title = "FIRSTNAME", order = 4)
    private String firstName;

    @ExcelField(title = "BOUGHT DATE", order = 5)
    private String boughtDate;

    @ExcelField(title = "DELIVERY DATE", order = 6)
    private String deliveryDate;

    public OrderEntity() {
    }

    public OrderEntity(long orderID, String lastName, String firstName, String boughtDate, String deliveryDate) {
        this.setOrderID(orderID);
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setBoughtDate(boughtDate);
        this.setDeliveryDate(deliveryDate);
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public String getStoreName() { return storeName; }

    public void setStoreName(String storeName) { this.storeName = storeName; }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getBoughtDate() {
        return boughtDate;
    }

    public void setBoughtDate(String boughtDate) {
        this.boughtDate = boughtDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", storeName='" + storeName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", boughDate='" + boughtDate + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                '}';
    }
}
