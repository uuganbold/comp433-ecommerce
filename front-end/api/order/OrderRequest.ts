import IRequest from "../IRequest";
import OrderItemRequest from "./OrderItemRequest";

export default class OrderRequest implements IRequest {
    public customerId: number;
    public items: Array<OrderItemRequest>;
    public addressId: number;
    public paymentId: number;


    constructor(customerId: number, items: Array<OrderItemRequest>, addressId: number, paymentId: number) {
        this.customerId = customerId;
        this.items = items;
        this.addressId = addressId;
        this.paymentId = paymentId;
    }

}