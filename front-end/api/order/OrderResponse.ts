import IResponse from "../IResponse";
import CustomerResponse from "../customer/CustomerResponse";
import OrderItemResponse from "./OrderItemResponse";
import AddressResponse from "../valueobjects/AddressResponse";

export default class OrderResponse implements IResponse {
    private _id: number;
    private _customer: CustomerResponse;
    private _date: Date;
    private _updateDate: Date;
    private _items: Array<OrderItemResponse>;
    private _status: string;
    private _address: AddressResponse;
    private _payment: PaymentResponse;
    private _totalValue: number;


    constructor(id: number, customer: CustomerResponse, date: Date, updateDate: Date, items: Array<OrderItemResponse>, status: string, address: AddressResponse, payment: PaymentResponse, totalValue: number) {
        this._id = id;
        this._customer = customer;
        this._date = date;
        this._updateDate = updateDate;
        this._items = items;
        this._status = status;
        this._address = address;
        this._payment = payment;
        this._totalValue = totalValue;
    }


    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
    }

    get customer(): CustomerResponse {
        return this._customer;
    }

    set customer(value: CustomerResponse) {
        this._customer = value;
    }

    get date(): Date {
        return this._date;
    }

    set date(value: Date) {
        this._date = value;
    }

    get updateDate(): Date {
        return this._updateDate;
    }

    set updateDate(value: Date) {
        this._updateDate = value;
    }

    get items(): Array<OrderItemResponse> {
        return this._items;
    }

    set items(value: Array<OrderItemResponse>) {
        this._items = value;
    }

    get status(): string {
        return this._status;
    }

    set status(value: string) {
        this._status = value;
    }

    get address(): AddressResponse {
        return this._address;
    }

    set address(value: AddressResponse) {
        this._address = value;
    }

    get payment(): PaymentResponse {
        return this._payment;
    }

    set payment(value: PaymentResponse) {
        this._payment = value;
    }

    get totalValue(): number {
        return this._totalValue;
    }

    set totalValue(value: number) {
        this._totalValue = value;
    }
}