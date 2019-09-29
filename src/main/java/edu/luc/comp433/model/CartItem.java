package edu.luc.comp433.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Product product;

    private double unitPrice;

    private long quantity;

    public OrderItem convertToOrderItem() {
        OrderItem o = new OrderItem();
        o.setProduct(this.product);
        o.setQuantity(this.quantity);
        o.setUnitPrice(this.unitPrice);
        return o;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartItem cartItem = (CartItem) o;

        return product.equals(cartItem.product);
    }

    @Override
    public int hashCode() {
        return product.hashCode();
    }
}
