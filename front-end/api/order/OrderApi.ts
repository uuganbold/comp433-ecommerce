import Server from "../Server";
import OrderResponse from "./OrderResponse";
import OrderRequest from "./OrderRequest";
import IListable from "../IListable";
import {HttpMethods} from "../HttpMethods";
import HateoasApi from "../HateoasApi";

export default class OrderApi extends HateoasApi implements IListable<OrderResponse> {

    private static readonly SINGULAR_URI = "/order";
    private static readonly PLURAL_URI = "/orders";

    constructor(server: Server) {
        super(server);
    }

    public async get(id: number): Promise<OrderResponse> {
        return this.sendRequest<OrderResponse>(OrderApi.SINGULAR_URI + "/" + id);
    }

    public async getUri(uri: string): Promise<OrderResponse> {
        return this.sendURIRequest<OrderResponse>(uri);
    }

    public async create(request: OrderRequest): Promise<OrderResponse> {
        return this.sendRequest<OrderResponse>(OrderApi.PLURAL_URI, HttpMethods.POST, request);
    }

    public async createUri(uri: string, request: OrderRequest): Promise<OrderResponse> {
        return this.sendURIRequest<OrderResponse>(uri, HttpMethods.POST, request);
    }

    public async cancel(order: OrderResponse): Promise<OrderResponse> {
        return this.sendURIRequest<OrderResponse>(this.getLink(order, 'cancel').href, HttpMethods.PUT);
    }

    public async ship(order: OrderResponse): Promise<OrderResponse> {
        return this.sendURIRequest<OrderResponse>(this.getLink(order, 'ship').href, HttpMethods.PUT)
    }

    public async deliver(order: OrderResponse): Promise<OrderResponse> {
        return this.sendURIRequest<OrderResponse>(this.getLink(order, 'deliver').href, HttpMethods.PUT)
    }

    public async list(): Promise<Array<OrderResponse>> {
        return this.sendRequest<Array<OrderResponse>>(OrderApi.PLURAL_URI);
    }

    public listUri(uri: string): Promise<Array<OrderResponse>> {
        return this.sendURIRequest<Array<OrderResponse>>(uri);
    }
}