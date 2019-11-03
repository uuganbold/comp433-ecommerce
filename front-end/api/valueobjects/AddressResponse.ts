import IResponse from "../IResponse";

export default class AddressResponse implements IResponse {
    private _id: number;
    private _country: string;
    private _street: string;
    private _unit: string;
    private _city: string;
    private _state: string;
    private _zipcode: number;
    private _phonenumber: string;


    constructor(id: number, country: string, street: string, unit: string, city: string, state: string, zipcode: number, phonenumber: string) {
        this._id = id;
        this._country = country;
        this._street = street;
        this._unit = unit;
        this._city = city;
        this._state = state;
        this._zipcode = zipcode;
        this._phonenumber = phonenumber;
    }


    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
    }

    get country(): string {
        return this._country;
    }

    set country(value: string) {
        this._country = value;
    }

    get street(): string {
        return this._street;
    }

    set street(value: string) {
        this._street = value;
    }

    get unit(): string {
        return this._unit;
    }

    set unit(value: string) {
        this._unit = value;
    }

    get city(): string {
        return this._city;
    }

    set city(value: string) {
        this._city = value;
    }

    get state(): string {
        return this._state;
    }

    set state(value: string) {
        this._state = value;
    }

    get zipcode(): number {
        return this._zipcode;
    }

    set zipcode(value: number) {
        this._zipcode = value;
    }

    get phonenumber(): string {
        return this._phonenumber;
    }

    set phonenumber(value: string) {
        this._phonenumber = value;
    }
}