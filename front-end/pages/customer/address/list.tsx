import {NextPage} from 'next';
import Layout from "../../../components/Layout/Layout";
import {Button, Container, Table} from "reactstrap";
import ServerRepo from "../../../util/ServerRepo";
import Link from "next/link";
import CustomerApi from "../../../api/customer/CustomerApi";
import Toolbar from "../../../components/ToolBar";
import AddressResponse from "../../../api/valueobjects/AddressResponse";
import Router, {useRouter} from "next/router";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrash} from "@fortawesome/free-solid-svg-icons";
import {useContext, useState} from "react";
import AppContext from "../../../components/AppContext/AppContext";

type Props = {
    addresses: Array<AddressResponse>
}

const styles = {
    a: {
        cursor: 'pointer',
    }
}

const AddressList: NextPage<Props> = (props) => {

    const customerId = useRouter().query.id;
    const uri = useRouter().query.uri;

    const {server} = useContext(AppContext);
    const api = server.getApi(CustomerApi);

    const [list, setList] = useState(props.addresses);

    const handleDelete = async (address: AddressResponse) => {
        await api.removeAddressObject(address);
        setList(list.filter(el => el.id != address.id))
    };

    const handleNew = () => {
        let url = `/customer/address/new?id=${customerId}`;
        if (uri) url += `&uri=${uri}`;
        Router.push(url, `new?id=${customerId}`);
    };

    return (
        <Layout>
            <div>
                <h1>List of Addresses</h1>
                <Container>
                    <Table>
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>ID</th>
                            <th>Street</th>
                            <th>City</th>
                            <th>State</th>
                            <th>Country</th>
                            <th>Zip</th>
                            <th>Phone</th>
                            <th>Delete</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            list.map((row, i) =>
                                <tr key={row.id}>
                                    <td>{i + 1}</td>
                                    <td>{row.id}</td>
                                    <td>{row.street}</td>
                                    <td>{row.city}</td>
                                    <td>{row.state}</td>
                                    <td>{row.country}</td>
                                    <td>{row.zipcode}</td>
                                    <td>{row.phonenumber}</td>
                                    <td><a onClick={() => handleDelete(row)} style={styles.a}><FontAwesomeIcon
                                        icon={faTrash} color={'red'}/></a></td>
                                </tr>
                            )
                        }
                        </tbody>
                    </Table>
                    <Toolbar>
                        <Button onClick={handleNew}
                                color={'primary'}>New</Button>
                        <Link href={`/customer/[id]`} as={`/customer/${customerId}`}><Button
                            color={'secondary'}>Customer</Button></Link>
                    </Toolbar>
                </Container>
            </div>
        </Layout>
    )
};

AddressList.getInitialProps = async (ctx) => {
    const id = ctx.query.id as string;
    const uri = ctx.query.uri;
    const {server} = ServerRepo(ctx);
    const api = server.getApi(CustomerApi);

    let addresses;
    if (uri) {
        addresses = await api.getAddressesUri(uri as string);
    } else addresses = await api.getAddresses(id as unknown as number);
    return {addresses};
};

export default AddressList;