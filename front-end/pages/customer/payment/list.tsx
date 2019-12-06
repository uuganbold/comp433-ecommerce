import {NextPage} from 'next';
import Layout from "../../../components/Layout/Layout";
import {Button, Container, Table} from "reactstrap";
import ServerRepo from "../../../util/ServerRepo";
import Link from "next/link";
import CustomerApi from "../../../api/customer/CustomerApi";
import Toolbar from "../../../components/ToolBar";
import PaymentResponse from "../../../api/valueobjects/PaymentResponse";
import Router, {useRouter} from "next/router";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrash} from "@fortawesome/free-solid-svg-icons";
import {useContext, useState} from "react";
import AppContext from "../../../components/AppContext/AppContext";

type Props = {
    payments: Array<PaymentResponse>
}

const styles = {
    a: {
        cursor: 'pointer',
    }
}

const PaymentList: NextPage<Props> = (props) => {

    const customerId = useRouter().query.id;
    const uri = useRouter().query.uri;

    const {server} = useContext(AppContext);
    const api = server.getApi(CustomerApi);

    const [list, setList] = useState(props.payments);

    const handleDelete = async (payment: PaymentResponse) => {
        await api.removePaymentObject(payment);
        setList(list.filter(el => el.id != payment.id));
    };

    const handleNew = () => {
        let url = `/customer/payment/new?id=${customerId}`;
        if (uri) url += `&uri=${uri}`;
        Router.push(url, `new?id=${customerId}`);
    };

    return (
        <Layout>
            <div>
                <h1>List of Payments</h1>
                <Container>
                    <Table>
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>ID</th>
                            <th>Name on Card</th>
                            <th>Card Number</th>
                            <th>Expire</th>
                            <th>Address</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            list.map((row, i) =>
                                <tr key={row.id}>
                                    <td>{i + 1}</td>
                                    <td>{row.id}</td>
                                    <td>{row.nameOnCard}</td>
                                    <td>{row.cardNumber}</td>
                                    <td>{row.expireMonth}/{row.expireYear}</td>
                                    <td>{row.billingAddress.street} {row.billingAddress.city}</td>
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

PaymentList.getInitialProps = async (ctx) => {
    const id = ctx.query.id as string;
    const uri = ctx.query.uri;
    const {server} = ServerRepo(ctx);
    const api = server.getApi(CustomerApi);

    let payments;
    if (uri) {
        payments = await api.getPaymentsUri(uri as string);
    } else payments = await api.getPayments(id as unknown as number);
    return {payments};
}

export default PaymentList;