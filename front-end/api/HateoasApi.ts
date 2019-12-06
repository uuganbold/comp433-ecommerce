import Api from "./Api";
import HateoasResponse from "./HateoasResponse";
import Link from "./Link";

export default class HateoasApi extends Api {

    public getLink(res: HateoasResponse, rel: string): Link {
        const l: Link = res.links.filter(l => l.rel == rel)[0];
        if (l == null) throw new Error("Link not found with rel:" + rel);
        return l;
    }

}