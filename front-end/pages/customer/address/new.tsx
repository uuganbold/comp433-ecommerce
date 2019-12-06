import {NextPage} from "next";
import Layout from "../../../components/Layout/Layout";
import {Alert, Button, Container, Input, Table} from "reactstrap";
import Toolbar from "../../../components/ToolBar";
import {ChangeEvent, useContext, useState} from "react";
import AppContext from "../../../components/AppContext/AppContext";
import Router, {useRouter} from "next/router";
import CustomerApi from "../../../api/customer/CustomerApi";
import AddressResponse from "../../../api/valueobjects/AddressResponse";
import AddressRequest from "../../../api/valueobjects/AddressRequest";

type Props = {}

const AddressNew: NextPage<Props> = (props) => {

    const [country, setCountry] = useState('');
    const [street, setStreet] = useState('');
    const [unit, setUnit] = useState('');
    const [city, setCity] = useState('');
    const [state, setState] = useState('');
    const [zipcode, setZipcode] = useState('');
    const [phonenumber, setPhonenumber] = useState('');

    const [error, setError] = useState('');
    const {server} = useContext(AppContext);

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        switch (e.target.name) {
            case 'country':
                setCountry(e.target.value);
                break;
            case 'street':
                setStreet(e.target.value);
                break;
            case 'unit':
                setUnit(e.target.value);
                break;
            case 'city':
                setCity(e.target.value);
                break;
            case 'state':
                setState(e.target.value);
                break;
            case 'zipcode':
                setZipcode(e.target.value);
                break;
            case 'phonenumber':
                setPhonenumber(e.target.value);
                break;
        }
    };

    const customerId = useRouter().query.id as string as unknown as number;
    const uri = useRouter().query.uri;

    const handleCancel = () => {
        let url = `/customer/address/list?id=${customerId}`;
        if (uri) url += `&uri=${uri}`;
        Router.push(url, `/customer/address/list?id=${customerId}`);
    }

    const handleSave = () => {
        const api = server.getApi(CustomerApi);
        const request = new AddressRequest(
            country,
            street,
            unit,
            city,
            state,
            zipcode as unknown as number,
            phonenumber
        );
        let response: Promise<AddressResponse>;
        if (uri) {
            response = api.addAddressUri(uri as string, request);
        } else {
            response = api.addAddress(customerId, request);
        }
        response.then((cat: AddressResponse) => {
            Router.push(`/customer/address/list?id=${customerId}&uri=${api.getLink(cat, 'all').href}`, `/customer/address/list?id=${customerId}`);
        }).catch((error) => {
            setError(error.message)
        });
    };
    return (
        <Layout>
            <div>
                <h1>New Address</h1>
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
                                Country
                            </th>
                            <td>
                                <Input name={'country'} value={country} onChange={handleChange}/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Street
                            </th>
                            <td>
                                <Input name={'street'} value={street} onChange={handleChange}/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Unit
                            </th>
                            <td>
                                <Input name={'unit'} value={unit} onChange={handleChange}/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                City
                            </th>
                            <td>
                                <Input name={'city'} value={city} onChange={handleChange}/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                State
                            </th>
                            <td>
                                <Input name={'state'} value={state} onChange={handleChange}/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Zipcode
                            </th>
                            <td>
                                <Input name={'zipcode'} value={zipcode} onChange={handleChange}/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Phonenumber
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
                        <Button onClick={handleCancel}
                                color={'secondary'}>Cancel</Button>
                    </Toolbar>
                </Container>
            </div>
        </Layout>
    )
};


export default AddressNew;