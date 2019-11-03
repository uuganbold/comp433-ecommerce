import IRequest from "../IRequest";

export default class PaymentRequest implements IRequest {
    private nameOnCard: string;
    private cardNumber: string;
    private expireMonth: number;
    private expireYear: number;
    private addressId: number;

    constructor(nameOnCard: string, cardNumber: string, expireMonth: number, expireYear: number, addressId: number) {
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.expireMonth = expireMonth;
        this.expireYear = expireYear;
        this.addressId = addressId;
    }
}