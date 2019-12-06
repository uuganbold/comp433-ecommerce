import Link from "./Link";
import IResponse from "./IResponse";

export default class HateoasResponse implements IResponse {

    private _links!: Array<Link>;


    get links(): Array<Link> {
        return this._links;
    }

    set links(value: Array<Link>) {
        this._links = value;
    }
}