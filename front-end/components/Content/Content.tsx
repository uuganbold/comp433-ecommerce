import {FunctionComponent, useContext} from "react";
import {Badge, Button, Container, Navbar} from "reactstrap";
import classNames from "classnames";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faAlignLeft} from "@fortawesome/free-solid-svg-icons";
import AppContext from "../AppContext/AppContext";

type Props = {
    isOpen: boolean,
    toggle: () => void
}
const Content: FunctionComponent<Props> = ({
                                               children,
                                               isOpen,
                                               toggle
                                           }) => {
    const {api} = useContext(AppContext);
    let msg;
    if (api == "spring") {
        msg = "You are using Spring implementation";
    } else msg = "You are using Apache CXF JAXRS implementation";
    return (<Container fluid
                       className={classNames('content', {'is-open': isOpen})}>
            <Navbar color="light" light className="navbar shadow-sm p-3 mb-5 bg-white rounded" expand="md"
                    style={{display: "flex", flexDirection: "row", justifyContent: "space-between"}}>
                <Button color="info" onClick={toggle}>
                    <FontAwesomeIcon icon={faAlignLeft}/>
                </Button>
                <div>
                    <Badge color="primary">{msg}</Badge>
                </div>
            </Navbar>
            {children}
        </Container>
    );
}


export default Content;

