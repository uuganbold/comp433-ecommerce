import {FunctionComponent} from "react";
import {Button, Container, Navbar} from "reactstrap";
import classNames from "classnames";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faAlignLeft} from "@fortawesome/free-solid-svg-icons";

type Props = {
    isOpen: boolean,
    toggle: () => void
}
const Content: FunctionComponent<Props> = ({
                                               children,
                                               isOpen,
                                               toggle
                                           }) => <Container fluid
                                                            className={classNames('content', {'is-open': isOpen})}>
    <Navbar color="light" light className="navbar shadow-sm p-3 mb-5 bg-white rounded" expand="md">
        <Button color="info" onClick={toggle}>
            <FontAwesomeIcon icon={faAlignLeft}/>
        </Button>
    </Navbar>
    {children}
</Container>;


export default Content;

