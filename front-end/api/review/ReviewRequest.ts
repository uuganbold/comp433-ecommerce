import IRequest from "../IRequest";

export default class ReviewRequest implements IRequest {
    private _productId: number;
    private _customerId: number;
    private _star: number;
    private _comment: string;


    constructor(productId: number, customerId: number, star: number, comment: string) {
        this._productId = productId;
        this._customerId = customerId;
        this._star = star;
        this._comment = comment;
    }


    get productId(): number {
        return this._productId;
    }

    set productId(value: number) {
        this._productId = value;
    }

    get customerId(): number {
        return this._customerId;
    }

    set customerId(value: number) {
        this._customerId = value;
    }

    get star(): number {
        return this._star;
    }

    set star(value: number) {
        this._star = value;
    }

    get comment(): string {
        return this._comment;
    }

    set comment(value: string) {
        this._comment = value;
    }
}