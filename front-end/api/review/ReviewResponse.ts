import IResponse from "../IResponse";
import ProductResponse from "../product/ProductResponse";
import CustomerResponse from "../customer/CustomerResponse";

export default class ReviewResponse implements IResponse {

    private id: number;
    private star: number;
    private comment: string;
    private date: Date;
    private productResponse: ProductResponse;
    private customerResponse: CustomerResponse;


    constructor(id: number, productId: number, customerId: number, star: number, comment: string, date: Date, productResponse: ProductResponse, customerResponse: CustomerResponse) {
        this.id = id;
        this.star = star;
        this.comment = comment;
        this.date = date;
        this.productResponse = productResponse;
        this.customerResponse = customerResponse

    }


    get getId(): number {
        return this.id;
    }

    set setId(value: number) {
        this.id = value;
    }

    get productId(): number {
        return this.productId;
    }

    set productId(value: number) {
        this.productId = value;
    }

    get customerId(): number {
        return this.customerId;
    }

    set customerId(value: number) {
        this.customerId = value;
    }

    get getStar(): number {
        return this.star;
    }

    set setStar(value: number) {
        this.star = value;
    }

    get getComment(): string {
        return this.comment;
    }

    set setComment(value: string) {
        this.comment = value;
    }

    get getCustomerReponse(): CustomerResponse {
        return this.customerResponse;
    }

    set setCustomerReponse(value: CustomerResponse) {
        this.customerResponse = value;
    }
    get getProductResponse(): ProductResponse {
        return this.productResponse;
    }
    set setProductResponse(value: ProductResponse) {
        this.productResponse = value;
    }

    get getDate(): Date {
        return this.date;
    }

    set setDate(value: Date) {
        this.date = value;
    }
}