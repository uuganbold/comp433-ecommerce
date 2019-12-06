import fetch from 'isomorphic-unfetch';
import Api from "./Api";
import CategoryApi from "./category/CategoryApi";
import CustomerApi from "./customer/CustomerApi";
import SellerApi from "./seller/SellerApi";
import ProductApi from "./product/ProductApi";
import OrderApi from "./order/OrderApi";
import ReviewApi from "./review/ReviewApi";
import {HttpMethods} from "./HttpMethods";

export default class Server {

    private baseUri: string;

    private constructor(baseUri: string) {
        this.baseUri = baseUri;
    }

    public static create(baseUri: string): Server {
        console.log("creating server")
        return new Server(baseUri);
    }

    public getApi<T extends Api>(type: (new(server: Server) => T)): T {
        return new type(this);
    }

    public getApiByName(api: string): Api {
        switch (api) {
            case "categories":
                return new CategoryApi(this);
            case "customers":
                return new CustomerApi(this);
            case "sellers":
                return new SellerApi(this);
            case "products":
                return new ProductApi(this);
            case "reviews":
                return new ReviewApi(this);
            case "orders":
                return new OrderApi(this);
        }
        throw new Error("Cannot find this api:" + api);
    }

    public async sendRequest(uri: string, method: string, body: any): Promise<Response> {
        return await this.sendURIRequest(this.baseUri + uri, method, body);
    }

    public async sendURIRequest(uri: string, method: string, body: any): Promise<Response> {
        if (body && (method.toUpperCase() == HttpMethods.GET || method.toUpperCase() == HttpMethods.DELETE)) {
            throw new Error("This request cannot be send via " + method);
        }

        const requestInfo = {
            method: method
        };
        const header = {
            'Accept': "application/hal+json"
        };
        if (body) {
            // @ts-ignore
            header["Content-Type"] = "application/json";
            // @ts-ignore
            requestInfo["body"] = JSON.stringify(body);
        }
        // @ts-ignore
        requestInfo["headers"] = header;

        return await fetch(uri, requestInfo);
    }
}