import IResponse from "../IResponse";
import HateoasResponse from "../HateoasResponse";

class CustomerResponse extends HateoasResponse implements IResponse {
    private _id: number;
    private _firstName: string;
    private _lastName: string;
    private _email: string;
    private _phonenumber: string;


    constructor(id: number, firstName: string, lastName: string, email: string, phonenumber: string) {
        super();
        this._id = id;
        this._firstName = firstName;
        this._lastName = lastName;
        this._email = email;
        this._phonenumber = phonenumber;
    }


    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
    }

    get firstName(): string {
        return this._firstName;
    }

    set firstName(value: string) {
        this._firstName = value;
    }

    get lastName(): string {
        return this._lastName;
    }

    set lastName(value: string) {
        this._lastName = value;
    }

    get email(): string {
        return this._email;
    }

    set email(value: string) {
        this._email = value;
    }

    get phonenumber(): string {
        return this._phonenumber;
    }

    set phonenumber(value: string) {
        this._phonenumber = value;
    }
}

export default CustomerResponse;