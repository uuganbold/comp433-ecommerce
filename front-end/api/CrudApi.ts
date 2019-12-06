import IRequest from "./IRequest";
import Server from "./Server";
import IListable from "./IListable";
import {HttpMethods} from "./HttpMethods";
import HateoasResponse from "./HateoasResponse";
import HateoasApi from "./HateoasApi";

export default class CrudApi<Req extends IRequest, Res extends HateoasResponse> extends HateoasApi implements IListable<Res> {

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

    public async getUri(uri: string): Promise<Res> {
        return this.sendURIRequest<Res>(uri);
    }

    public async create(request: Req): Promise<Res> {
        return this.sendRequest<Res>(this.plural_baseURI, HttpMethods.POST, request);
    }

    public async update(id: number, request: Req): Promise<Res> {
        return this.sendRequest<Res>(this.singular_baseURI + "/" + id, HttpMethods.PUT, request);
    }

    public async updateUri(uri: string, request: Req): Promise<Res> {
        return this.sendURIRequest<Res>(uri, HttpMethods.PUT, request);
    }

    public async delete(id: number): Promise<void> {
        return this.sendRequestWithVoidResponse(this.singular_baseURI + "/" + id, HttpMethods.DELETE);
    }

    public async deleteObject(obj: Res): Promise<void> {
        return this.sendURIRequestWithVoidResponse(this.getLink(obj, 'self').href, HttpMethods.DELETE);
    }

    public list(): Promise<Array<Res>> {
        return this.sendRequest<Array<Res>>(this.plural_baseURI);
    }

    public listUri(uri: string): Promise<Array<Res>> {
        return this.sendURIRequest<Array<Res>>(uri);
    }

}