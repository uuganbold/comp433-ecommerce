import ProductResponse from "../../api/product/ProductResponse";
import {NextPage} from "next";
import Layout from "../../components/Layout/Layout";
import {Alert, Button, Container, Input, Table} from "reactstrap";
import ServerRepo from "../../util/ServerRepo";
import ProductApi from "../../api/product/ProductApi";
import Toolbar from "../../components/ToolBar";
import {ChangeEvent, useContext, useState} from "react";
import AppContext from "../../components/AppContext/AppContext";
import ProductRequest from "../../api/product/ProductRequest";
import Router from "next/router";
import SellerApi from "../../api/seller/SellerApi";
import CategoryApi from "../../api/category/CategoryApi";
import SellerResponse from "../../api/seller/SellerResponse";
import CategoryResponse from "../../api/category/CategoryResponse";

type Props = {
    product: ProductResponse | undefined,
    sellers: SellerResponse[],
    categories: CategoryResponse[]
}

const ProductEdit: NextPage<Props> = ({
                                          product,
                                          sellers,
                                          categories
                                      }) => {

    const [name, setName] = useState(product ? product.name : '');
    const [description, setDescription] = useState(product ? product.description : '');
    const [listPrice, setListPrice] = useState(product ? product.listPrice : '');
    const [availableQuantity, setAvailableQuantity] = useState(product ? product.availableQuantity : '');
    const [sellerId, setSellerId] = useState(product ? product.seller.id : 0);
    const [categoryId, setCategoryId] = useState(product ? product.category.id : 0);
    const [error, setError] = useState('');
    const {server} = useContext(AppContext);
    const api = server.getApi(ProductApi);

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        switch (e.target.name) {
            case 'name':
                setName(e.target.value);
                break;
            case 'description':
                setDescription(e.target.value);
                break;
            case 'listPrice':
                setListPrice(e.target.value);
                break;
            case 'availableQuantity':
                setAvailableQuantity(e.target.value);
                break;
            case 'sellerId':
                setSellerId(e.target.value as unknown as number);
                break;
            case 'categoryId':
                setCategoryId(e.target.value as unknown as number);
                break;
        }
    };

    const handleCancel = () => {
        if (product == null) {
            Router.push('/product/list');
        } else {
            Router.push('/product/list?uri=' + api.getLink(product, 'all').href, '/product/list');
        }
    }

    const handleSave = () => {
        let response: Promise<ProductResponse>;
        const request = new ProductRequest(name,
            description,
            listPrice as unknown as number,
            availableQuantity as unknown as number,
            sellerId,
            categoryId);
        if (product)
            response = api.updateUri(api.getLink(product, 'self').href, request);
        else response = api.create(request);

        response.then((cat: ProductResponse) => {
            Router.push(`/product/[id]?uri=${api.getLink(cat, 'self').href}`, `/product/${cat.id}`)
        }).catch((error) => {
            setError(error.message)
        });
    };
    return (
        <Layout>
            <div>
                <h1>{product ? 'Editing' : 'New'} Product</h1>
                <Container>
                    <Table>
                        <tbody>
                        {product && <tr>
                            <th scope="row">
                                ID
                            </th>
                            <td>
                                {product.id}
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
                                List Price
                            </th>
                            <td>
                                <Input name={'listPrice'} value={listPrice} onChange={handleChange}/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Available Quantity
                            </th>
                            <td>
                                <Input name={'availableQuantity'} value={availableQuantity} onChange={handleChange}/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" colSpan={2}>
                                Description
                            </th>
                        </tr>
                        <tr>
                            <td colSpan={2}>
                                <Input width={100} height={50} type={'textarea'} name={'description'}
                                       value={description} onChange={handleChange}/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Seller
                            </th>
                            <td>
                                <Input type={"select"} name={'sellerId'} value={sellerId} onChange={handleChange}>
                                    <option value={0} key={0}>Choose Seller</option>
                                    {
                                        sellers.map(s =>
                                            <option key={s.id} value={s.id}>{s.name}</option>)
                                    }
                                </Input>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Category
                            </th>
                            <td>
                                <Input type={'select'} name={'categoryId'} value={categoryId} onChange={handleChange}>
                                    <option value={0} key={0}>Choose Category</option>
                                    {
                                        categories.map(c =>
                                            <option value={c.id} key={c.id}>{c.name}</option>)
                                    }
                                </Input>
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

ProductEdit.getInitialProps = async (ctx) => {
    const id = ctx.query.id;
    const uri = ctx.query.uri;
    const {server} = ServerRepo(ctx);
    const api = server.getApi(ProductApi);

    let product;
    if (uri) product = await api.getUri(uri as string);
    else if (id) {
        product = await api.get(id as string as unknown as number);
    }

    const sellers = await server.getApi(SellerApi).list();
    const categories = await server.getApi(CategoryApi).list();

    return {product, sellers, categories}
};

export default ProductEdit;