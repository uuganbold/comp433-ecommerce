import IRequest from "../IRequest";

export default class ProductRequest implements IRequest {
    private name: string;
    private description: string;
    private listPrice: number;
    private availableQuantity: number;
    private sellerId: number;
    private categoryId: number;


    constructor(name: string, description: string, listPrice: number, availableQuantity: number, sellerId: number, categoryId: number) {
        this.name = name;
        this.description = description;
        this.listPrice = listPrice;
        this.availableQuantity = availableQuantity;
        this.sellerId = sellerId;
        this.categoryId = categoryId;
    }
}