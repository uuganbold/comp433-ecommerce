import IRequest from "./IRequest";
import ApiError from "./ApiError";
import Server from "./Server";
import {HttpMethods} from "./HttpMethods";

class Api {

    protected server: Server;

    constructor(server: Server) {
        this.server = server;
    }


    protected async sendRequest<T>(uri: string, method = HttpMethods.GET, request?: IRequest): Promise<T> {
        const response = await this.server.sendRequest(uri, method, request);

        if (!response.ok) {
            const obj = await response.json();
            throw new ApiError(obj.status, obj.message, obj.errors)
            //throw new new ApiError();response.json().then(data=>data as ApiError);
        }

        return response.json().then(data => data as T);
    }

    protected async sendRequestWithVoidResponse<T>(uri: string, method = HttpMethods.GET, request?: IRequest): Promise<void> {
        const response = await this.server.sendRequest(uri, method, request);

        if (!response.ok) {
            const obj = await response.json();
            throw new ApiError(obj.status, obj.message, obj.errors)
            //return response.json().then(data=>data as ApiError);
        }

        return Promise.resolve();
    }

}

export default Api;