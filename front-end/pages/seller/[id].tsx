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
import SellerApi from "../../api/seller/SellerApi";
import SellerResponse from "../../api/seller/SellerResponse";

type Props = {
    seller: SellerResponse
}

const SellerView: NextPage<Props> = (props) => {

    const {server} = useContext(AppContext);
    const api = server.getApi(SellerApi);

    const [error, setError] = useState('');

    const handleDelete = () => {
        api.deleteObject(props.seller).then(() => {
            Router.push(`/seller/list?uri=${api.getLink(props.seller, 'all').href}`, `/seller/list`);
        }).catch((error: ApiError) => {
            setError(error.message);
        });
    };

    return (
        <Layout>
            <div>
                <h1>Sellers</h1>
                <Container>
                    <Table>
                        <tbody>
                        <tr>
                            <th scope="row">
                                ID
                            </th>
                            <td>
                                {props.seller.id}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Name
                            </th>
                            <td>
                                {props.seller.name}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Website
                            </th>
                            <td>
                                {props.seller.website}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Email
                            </th>
                            <td>
                                {props.seller.email}
                            </td>
                        </tr>
                        </tbody>
                    </Table>
                    {error.length > 0 ? <Alert color="danger">{error}</Alert> : ''}
                    <Toolbar>
                        {<Link href={`/seller/edit?uri=${api.getLink(props.seller, 'self').href}`}
                               as={`/seller/edit?id=${props.seller.id}`}><Button color={'primary'}>Edit</Button></Link>}
                        <Button onClick={handleDelete} color={'danger'}>Delete</Button>
                        <Link href={{pathname: '/seller/address/list', query: {id: props.seller.id}}}><Button
                            color={'secondary'}>Addresses</Button></Link>
                        <Link href={`/seller/list?uri=${api.getLink(props.seller, 'all').href}`}
                              as={'/seller/list'}><Button color={'secondary'}>List</Button></Link>
                    </Toolbar>
                </Container>
            </div>
        </Layout>
    )
};

SellerView.getInitialProps = async (ctx) => {
    const {server} = ServerRepo(ctx);
    const api = server.getApi(SellerApi);

    const uri = ctx.query.uri;
    let seller;
    if (uri) seller = await api.getUri(uri as string);
    else seller = await api.get(ctx.query.id as string as unknown as number);
    return {seller};
};

export default SellerView;