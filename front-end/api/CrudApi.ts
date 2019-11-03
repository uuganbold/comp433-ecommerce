import Api from "./Api";
import IRequest from "./IRequest";
import IResponse from "./IResponse";
import Server from "./Server";
import IListable from "./IListable";
import {HttpMethods} from "./HttpMethods";

export default class CrudApi<Req extends IRequest, Res extends IResponse> extends Api implements IListable<Res> {

    private readonly singular_baseURI: string;
    private readonly plural_baseURI: string;

    constructor(server: Server, singular_baseUri: string, plural_baseUri: string) {
        super(server);
        this.singular_baseURI = singular_baseUri;
        this.plural_baseURI = plural_baseUri;
    }

    public async get(id: number): Promise<Res> {
        return this.sendRequest<Res>(this.singular_baseURI + "/" + id);
    }

    public async create(request: Req): Promise<Res> {
        return this.sendRequest<Res>(this.plural_baseURI, HttpMethods.POST, request);
    }

    public async update(id: number, request: Req): Promise<Res> {
        return this.sendRequest<Res>(this.singular_baseURI + "/" + id, HttpMethods.PUT, request);
    }

    public async delete(id: number): Promise<void> {
        return this.sendRequestWithVoidResponse(this.singular_baseURI + "/" + id, HttpMethods.DELETE);
    }

    public list(): Promise<Array<Res>> {
        return this.sendRequest<Array<Res>>(this.plural_baseURI);
    }

}