import {createContext} from "react";
import Server from "../../api/Server";
import {BASE_URI, DEFAULT_API, DEFAULT_API_URI} from "../../config";

type ContextProps = {
    api: string,
    server: Server,
    handleChangeApi: (api: string) => void
}

const AppContext = createContext<ContextProps>({
    api: DEFAULT_API,
    server: Server.create(BASE_URI + DEFAULT_API_URI),
    handleChangeApi: (api) => {
    }
});

export default AppContext;