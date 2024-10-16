package com.sp23.koifishproject.dto;

import com.sp23.koifishproject.model.Order;

public class UpdateOrderStatusDTO {
    private Order.Status status;

    public Order.Status getStatus() {
        return status;
    }

    public void setStatus(Order.Status status) {
        this.status = status;
    }
}
