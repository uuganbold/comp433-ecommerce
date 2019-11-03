import IRequest from "../IRequest";

export default class CustomerRequest implements IRequest {
    private firstName: string;
    private lastName: string;
    private email: string;
    private phonenumber: string;


    constructor(firstName: string, lastName: string, email: string, phonenumber: string) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phonenumber = phonenumber;
    }


}
