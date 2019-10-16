package edu.luc.comp433.business.dto;

import edu.luc.comp433.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {


    private Long id;

    private CustomerDTO customer;

    private Instant date;

    private Instant updateDate;

    private List<OrderItemDTO> items = new ArrayList<>();

    private String status;

    private AddressDTO address;

    private PaymentDTO payment;

    private double totalValue;

    public OrderDTO setCustomer(CustomerDTO customer) {
        this.customer = customer;
        return this;
    }

    public OrderDTO setItems(List<OrderItemDTO> items) {
        this.items = items;
        return this;
    }

    public void addItem(OrderItemDTO item) {
        this.items.add(item);
    }

    public OrderDTO setAddress(AddressDTO address) {
        this.address = address;
        return this;
    }

    public OrderDTO setPayment(PaymentDTO payment) {
        this.payment = payment;
        return this;
    }

    public OrderDTO(Long id, Instant date, Instant updateDate, String status, double totalValue) {
        this.id = id;
        this.date = date;
        this.updateDate = updateDate;
        this.status = status;
        this.totalValue = totalValue;
    }

    public static OrderDTO of(Order order) {
        return new OrderDTO(order.getId(), order.getDate(), order.getUpdateDate(), order.getStatus().name(), order.getTotalValue());
    }

    public Order toEntity() {
        return new Order();
    }
}
