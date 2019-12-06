import IResponse from "../IResponse";
import ProductResponse from "../product/ProductResponse";

export default class OrderItemResponse implements IResponse {
    private _id: number;
    private _product: ProductResponse;
    private _unitPrice: number;
    private _quantity: number;
    private _value: number;


    constructor(id: number, product: ProductResponse, unitPrice: number, quantity: number, value: number) {
        this._id = id;
        this._product = product;
        this._unitPrice = unitPrice;
        this._quantity = quantity;
        this._value = value;
    }


    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
    }

    get product(): ProductResponse {
        return this._product;
    }

    set product(value: ProductResponse) {
        this._product = value;
    }

    get unitPrice(): number {
        return this._unitPrice;
    }

    set unitPrice(value: number) {
        this._unitPrice = value;
    }

    get quantity(): number {
        return this._quantity;
    }

    set quantity(value: number) {
        this._quantity = value;
    }

    get value(): number {
        return this._value;
    }

    set value(value: number) {
        this._value = value;
    }
}