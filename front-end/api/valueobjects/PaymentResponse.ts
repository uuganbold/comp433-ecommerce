import HateoasResponse from "../HateoasResponse";
import IResponse from "../IResponse";
import AddressResponse from "./AddressResponse";

export default class PaymentResponse extends HateoasResponse implements IResponse {
    private _id: number;
    private _nameOnCard: string;
    private _cardNumber: string;
    private _expireMonth: number;
    private _expireYear: number;
    private _billingAddress: AddressResponse;


    constructor(id: number, nameOnCard: string, cardNumber: string, expireMonth: number, expireYear: number, billingAddress: AddressResponse) {
        super();
        this._id = id;
        this._nameOnCard = nameOnCard;
        this._cardNumber = cardNumber;
        this._expireMonth = expireMonth;
        this._expireYear = expireYear;
        this._billingAddress = billingAddress;
    }


    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
    }

    get nameOnCard(): string {
        return this._nameOnCard;
    }

    set nameOnCard(value: string) {
        this._nameOnCard = value;
    }

    get cardNumber(): string {
        return this._cardNumber;
    }

    set cardNumber(value: string) {
        this._cardNumber = value;
    }

    get expireMonth(): number {
        return this._expireMonth;
    }

    set expireMonth(value: number) {
        this._expireMonth = value;
    }

    get expireYear(): number {
        return this._expireYear;
    }

    set expireYear(value: number) {
        this._expireYear = value;
    }

    get billingAddress(): AddressResponse {
        return this._billingAddress;
    }

    set billingAddress(value: AddressResponse) {
        this._billingAddress = value;
    }
}