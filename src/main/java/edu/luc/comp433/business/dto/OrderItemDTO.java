package edu.luc.comp433.business.dto;

import edu.luc.comp433.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private Long id;

    private ProductDTO product;

    private double unitPrice;

    private long quantity;

    public OrderItemDTO(Long id, double unitPrice, long quantity) {
        this.id = id;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public OrderItemDTO(double unitPrice, long quantity) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public OrderItemDTO(long quantity) {
        this.quantity = quantity;
    }

    public OrderItemDTO setProduct(ProductDTO product) {
        this.product = product;
        return this;
    }

    public static OrderItemDTO of(OrderItem orderItem) {
        return new OrderItemDTO(orderItem.getId(), orderItem.getUnitPrice(), orderItem.getQuantity());
    }

    public OrderItem toEntity() {
        OrderItem p = new OrderItem();
        p.setUnitPrice(this.getUnitPrice());
        p.setQuantity(this.getQuantity());
        return p;
    }
}
