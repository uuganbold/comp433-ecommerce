import IRequest from "../IRequest";

class CategoryRequest implements IRequest {

    public name!: string;

    constructor(name: string) {
        this.name = name;
    }
}

export default CategoryRequest;