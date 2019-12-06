import {FunctionComponent, useState} from "react";
import Sidebar from "../Sidebar/Sidebar";
import Content from "../Content/Content";
import "../../style.css";
import 'bootstrap/dist/css/bootstrap.min.css';

type Props = {}

const Layout: FunctionComponent<Props> = ({
                                              children
                                          }) => {
    const [isOpen, setOpen] = useState(true)
    const toggle = () => setOpen(!isOpen)
    return (
        <div className="wrapper">
            <Sidebar toggle={toggle} isOpen={isOpen}/>
            <Content toggle={toggle} isOpen={isOpen}>
                {children}
            </Content>
        </div>
    )
};

export default Layout;