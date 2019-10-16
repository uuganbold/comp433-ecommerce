package edu.luc.comp433.api.payload;

import edu.luc.comp433.business.dto.OrderItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "item")
public class OrderItemRepresentation {

    private Long id;

    private ProductRepresentation product;

    private double unitPrice;

    private long quantity;

    private double value;

    public OrderItemRepresentation(Long id, double unitPrice, long quantity) {
        this.id = id;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.value = unitPrice * quantity;
    }

    public static OrderItemRepresentation of(OrderItemDTO dto) {
        OrderItemRepresentation item = new OrderItemRepresentation(dto.getId(), dto.getUnitPrice(), dto.getQuantity());
        if (dto.getProduct() != null) item.setProduct(ProductRepresentation.of(dto.getProduct()));
        return item;
    }
}
