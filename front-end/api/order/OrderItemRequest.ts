import IRequest from "../IRequest";

export default class OrderItemRequest implements IRequest {

    public productId: number;
    public quantity: number;


    constructor(productId: number, quantity: number) {
        this.productId = productId;
        this.quantity = quantity;
    }

}