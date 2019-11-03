import CategoryResponse from "./CategoryResponse";
import CategoryRequest from "./CategoryRequest";
import CrudApi from "../CrudApi";
import Server from "../Server";

class CategoryApi extends CrudApi<CategoryRequest, CategoryResponse> {

    private static readonly SINGULAR_BASEURI = "/category";
    private static readonly PLURAL_BASEURI = "/categories";

    constructor(server: Server) {
        super(server, CategoryApi.SINGULAR_BASEURI, CategoryApi.PLURAL_BASEURI);
    }
}

export default CategoryApi;