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

    const [error, setError] = useState('');

    const handleDelete = () => {
        server.getApi(SellerApi).delete(props.seller.id).then(() => {
            Router.push('/seller/list');
        }).catch((error: ApiError) => {
            setError(error.message);
        });
    };

    const handleEdit = () => {
        Router.push(`/seller/edit?id=${props.seller.id}`);
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
                        <Button onClick={handleEdit} color={'primary'}>Edit</Button>
                        <Button onClick={handleDelete} color={'danger'}>Delete</Button>
                        <Link href={{pathname: '/seller/address/list', query: {id: props.seller.id}}}><Button
                            color={'secondary'}>Addresses</Button></Link>
                        <Link href={'/seller/list'}><Button color={'secondary'}>List</Button></Link>
                    </Toolbar>
                </Container>
            </div>
        </Layout>
    )
};

SellerView.getInitialProps = async (ctx) => {
    const {server} = ServerRepo(ctx);
    const api = server.getApi(SellerApi);
    const seller = await api.get(ctx.query.id as string as unknown as number);
    return {seller};
}

export default SellerView;