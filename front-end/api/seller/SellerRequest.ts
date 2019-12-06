import IRequest from "../IRequest";

export default class SellerRequest implements IRequest {
    public name: string;
    public website: string;
    public email: string;


    constructor(name: string, website: string, email: string) {
        this.name = name;
        this.website = website;
        this.email = email;
    }
}