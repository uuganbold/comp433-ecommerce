import {NextPage} from 'next';
import Layout from "../../components/Layout/Layout";
import {Button, Container, Table} from "reactstrap";
import ServerRepo from "../../util/ServerRepo";
import Link from "next/link";
import Toolbar from "../../components/ToolBar";
import OrderResponse from "../../api/order/OrderResponse";
import OrderApi from "../../api/order/OrderApi";
import {useContext} from "react";
import AppContext from "../../components/AppContext/AppContext";

type Props = {
    orders: Array<OrderResponse>
}

const OrderList: NextPage<Props> = (props) => {
    const {server} = useContext(AppContext);
    const api = server.getApi(OrderApi);

    return (
        <Layout>
            <div>
                <h1>List of Orders</h1>
                <Container>
                    <Table>
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>ID</th>
                            <th>Date</th>
                            <th>Update</th>
                            <th>Status</th>
                            <th>Customer</th>
                            <th>Total Value</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            props.orders.map((row, i) =>
                                <tr key={row.id}>
                                    <td>{i + 1}</td>
                                    <td><Link href={`/order/[id]?uri=${api.getLink(row, 'self').href}`}
                                              as={'/order/' + row.id}><a>{row.id}</a></Link>
                                    </td>
                                    <td><Link href={`/order/[id]?uri=${api.getLink(row, 'self').href}`}
                                              as={'/order/' + row.id}><a>{row.date}</a></Link>
                                    </td>
                                    <td>{row.updateDate}</td>
                                    <td>{row.status}</td>
                                    <td><Link href={`/customer/[id]?uri=${api.getLink(row, 'customer').href}`}
                                              as={'/customer/' + row.customer.id}><a>{row.customer.firstName}</a></Link>
                                    </td>
                                    <td>{row.totalValue}</td>
                                </tr>
                            )
                        }
                        </tbody>
                    </Table>
                    <Toolbar>
                        <Link href={'edit'}><Button color={'primary'}>New</Button></Link>
                    </Toolbar>
                </Container>
            </div>
        </Layout>
    )
};

OrderList.getInitialProps = async (ctx) => {
    const uri = ctx.query.uri;
    const {server} = ServerRepo(ctx);
    const api = server.getApi(OrderApi);
    let orders;

    if (uri) orders = await api.listUri(uri as string);
    else orders = await api.list();
    return {orders};
}

export default OrderList;