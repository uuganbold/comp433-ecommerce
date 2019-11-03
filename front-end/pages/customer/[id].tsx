import {NextPage} from "next";
import Layout from "../../components/Layout/Layout";
import {Alert, Button, Container, Table} from "reactstrap";
import Link from "next/link";
import ServerRepo from "../../util/ServerRepo";
import Router from "next/router";
import Toolbar from "../../components/ToolBar";
import {useContext, useState} from "react";
import AppContext from "../../components/AppContext/AppContext";
import ApiError from "../../api/ApiError";
import CustomerApi from "../../api/customer/CustomerApi";
import CustomerResponse from "../../api/customer/CustomerResponse";

type Props = {
    customer: CustomerResponse
}

const CustomerView: NextPage<Props> = (props) => {

    const {server} = useContext(AppContext);

    const [error, setError] = useState('');

    const handleDelete = () => {
        server.getApi(CustomerApi).delete(props.customer.id).then(() => {
            Router.push('/customer/list');
        }).catch((error: ApiError) => {
            setError(error.message);
        });
    };

    const handleEdit = () => {
        Router.push(`/customer/edit?id=${props.customer.id}`);
    };

    return (
        <Layout>
            <div>
                <h1>Customers</h1>
                <Container>
                    <Table>
                        <tbody>
                        <tr>
                            <th scope="row">
                                ID
                            </th>
                            <td>
                                {props.customer.id}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                First Name
                            </th>
                            <td>
                                {props.customer.firstName}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Last Name
                            </th>
                            <td>
                                {props.customer.lastName}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Email
                            </th>
                            <td>
                                {props.customer.email}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Phone Number
                            </th>
                            <td>
                                {props.customer.phonenumber}
                            </td>
                        </tr>
                        </tbody>
                    </Table>
                    {error.length > 0 ? <Alert color="danger">{error}</Alert> : ''}
                    <Toolbar>
                        <Button onClick={handleEdit} color={'primary'}>Edit</Button>
                        <Button onClick={handleDelete} color={'danger'}>Delete</Button>
                        <Link href={{pathname: '/customer/address/list', query: {id: props.customer.id}}}><Button
                            color={'secondary'}>Addresses</Button></Link>
                        <Link href={{pathname: '/customer/payment/list', query: {id: props.customer.id}}}><Button
                            color={'secondary'}>Payments</Button></Link>
                        <Link href={'/customer/list'}><Button color={'secondary'}>List</Button></Link>
                    </Toolbar>
                </Container>
            </div>
        </Layout>
    )
};

CustomerView.getInitialProps = async (ctx) => {
    const {server} = ServerRepo(ctx);
    const api = server.getApi(CustomerApi);
    const customer = await api.get(ctx.query.id as string as unknown as number);
    return {customer};
}

export default CustomerView;