import IResponse from "../IResponse";

export default class ReviewResponse implements IResponse {
    private _id: number;
    private _productId: number;
    private _customerId: number;
    private _star: number;
    private _comment: string;


    constructor(id: number, productId: number, customerId: number, star: number, comment: string) {
        this._id = id;
        this._productId = productId;
        this._customerId = customerId;
        this._star = star;
        this._comment = comment;
    }


    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
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