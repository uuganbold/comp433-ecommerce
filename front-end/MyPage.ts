import {NextPageContext} from "next";
import Server from "./api/Server";

/**
 * `Page` type, use it as a guide to create `pages`.
 */
export type MyPage<P = {}, IP = P> = {
    (props: P): JSX.Element | null
    defaultProps?: Partial<P>
    displayName?: string
    /**
     * Used for initial page load data population. Data returned from `getInitialProps` is serialized when server rendered.
     * Make sure to return plain `Object` without using `Date`, `Map`, `Set`.
     * @param ctx Context of `page`
     */
    getInitialProps?(ctx: NextPageContext, server: Server): Promise<IP>
}