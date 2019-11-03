import CrudApi from "../CrudApi";
import ProductRequest from "./ProductRequest";
import ProductResponse from "./ProductResponse";
import Server from "../Server";

export default class ProductApi extends CrudApi<ProductRequest, ProductResponse> {

    private static readonly SINGULAR_BASEURI = "/product";
    private static readonly PLURAL_BASEURI = "/products";

    constructor(server: Server) {
        super(server, ProductApi.SINGULAR_BASEURI, ProductApi.PLURAL_BASEURI);
    }

    public async search(query: string): Promise<Array<ProductResponse>> {
        return this.sendRequest<Array<ProductResponse>>(ProductApi.PLURAL_BASEURI + "?q=" + encodeURIComponent(query));
    }

}