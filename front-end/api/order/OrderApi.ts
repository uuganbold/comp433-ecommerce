import Api from "../Api";
import Server from "../Server";
import OrderResponse from "./OrderResponse";
import OrderRequest from "./OrderRequest";
import IListable from "../IListable";
import {HttpMethods} from "../HttpMethods";

export default class OrderApi extends Api implements IListable<OrderResponse> {

    private static readonly SINGULAR_URI = "/order";
    private static readonly PLURAL_URI = "/orders";

    constructor(server: Server) {
        super(server);
    }

    public async getOrder(id: number): Promise<OrderResponse> {
        return this.sendRequest<OrderResponse>(OrderApi.SINGULAR_URI + "/" + id);
    }

    public async createOrder(request: OrderRequest): Promise<OrderResponse> {
        return this.sendRequest<OrderResponse>(OrderApi.PLURAL_URI, HttpMethods.POST, request);
    }

    public async cancel(id: number): Promise<OrderResponse> {
        return this.sendRequest<OrderResponse>(OrderApi.SINGULAR_URI + "/" + id + "/cancel", HttpMethods.PUT);
    }

    public async ship(id: number): Promise<OrderResponse> {
        return this.sendRequest<OrderResponse>(OrderApi.SINGULAR_URI + "/" + id + "/ship", HttpMethods.PUT)
    }

    public async deliver(id: number): Promise<OrderResponse> {
        return this.sendRequest<OrderResponse>(OrderApi.SINGULAR_URI + "/" + id + "/deliver", HttpMethods.PUT)
    }

    public async list(): Promise<Array<OrderResponse>> {
        return this.sendRequest<Array<OrderResponse>>(OrderApi.PLURAL_URI);
    }
}