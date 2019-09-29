package edu.luc.comp433.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {

    private List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem item) {
        int i;
        if ((i = items.indexOf(item)) >= 0) {
            CartItem current = items.get(i);
            current.setQuantity(item.getQuantity() + current.getQuantity());
        } else {
            items.add(item);
        }
    }

    public void removeItem(CartItem item) {
        items.remove(item);
    }

    public void updateItem(CartItem item) {
        int i;
        if ((i = items.indexOf(item)) >= 0) {
            items.remove(i);
            items.add(item);
        } else {
            items.add(item);
        }
    }

    public Order convertToOrder() {
        Order o = new Order();
        for (CartItem i : items) {
            o.addItem(i.convertToOrderItem());
        }
        return o;
    }
}
