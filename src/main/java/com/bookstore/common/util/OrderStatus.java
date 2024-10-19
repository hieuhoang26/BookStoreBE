package com.bookstore.common.util;

public enum OrderStatus {
    PROCESSING(1, "Processing"),
    SHIPPING(2, "Shipping"),
    SUCCESSFUL_DELIVERY(3, "Successful"),
    RETURN(4, "Return"),
    CANCELED_ORDER(5, "Canceled");

    private final int code;
    private final String description;

    OrderStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static String fromCode(int code) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getCode() == code) {
                return status.getDescription();
            }
        }
        throw new IllegalArgumentException("Invalid order status: " + code);
    }
}

