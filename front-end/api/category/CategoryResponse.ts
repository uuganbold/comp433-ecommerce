import IResponse from "../IResponse";
import HateoasResponse from "../HateoasResponse";

class CategoryResponse extends HateoasResponse implements IResponse {
    public id!: number;
    public name!: string;
}

export default CategoryResponse;