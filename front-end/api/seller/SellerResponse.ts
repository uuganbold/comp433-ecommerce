import IResponse from "../IResponse";

export default class SellerResponse implements IResponse {
    public id: number;
    public name: string;
    public website: string;
    public email: string;


    constructor(id: number, name: string, website: string, email: string) {
        this.id = id;
        this.name = name;
        this.website = website;
        this.email = email;
    }
}