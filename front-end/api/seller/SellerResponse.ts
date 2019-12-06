import IResponse from "../IResponse";
import HateoasResponse from "../HateoasResponse";

export default class SellerResponse extends HateoasResponse implements IResponse {
    public id: number;
    public name: string;
    public website: string;
    public email: string;


    constructor(id: number, name: string, website: string, email: string) {
        super();
        this.id = id;
        this.name = name;
        this.website = website;
        this.email = email;
    }
}