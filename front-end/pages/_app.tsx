import App from "next/app";
import AppContext from "../components/AppContext/AppContext";
import Server from "../api/Server";
import {Component} from "react";
import {NextComponentType, NextPageContext} from "next";
import {NextRouter} from "next/router";
import cookies from 'js-cookie';
import ServerRepo, {ServerRepo_GetServer} from "../util/ServerRepo";

type Props = {
    Component: NextComponentType,
    router: NextRouter,
    ctx: NextPageContext
}

type PropType = {
    api: string,
    server: Server
}

type StateType = {
    api: string,
    server: Server
}
export default class MyApp extends App<PropType, {}, StateType> {

    constructor(props: any) {
        super(props);
        this.state = {
            api: props.api,
            server: ServerRepo_GetServer(props.api)
        }
    }

    static async getInitialProps({Component, ctx}: Props) {
        const {api, server} = ServerRepo(ctx);

        let pageProps = {};
        if (Component.getInitialProps) {
            pageProps = await Component.getInitialProps(ctx)
        }
        return {api, pageProps: {...pageProps}}
    }

    handleApiChange = (api: string) => {
        cookies.set("ecommerceApi", api, {expires: 1});
        const server = ServerRepo_GetServer(api);
        this.setState({
            api: api,
            server: server
        })
    };

    render() {
        const {Component, pageProps} = this.props;
        return (
            <AppContext.Provider
                value={{api: this.state.api, server: this.state.server, handleChangeApi: this.handleApiChange}}>
                <Component {...pageProps}/>
            </AppContext.Provider>
        )
    }

}