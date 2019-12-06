import {NextPage} from "next";
import Layout from "../../components/Layout/Layout";
import {Alert, Button, Container, Input, Table} from "reactstrap";
import ServerRepo from "../../util/ServerRepo";
import Toolbar from "../../components/ToolBar";
import {ChangeEvent, useContext, useState} from "react";
import AppContext from "../../components/AppContext/AppContext";
import Router from "next/router";
import CustomerApi from "../../api/customer/CustomerApi";
import CustomerResponse from "../../api/customer/CustomerResponse";
import CustomerRequest from "../../api/customer/CustomerRequest";

type Props = {
    customer: CustomerResponse | undefined
}

const CustomerEdit: NextPage<Props> = (props) => {

    const [firstName, setFirstName] = useState(props.customer ? props.customer.firstName : '');
    const [lastName, setLastName] = useState(props.customer ? props.customer.lastName : '');
    const [email, setEmail] = useState(props.customer ? props.customer.email : '');
    const [phonenumber, setPhonenumber] = useState(props.customer ? props.customer.phonenumber : '');

    const [error, setError] = useState('');
    const {server} = useContext(AppContext);
    const api = server.getApi(CustomerApi);

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        switch (e.target.name) {
            case 'firstName':
                setFirstName(e.target.value);
                break;
            case 'lastName':
                setLastName(e.target.value);
                break;
            case 'email':
                setEmail(e.target.value);
                break;
            case 'phonenumber':
                setPhonenumber(e.target.value);
                break;
        }
    };

    const handleSave = () => {
        if (props.customer) {
            api.updateUri(api.getLink(props.customer, 'self').href, new CustomerRequest(firstName, lastName, email, phonenumber)).then((cus) => {
                Router.push(`/customer/[id]?uri=${api.getLink(cus, 'self').href}`, `/customer/${cus.id}`)
            }).catch((error) => {
                setError(error.message)
            });
        } else api.create(new CustomerRequest(firstName, lastName, email, phonenumber)).then((cat: CustomerResponse) => {
            Router.push(`/customer/[id]?uri=${api.getLink(cat, 'self').href}`, `/customer/${cat.id}`)
        }).catch((error) => {
            setError(error.message)
        });
    };

    const handleCancel = () => {
        if (props.customer) {
            Router.push(`/customer/list?uri=${api.getLink(props.customer, 'all').href}`, `/customer/list`);
        } else {
            Router.push(`/customer/list`);
        }
    };

    return (
        <Layout>
            <div>
                <h1>{props.customer ? 'Editing' : 'New'} Customer</h1>
                <Container>
                    <Table>
                        <tbody>{props.customer && <tr>
                            <th scope="row">
                                ID
                            </th>
                            <td>
                                {props.customer.id}
                            </td>
                        </tr>}
                        <tr>
                            <th scope="row">
                                First Name
                            </th>
                            <td>
                                <Input name={'firstName'} value={firstName} onChange={handleChange}/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Last Name
                            </th>
                            <td>
                                <Input name={'lastName'} value={lastName} onChange={handleChange}/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Email
                            </th>
                            <td>
                                <Input name={'email'} value={email} onChange={handleChange}/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Phone Number
                            </th>
                            <td>
                                <Input name={'phonenumber'} value={phonenumber} onChange={handleChange}/>
                            </td>
                        </tr>
                        </tbody>
                    </Table>
                    {error.length > 0 ? <Alert color="danger">{error}</Alert> : ''}
                    <Toolbar>
                        <Button onClick={handleSave} color={'primary'}>Save</Button>
                        <Button onClick={handleCancel} color={'secondary'}>Cancel</Button>
                    </Toolbar>
                </Container>
            </div>
        </Layout>
    )
};

CustomerEdit.getInitialProps = async (ctx) => {
    const id = ctx.query.id;
    const uri = ctx.query.uri;

    const {server} = ServerRepo(ctx);
    const api = server.getApi(CustomerApi);
    let customer;
    if (uri) {
        customer = await api.getUri(uri as string);
    } else if (id) {
        customer = await api.get(id as string as unknown as number);
    }
    return {customer}
};

export default CustomerEdit;