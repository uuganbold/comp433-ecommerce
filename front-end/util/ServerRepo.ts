import Server from "../api/Server";
import {BASE_URI, DEFAULT_API, JAXRS_BASE_URI, SPRING_BASE_URI} from "../config";
import nextCookie from "next-cookies";
import {NextPageContext} from "next";

const SPRING_SERVER = Server.create(BASE_URI + SPRING_BASE_URI);
const JAXRS_SERVER = Server.create(BASE_URI + JAXRS_BASE_URI);

type Props = {
    api: string,
    server: Server
}

export default (ctx: NextPageContext): Props => {
    const {ecommerceApi} = nextCookie(ctx);
    let api = ecommerceApi;
    if (!ecommerceApi) {
        api = DEFAULT_API;
    }
    api = api as string;
    const server = ServerRepo_GetServer(api as string);
    return {api, server};
}

export const ServerRepo_GetServer = (api: string): Server => {
    if (api == "spring") return SPRING_SERVER;
    else return JAXRS_SERVER;
};