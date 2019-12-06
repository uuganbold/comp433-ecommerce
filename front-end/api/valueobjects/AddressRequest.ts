import IRequest from "../IRequest";

export default class AddressRequest implements IRequest {
    public country: string;
    public street: string;
    public unit: string;
    public city: string;
    public state: string;
    public zipcode: number;
    public phonenumber: string;


    constructor(country: string, street: string, unit: string, city: string, state: string, zipcode: number, phonenumber: string) {
        this.country = country;
        this.street = street;
        this.unit = unit;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.phonenumber = phonenumber;
    }
}