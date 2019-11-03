import SellerResponse from "./SellerResponse";
import SellerRequest from "./SellerRequest";
import AddressResponse from "../valueobjects/AddressResponse";
import AddressRequest from "../valueobjects/AddressRequest";
import CrudApi from "../CrudApi";
import Server from "../Server";
import {HttpMethods} from "../HttpMethods";

export default class SellerApi extends CrudApi<SellerRequest, SellerResponse> {

    private static readonly SINGULAR_BASEURI = "/seller";
    private static readonly PLURAL_BASEURI = "/sellers";

    constructor(server: Server) {
        super(server, SellerApi.SINGULAR_BASEURI, SellerApi.PLURAL_BASEURI);
    }

    public async addAddress(id: number, request: AddressRequest): Promise<AddressResponse> {
        return this.sendRequest<AddressResponse>(SellerApi.SINGULAR_BASEURI + "/" + id + "/addresses", HttpMethods.POST, request);
    }

    public async removeAddress(id: number, addressid: number): Promise<void> {
        return this.sendRequestWithVoidResponse(SellerApi.SINGULAR_BASEURI + "/" + id + "/address/" + addressid,
            HttpMethods.DELETE);
    }

    public async getAddress(id: number, addressid: number): Promise<AddressResponse> {
        return this.sendRequest<AddressResponse>(SellerApi.SINGULAR_BASEURI + "/" + id + "/address/" + addressid);
    }

    public async getAddresses(id: number): Promise<Array<AddressResponse>> {
        return this.sendRequest<Array<AddressResponse>>(SellerApi.SINGULAR_BASEURI + "/" + id + "/addresses");
    }

}