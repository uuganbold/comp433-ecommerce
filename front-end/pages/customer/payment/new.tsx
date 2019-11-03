import {NextPage} from "next";
import Layout from "../../../components/Layout/Layout";
import {Alert, Button, Container, Input, Table} from "reactstrap";
import Toolbar from "../../../components/ToolBar";
import {ChangeEvent, useContext, useState} from "react";
import AppContext from "../../../components/AppContext/AppContext";
import Router, {useRouter} from "next/router";
import CustomerApi from "../../../api/customer/CustomerApi";
import Link from "next/link";
import PaymentResponse from "../../../api/valueobjects/PaymentResponse";
import PaymentRequest from "../../../api/valueobjects/PaymentRequest";
import ServerRepo from "../../../util/ServerRepo";
import AddressResponse from "../../../api/valueobjects/AddressResponse";

type Props = {
    addresses: AddressResponse[]
}

const PaymentNew: NextPage<Props> = (props) => {

    const [nameOnCard, setNameOnCard] = useState('');
    const [cardNumber, setCardNumber] = useState('');
    const [expireMonth, setExpireMonth] = useState('');
    const [expireYear, setExpireYear] = useState('');
    const [addressId, setAddressId] = useState('');

    const [error, setError] = useState('');
    const {server} = useContext(AppContext);

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        switch (e.target.name) {
            case 'nameOnCard':
                setNameOnCard(e.target.value);
                break;
            case 'cardNumber':
                setCardNumber(e.target.value);
                break;
            case 'expireMonth':
                setExpireMonth(e.target.value);
                break;
            case 'expireYear':
                setExpireYear(e.target.value);
                break;
            case 'addressName':
                setAddressId(e.target.value);
                break;
        }
    };

    const customerId = useRouter().query.id as string as unknown as number;

    const handleSave = () => {
        const api = server.getApi(CustomerApi);
        api.addPayment(customerId, new PaymentRequest(
            nameOnCard,
            cardNumber,
            expireMonth as unknown as number,
            expireYear as unknown as number,
            addressId as unknown as number
        )).then((cat: PaymentResponse) => {
            Router.push(`/customer/payment/list?id=${customerId}`)
        }).catch((error) => {
            setError(error.message)
        });
    };
    return (
        <Layout>
            <div>
                <h1>New Payment</h1>
                <Container>
                    <Table>
                        <tbody>
                        <tr>
                            <th scope="row">
                                CUSTOMER ID
                            </th>
                            <td>
                                {customerId}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Name on Card
                            </th>
                            <td>
                                <Input name={'nameOnCard'} value={nameOnCard} onChange={handleChange}/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Card Number
                            </th>
                            <td>
                                <Input name={'cardNumber'} value={cardNumber} onChange={handleChange}/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Expire Month
                            </th>
                            <td>
                                <Input name={'expireMonth'} value={expireMonth} onChange={handleChange}/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Expire Year
                            </th>
                            <td>
                                <Input name={'expireYear'} value={expireYear} onChange={handleChange}/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Address
                            </th>
                            <td>
                                <Input type={"select"} name={'addressName'} onChange={handleChange}>
                                    <option value={0} key={0}>Select Address</option>
                                    {
                                        props.addresses.map(a =>
                                            <option value={a.id} key={a.id}>a.street</option>)
                                    }
                                </Input>
                            </td>
                        </tr>
                        </tbody>
                    </Table>
                    {error.length > 0 ? <Alert color="danger">{error}</Alert> : ''}
                    <Toolbar>
                        <Button onClick={handleSave} color={'primary'}>Save</Button>
                        <Link href={{pathname: '/customer/payment/list', query: {id: customerId}}}><Button
                            color={'secondary'}>Cancel</Button></Link>
                    </Toolbar>
                </Container>
            </div>
        </Layout>
    )
};
PaymentNew.getInitialProps = async (ctx) => {
    const {server} = ServerRepo(ctx);
    const api = server.getApi(CustomerApi);
    const addresses = await api.getAddresses(ctx.query.id as string as unknown as number);
    return {addresses};
}


export default PaymentNew;