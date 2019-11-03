import Link from "./Link";

export default class HateoasResponse {

    private _links!: Array<Link>;


    get links(): Array<Link> {
        return this._links;
    }

    set links(value: Array<Link>) {
        this._links = value;
    }
}