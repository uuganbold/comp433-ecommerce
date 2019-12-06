import ProductResponse from "../../api/product/ProductResponse";
import {NextPage} from "next";
import Layout from "../../components/Layout/Layout";
import {Alert, Button, Container, Table} from "reactstrap";
import Link from "next/link";
import ServerRepo from "../../util/ServerRepo";
import ProductApi from "../../api/product/ProductApi";
import Router from "next/router";
import Toolbar from "../../components/ToolBar";
import {useContext, useState} from "react";
import AppContext from "../../components/AppContext/AppContext";
import ApiError from "../../api/ApiError";
import {addToCart} from "../../util/CartItem";

type Props = {
    product: ProductResponse
}

const ProductView: NextPage<Props> = (props) => {

    const {server} = useContext(AppContext);
    const api = server.getApi(ProductApi);

    const [error, setError] = useState('');

    const handleDelete = () => {
        api.deleteObject(props.product).then(() => {
            Router.push(`/product/list?uri=${api.getLink(props.product, 'all').href}`, `/product/list`);
        }).catch((error: ApiError) => {
            setError(error.message);
        });
    };

    const handleBuy = () => {
        addToCart({id: props.product.id, name: props.product.name, quantity: 1});
        Router.push(`/order/edit?uri=${api.getLink(props.product, 'orders').href}`, `/order/edit`);
    };

    const handleEdit = () => {
        Router.push(`/product/edit?id=${props.product.id}`);
    };

    return (
        <Layout>
            <div>
                <h1>Products</h1>
                <Container>
                    <Table>
                        <tbody>
                        <tr>
                            <th scope="row">
                                ID
                            </th>
                            <td>
                                {props.product.id}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Name
                            </th>
                            <td>
                                {props.product.name}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                List Price
                            </th>
                            <td>
                                {props.product.listPrice}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Available Quantity
                            </th>
                            <td>
                                {props.product.availableQuantity}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" colSpan={2}>
                                Description
                            </th>
                        </tr>
                        <tr>
                            <td colSpan={2}>
                                {props.product.description}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" colSpan={2} style={{background: "#7386D5", color: "white"}}>
                                Category
                            </th>
                        </tr>
                        <tr>
                            <th scope="row">ID</th>
                            <td>
                                {props.product.category.id}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Name</th>
                            <td>
                                <Link href={`/category/[id]?uri=${api.getLink(props.product, 'category').href}`}
                                      as={`/category/${props.product.category.id}`}><a>{props.product.category.name}</a></Link>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" colSpan={2} style={{background: "#7386D5", color: "white"}}>
                                Seller
                            </th>
                        </tr>
                        <tr>
                            <th scope="row">ID</th>
                            <td>
                                {props.product.seller.id}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Name</th>
                            <td>
                                <Link href={`/seller/[id]?uri=${api.getLink(props.product, 'seller').href}`}
                                      as={`/seller/${props.product.seller.id}`}><a>{props.product.seller.name}</a></Link>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Website</th>
                            <td>
                                {props.product.seller.website}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Email</th>
                            <td>
                                {props.product.seller.email}
                            </td>
                        </tr>

                        </tbody>
                    </Table>
                    {error.length > 0 ? <Alert color="danger">{error}</Alert> : ''}
                    <Toolbar>
                        <Button onClick={handleBuy} color={'success'}>Buy</Button>
                        <Link href={`/product/edit?uri=?uri=${api.getLink(props.product, 'self').href}`}
                              as={`/product/edit?id=${props.product.id}`}><Button onClick={handleEdit}
                                                                                  color={'primary'}>Edit</Button></Link>
                        <Button onClick={handleDelete} color={'danger'}>Delete</Button>
                        <Link href={`/product/list?uri=${api.getLink(props.product, 'all').href}`}
                              as={`/product/list`}><Button color={'secondary'}>List</Button></Link>
                    </Toolbar>
                </Container>
            </div>
        </Layout>
    )
};

ProductView.getInitialProps = async (ctx) => {
    const {server} = ServerRepo(ctx);
    const api = server.getApi(ProductApi);

    const uri = ctx.query.uri;
    let product;

    if (uri) product = await api.getUri(uri as string);
    else product = await api.get(ctx.query.id as string as unknown as number);
    return {product};
}

export default ProductView;