import CrudApi from "../CrudApi";
import ReviewRequest from "./ReviewRequest";
import ReviewResponse from "./ReviewResponse";
import Server from "../Server";

export default class ReviewApi extends CrudApi<ReviewRequest, ReviewResponse> {
    private static readonly SINGULAR_BASEURI = "/review";
    private static readonly PLURAL_BASEURI = "/reviews";

    constructor(server: Server) {
        super(server, ReviewApi.SINGULAR_BASEURI, ReviewApi.PLURAL_BASEURI);
    }
}