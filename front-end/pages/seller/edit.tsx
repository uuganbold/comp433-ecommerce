import {NextPage} from "next";
import Layout from "../../components/Layout/Layout";
import {Alert, Button, Container, Input, Table} from "reactstrap";
import ServerRepo from "../../util/ServerRepo";
import Toolbar from "../../components/ToolBar";
import {ChangeEvent, useContext, useState} from "react";
import AppContext from "../../components/AppContext/AppContext";
import Router from "next/router";
import SellerApi from "../../api/seller/SellerApi";
import SellerResponse from "../../api/seller/SellerResponse";
import SellerRequest from "../../api/seller/SellerRequest";
import Link from "next/link";

type Props = {
    seller: SellerResponse | undefined
}

const SellerEdit: NextPage<Props> = (props) => {

    const [name, setName] = useState(props.seller ? props.seller.name : '');
    const [website, setWebsite] = useState(props.seller ? props.seller.website : '');
    const [email, setEmail] = useState(props.seller ? props.seller.email : '');

    const [error, setError] = useState('');
    const {server} = useContext(AppContext);

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        console.log(e.target.name)
        switch (e.target.name) {
            case 'name':
                setName(e.target.value);
                break;
            case 'website':
                setWebsite(e.target.value);
                break;
            case 'email':
                setEmail(e.target.value);
                break;
        }
    };

    const handleSave = () => {
        const api = server.getApi(SellerApi);
        if (props.seller) {
            const id = props.seller.id;
            api.update(id, new SellerRequest(name, website, email)).then(() => {
                Router.push(`/seller/[id]`, `/seller/${id}`)
            }).catch((error) => {
                setError(error.message)
            });
        } else api.create(new SellerRequest(name, website, email)).then((cat: SellerResponse) => {
            Router.push(`/seller/[id]`, `/seller/${cat.id}`)
        }).catch((error) => {
            setError(error.message)
        });
    };
    return (
        <Layout>
            <div>
                <h1>{props.seller ? 'Editing' : 'New'} Seller</h1>
                <Container>
                    <Table>
                        <tbody>{props.seller && <tr>
                            <th scope="row">
                                ID
                            </th>
                            <td>
                                {props.seller.id}
                            </td>
                        </tr>}
                        <tr>
                            <th scope="row">
                                Name
                            </th>
                            <td>
                                <Input name={'name'} value={name} onChange={handleChange}/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Website
                            </th>
                            <td>
                                <Input name={'website'} value={website} onChange={handleChange}/>
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
                        </tbody>
                    </Table>
                    {error.length > 0 ? <Alert color="danger">{error}</Alert> : ''}
                    <Toolbar>
                        <Button onClick={handleSave} color={'primary'}>Save</Button>
                        <Link href={'/seller/list'}><Button color={'secondary'}>Cancel</Button></Link>
                    </Toolbar>
                </Container>
            </div>
        </Layout>
    )
};

SellerEdit.getInitialProps = async (ctx) => {
    const id = ctx.query.id;
    let seller;
    if (id) {
        const {server} = ServerRepo(ctx);
        const api = server.getApi(SellerApi);
        seller = await api.get(id as string as unknown as number);
    }
    return {seller}
}

export default SellerEdit;