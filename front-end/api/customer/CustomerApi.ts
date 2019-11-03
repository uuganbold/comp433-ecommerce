import CrudApi from "../CrudApi";
import CustomerRequest from "./CustomerRequest";
import CustomerResponse from "./CustomerResponse";
import Server from "../Server";
import AddressRequest from "../valueobjects/AddressRequest";
import AddressResponse from "../valueobjects/AddressResponse";
import PaymentRequest from "../valueobjects/PaymentRequest";
import PaymentResponse from "../valueobjects/PaymentResponse";
import {HttpMethods} from "../HttpMethods";

export default class CustomerApi extends CrudApi<CustomerRequest, CustomerResponse> {
    private static readonly SINGULAR_BASEURI = "/customer";
    private static readonly PLURAL_BASEURI = "/customers";

    constructor(server: Server) {
        super(server, CustomerApi.SINGULAR_BASEURI, CustomerApi.PLURAL_BASEURI);
    }

    public async addAddress(id: number, request: AddressRequest): Promise<AddressResponse> {
        return this.sendRequest<AddressResponse>(CustomerApi.SINGULAR_BASEURI + "/" + id + "/addresses", HttpMethods.POST, request);
    }

    public async removeAddress(id: number, addressid: number): Promise<void> {
        return this.sendRequestWithVoidResponse(CustomerApi.SINGULAR_BASEURI + "/" + id + "/address/" + addressid,
            HttpMethods.DELETE);
    }

    public async getAddresses(id: number): Promise<Array<AddressResponse>> {
        return this.sendRequest<Array<AddressResponse>>(CustomerApi.SINGULAR_BASEURI + "/" + id + "/addresses");
    }

    public async addPayment(id: number, paymentRequest: PaymentRequest): Promise<PaymentResponse> {
        return this.sendRequest<PaymentResponse>(CustomerApi.SINGULAR_BASEURI + "/" + id + "/payments", HttpMethods.POST, paymentRequest);
    }

    public async removePayment(id: number, paymentId: number): Promise<void> {
        return this.sendRequestWithVoidResponse(CustomerApi.SINGULAR_BASEURI + "/" + id + "/payment/" + paymentId,
            HttpMethods.DELETE);
    }

    public async getPayments(id: number): Promise<Array<PaymentResponse>> {
        return this.sendRequest<Array<PaymentResponse>>(CustomerApi.SINGULAR_BASEURI + "/" + id + "/payments");
    }
}