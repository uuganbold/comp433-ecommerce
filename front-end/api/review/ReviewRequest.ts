import IRequest from "../IRequest";

export default class ReviewRequest implements IRequest {
    private productId: number;
    private customerId: number;
    private star: number;
    private comment: string;


    constructor(productId: number, customerId: number, star: number, comment: string) {
        this.productId = productId;
        this.customerId = customerId;
        this.star = star;
        this.comment = comment;
    }
}