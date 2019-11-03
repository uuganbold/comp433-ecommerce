import IRequest from "../IRequest";

export default class ProductRequest implements IRequest {
    private _name: string;
    private _description: string;
    private _listPrice: number;
    private _availableQuantity: number;
    private _sellerId: number;
    private _categoryId: number;


    constructor(name: string, description: string, listPrice: number, availableQuantity: number, sellerId: number, categoryId: number) {
        this._name = name;
        this._description = description;
        this._listPrice = listPrice;
        this._availableQuantity = availableQuantity;
        this._sellerId = sellerId;
        this._categoryId = categoryId;
    }


    get name(): string {
        return this._name;
    }

    set name(value: string) {
        this._name = value;
    }

    get description(): string {
        return this._description;
    }

    set description(value: string) {
        this._description = value;
    }

    get listPrice(): number {
        return this._listPrice;
    }

    set listPrice(value: number) {
        this._listPrice = value;
    }

    get availableQuantity(): number {
        return this._availableQuantity;
    }

    set availableQuantity(value: number) {
        this._availableQuantity = value;
    }

    get sellerId(): number {
        return this._sellerId;
    }

    set sellerId(value: number) {
        this._sellerId = value;
    }

    get categoryId(): number {
        return this._categoryId;
    }

    set categoryId(value: number) {
        this._categoryId = value;
    }
}