import IResponse from "../IResponse";
import ProductResponse from "../product/ProductResponse";
import CustomerResponse from "../customer/CustomerResponse";
import HateoasResponse from "../HateoasResponse";

export default class ReviewResponse extends HateoasResponse implements IResponse {

    public id: number;
    public star: number;
    public comment: string;
    public date: Date;
    public product: ProductResponse;
    public customer: CustomerResponse;


    constructor(id: number, productId: number, customerId: number, star: number, comment: string, date: Date, productResponse: ProductResponse, customerResponse: CustomerResponse) {
        super();
        this.id = id;
        this.star = star;
        this.comment = comment;
        this.date = date;
        this.product = productResponse;
        this.customer = customerResponse
    }
}