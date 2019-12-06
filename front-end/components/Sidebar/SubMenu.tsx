import React, {FunctionComponent, useState} from 'react';
import {Collapse, NavItem, NavLink} from 'reactstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import classNames from 'classnames';
import {IconDefinition} from "@fortawesome/fontawesome-common-types";
import Link from "next/link";

type Props = {
    title: string
    icon: IconDefinition,
    items: Array<{ title: string, target: string }>
}

const SubMenu: FunctionComponent<Props> = ({
                                               title,
                                               icon,
                                               items
                                           }) => {

    const [collapsed, setCollapsed] = useState(true)
    const toggleNavbar = () => setCollapsed(!collapsed)


    return (
        <div>
            <NavItem onClick={toggleNavbar} className={classNames({'menu-open': !collapsed})}>
                <NavLink className="dropdown-toggle">
                    <FontAwesomeIcon icon={icon} className="mr-2"/>{title}
                </NavLink>
            </NavItem>
            <Collapse isOpen={!collapsed} navbar className={classNames('items-menu', {'mb-1': !collapsed})}>
                {items.map((item, index) => (
                    <NavItem key={index} className="pl-4">
                        <Link href={item.target}>
                            <NavLink tag="a">
                                {item.title}
                            </NavLink>
                        </Link>
                    </NavItem>
                ))}
            </Collapse>
        </div>
    );
}

export default SubMenu;