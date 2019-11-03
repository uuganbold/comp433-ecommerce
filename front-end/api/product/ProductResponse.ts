import IResponse from "../IResponse";
import CategoryResponse from "../category/CategoryResponse";
import SellerResponse from "../seller/SellerResponse";

export default class ProductResponse implements IResponse {
    private _id: number;
    private _name: string;
    private _description: string;
    private _listPrice: number;
    private _availableQuantity: number;
    private _category: CategoryResponse;
    private _seller: SellerResponse;


    constructor(id: number, name: string, description: string, listPrice: number, availableQuantity: number, category: CategoryResponse, seller: SellerResponse) {
        this._id = id;
        this._name = name;
        this._description = description;
        this._listPrice = listPrice;
        this._availableQuantity = availableQuantity;
        this._category = category;
        this._seller = seller;
    }


    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
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

    get category(): CategoryResponse {
        return this._category;
    }

    set category(value: CategoryResponse) {
        this._category = value;
    }

    get seller(): SellerResponse {
        return this._seller;
    }

    set seller(value: SellerResponse) {
        this._seller = value;
    }
}