import IRequest from "../IRequest";
import OrderItemRequest from "./OrderItemRequest";

export default class OrderRequest implements IRequest {
    private _customerId: number;
    private _items: Array<OrderItemRequest>;
    private _addressId: number;
    private _paymentId: number;


    constructor(customerId: number, items: Array<OrderItemRequest>, addressId: number, paymentId: number) {
        this._customerId = customerId;
        this._items = items;
        this._addressId = addressId;
        this._paymentId = paymentId;
    }


    get customerId(): number {
        return this._customerId;
    }

    set customerId(value: number) {
        this._customerId = value;
    }

    get items(): Array<OrderItemRequest> {
        return this._items;
    }

    set items(value: Array<OrderItemRequest>) {
        this._items = value;
    }

    get addressId(): number {
        return this._addressId;
    }

    set addressId(value: number) {
        this._addressId = value;
    }

    get paymentId(): number {
        return this._paymentId;
    }

    set paymentId(value: number) {
        this._paymentId = value;
    }
}