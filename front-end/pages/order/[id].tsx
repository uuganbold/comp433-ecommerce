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
import OrderResponse from "../../api/order/OrderResponse";
import OrderApi from "../../api/order/OrderApi";

type Props = {
    order: OrderResponse
}

const OrderView: NextPage<Props> = (props) => {

    const {server} = useContext(AppContext);
    const api = server.getApi(OrderApi);

    const [error, setError] = useState('');

    const handleCancel = () => {
        api.cancel(props.order).then((ord) => {
            Router.push(`/order/list/?uri=${api.getLink(ord, 'all').href}`, '/order/list');
        }).catch((error: ApiError) => {
            setError(error.message);
        })
    };

    const handleShip = () => {
        api.ship(props.order).then((ord) => {
            Router.push(`/order/list/?uri=${api.getLink(ord, 'all').href}`, '/order/list');
        }).catch((error: ApiError) => {
            setError(error.message);
        })
    };

    const handleDeliver = () => {
        api.deliver(props.order).then((ord) => {
            Router.push(`/order/list/?uri=${api.getLink(ord, 'all').href}`, '/order/list');
        }).catch((error: ApiError) => {
            setError(error.message);
        })
    };

    return (
        <Layout>
            <div>
                <h1>Order</h1>
                <Container>
                    <Table>
                        <tbody>
                        <tr>
                            <th scope="row">
                                ID
                            </th>
                            <td>
                                {props.order.id}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Date
                            </th>
                            <td>
                                {props.order.date}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Update date
                            </th>
                            <td>
                                {props.order.updateDate}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Status
                            </th>
                            <td>
                                {props.order.status}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Total Value
                            </th>
                            <td>
                                {props.order.totalValue}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" colSpan={2} style={{background: "#7386D5", color: "white"}}>
                                Customer
                            </th>
                        </tr>
                        <tr>
                            <th scope="row">ID</th>
                            <td>
                                {props.order.customer.id}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Name</th>
                            <td>
                                <Link href={`/customer/[id]?uri=${api.getLink(props.order, 'customer').href}`}
                                      as={`/customer/${props.order.customer.id}`}><a>{props.order.customer.firstName} {props.order.customer.lastName}</a></Link>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Email</th>
                            <td>
                                {props.order.customer.email}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Phonenumber</th>
                            <td>
                                {props.order.customer.phonenumber}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" colSpan={2} style={{background: "#7386D5", color: "white"}}>
                                Address
                            </th>
                        </tr>
                        <tr>
                            <th scope="row">ID</th>
                            <td>
                                {props.order.address.id}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Street</th>
                            <td>
                                {props.order.address.street}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Unit</th>
                            <td>
                                {props.order.address.unit}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">City</th>
                            <td>
                                {props.order.address.city}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">state</th>
                            <td>
                                {props.order.address.state}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Country</th>
                            <td>
                                {props.order.address.country}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Zipcode</th>
                            <td>
                                {props.order.address.zipcode}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Phone number</th>
                            <td>
                                {props.order.address.phonenumber}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" colSpan={2} style={{background: "#7386D5", color: "white"}}>
                                Payment
                            </th>
                        </tr>
                        <tr>
                            <th scope="row">ID</th>
                            <td>
                                {props.order.payment.id}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Card Number</th>
                            <td>
                                {props.order.payment.cardNumber}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Name on card</th>
                            <td>
                                {props.order.payment.nameOnCard}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Expire</th>
                            <td>
                                {props.order.payment.expireMonth}/{props.order.payment.expireYear}
                            </td>
                        </tr>
                        </tbody>
                    </Table>
                    {error.length > 0 ? <Alert color="danger">{error}</Alert> : ''}
                    <Toolbar>
                        <Button onClick={handleDeliver} color={'success'} name={'deliver'}>Deliver</Button>
                        <Button onClick={handleShip} color={'info'} name={'ship'}>Ship</Button>
                        <Button onClick={handleCancel} color={'danger'} name={'cancel'}>Cancel</Button>
                        <Link href={`/order/list?uri=${api.getLink(props.order, 'all').href}`}
                              as={'/order/list'}><Button color={'secondary'}>List</Button></Link>
                    </Toolbar>
                </Container>
            </div>
        </Layout>
    )
};

OrderView.getInitialProps = async (ctx) => {
    const {server} = ServerRepo(ctx);
    const api = server.getApi(OrderApi);

    const uri = ctx.query.uri;
    let order;
    if (uri) order = await api.getUri(uri as string);
    else order = await api.get(ctx.query.id as string as unknown as number);
    return {order};
}

export default OrderView;