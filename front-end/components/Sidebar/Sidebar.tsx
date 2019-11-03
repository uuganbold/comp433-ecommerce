/*
import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHome, faBriefcase, faPaperPlane, faQuestion, faImage, faCopy } from '@fortawesome/free-solid-svg-icons';
import SubMenu from './SubMenu';
import { NavItem, NavLink, Nav } from 'reactstrap';
import classNames from 'classnames';
import {Link} from 'react-router-dom';

const SideBar = props => (
    <div className={classNames('sidebar', {'is-open': props.isOpen})}>
        <div className="sidebar-header">
            <span color="info" onClick={props.toggle} style={{color: '#fff'}}>&times;</span>
            <h3>Bootstrap Sidebar</h3>
        </div>
        <div className="side-menu">
            <Nav vertical className="list-unstyled pb-3">
                <p>Dummy Heading</p>
                <SubMenu title="Home" icon={faHome} items={submenus[0]}/>
                <NavItem>
                    <NavLink tag={Link} to={'/about'}>
                        <FontAwesomeIcon icon={faBriefcase} className="mr-2"/>About
                    </NavLink>
                </NavItem>
                <SubMenu title="Pages" icon={faCopy} items={submenus[1]}/>
                <NavItem>
                    <NavLink tag={Link} to={'/pages'}>
                        <FontAwesomeIcon icon={faImage} className="mr-2"/>Portfolio
                    </NavLink>
                </NavItem>
                <NavItem>
                    <NavLink tag={Link} to={'/faq'}>
                        <FontAwesomeIcon icon={faQuestion} className="mr-2"/>FAQ
                    </NavLink>
                </NavItem>
                <NavItem>
                    <NavLink tag={Link} to={'/contact'}>
                        <FontAwesomeIcon icon={faPaperPlane} className="mr-2"/>Contact
                    </NavLink>
                </NavItem>
            </Nav>
        </div>
    </div>
);

const submenus = [
    [
        {
            title: "Home 1",
            target: "Home-1"
        },
        {
            title: "Home 2",
            target: "Home-2",
        },
        {
            itle: "Home 3",
            target: "Home-3",
        }
    ],
    [
        {
            title: "Page 1",
            target: "Page-1",
        },
        {
            title: "Page 2",
            target: "Page-2",
        }
    ]
]
*/


import {FunctionComponent} from "react";
import classNames from 'classnames'
import {Nav, NavItem, NavLink} from "reactstrap";
import {
    faBoxes,
    faBriefcase,
    faCalendarCheck,
    faCodeBranch,
    faComment,
    faHome,
    faUser
} from "@fortawesome/free-solid-svg-icons";
import SubMenu from "./SubMenu";
import Link from "next/link";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

type Props = {
    isOpen: boolean,
    toggle: () => void
}

const SideBar: FunctionComponent<Props> = ({
                                               isOpen,
                                               toggle
                                           }) => {
    return (
        <div className={classNames('sidebar', {'is-open': isOpen})}>
            <div className="sidebar-header">
                <span color="info" onClick={toggle} style={{color: '#fff'}}>&times;</span>
                <h3>API LIST</h3>
            </div>
            <div className="side-menu">
                <Nav vertical className="list-unstyled pb-3">
                    <NavItem>
                        <Link href={'/'}>
                            <NavLink>
                                <FontAwesomeIcon icon={faHome} className="mr-2"/>Home
                            </NavLink>
                        </Link>
                    </NavItem>
                    <SubMenu title="Category" icon={faCodeBranch} items={submenus[0]}/>
                    <SubMenu title="Seller" icon={faBriefcase} items={submenus[1]}/>
                    <SubMenu title="Customer" icon={faUser} items={submenus[2]}/>
                    <SubMenu title="Product" icon={faBoxes} items={submenus[3]}/>
                    <SubMenu title="Review" icon={faComment} items={submenus[4]}/>
                    <SubMenu title="Orders" icon={faCalendarCheck} items={submenus[5]}/>
                </Nav>
            </div>
        </div>
    )
};

const submenus: Array<Array<{ title: string, target: string }>> = [
    [
        {
            title: "New Category",
            target: "/category/edit"
        },
        {
            title: "All Categories",
            target: "/category/list",
        }
    ],
    [
        {
            title: "New Seller",
            target: "/seller/edit",
        },
        {
            title: "All Sellers",
            target: "/seller/list",
        }
    ],
    [
        {
            title: "New Customer",
            target: "/customer/edit",
        },
        {
            title: "All Customer",
            target: "/customer/list",
        }
    ],
    [
        {
            title: "New Product",
            target: "/product/edit",
        },
        {
            title: "All Products",
            target: "/product/list",
        },
        {
            title: "Search Products",
            target: "/product/search"
        }
    ],
    [
        {
            title: "New Review",
            target: "/review/edit",
        },
        {
            title: "All Reviews",
            target: "/review/list",
        }
    ],
    [
        {
            title: "New Order",
            target: "/order/edit",
        },
        {
            title: "All Orders",
            target: "/order/list",
        }
    ]
];

export default SideBar;