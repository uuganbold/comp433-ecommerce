import {NextPage} from 'next';
import Layout from "../../components/Layout/Layout";
import {Button, Col, Container, Form, FormGroup, Input, Label, Row, Table} from "reactstrap";
import ServerRepo from "../../util/ServerRepo";
import ProductApi from "../../api/product/ProductApi";
import ProductResponse from "../../api/product/ProductResponse";
import Link from "next/link";
import Toolbar from "../../components/ToolBar";
import {useContext, useState} from "react";
import Router, {useRouter} from "next/router";
import AppContext from "../../components/AppContext/AppContext";

type Props = {
    products: Array<ProductResponse>
}

const ProductList: NextPage<Props> = (props) => {
    const router = useRouter();
    const [query, setQuery] = useState(router.query.q);

    const {server} = useContext(AppContext);
    const api = server.getApi(ProductApi);

    const uri = router.query.uri;

    const handleSearch = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const as = `/product/list?q=${query}`;
        let url = as;
        if (uri) url += `&uri=${uri as string}`;
        Router.push(url, as);
    }

    return (
        <Layout>
            <div>
                <h1>List of Products</h1>
                <Container>
                    <Form inline onSubmit={handleSearch}>
                        <FormGroup row>
                            <Label>Search Query</Label>
                            <Col><Input value={query} onChange={(e) => setQuery(e.target.value)}/><Button
                                color={"success"}>Search</Button></Col>
                        </FormGroup>
                    </Form>
                    <Row>
                        <Col>
                            <Table>
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>List Price</th>
                                    <th>Available</th>
                                    <th>Category</th>
                                    <th>Seller</th>
                                </tr>
                                </thead>
                                <tbody>
                                {
                                    props.products.map((row, i) =>
                                        <tr key={row.id}>
                                            <td>{i + 1}</td>
                                            <td><Link href={`/product/[id]?uri=${api.getLink(row, 'self').href}`}
                                                      as={'/product/' + row.id}><a>{row.id}</a></Link>
                                            </td>
                                            <td><Link href={`/product/[id]?uri=${api.getLink(row, 'self').href}`}
                                                      as={'/product/' + row.id}><a>{row.name}</a></Link></td>
                                            <td>{row.listPrice}</td>
                                            <td>{row.availableQuantity}</td>
                                            <td><Link href={`/category/[id]?uri=${api.getLink(row, 'category').href}`}
                                                      as={`/category/${row.category.id}`}><a>{row.category.name}</a></Link>
                                            </td>
                                            <td><Link href={`/seller/[id]?uri=${api.getLink(row, 'seller').href}`}
                                                      as={`/seller/${row.seller.id}`}><a>{row.seller.name}</a></Link>
                                            </td>
                                        </tr>
                                    )
                                }
                                </tbody>
                            </Table>
                            <Toolbar>
                                <Link href={'edit'}><Button color={'primary'}>New</Button></Link>
                            </Toolbar>
                        </Col>
                    </Row>

                </Container>
            </div>
        </Layout>
    )
};

ProductList.getInitialProps = async (ctx) => {
    const uri = ctx.query.uri;

    const {server} = ServerRepo(ctx);
    const api = server.getApi(ProductApi);
    let products;
    if (ctx.query.q) {
        if (uri) products = await api.searchUri(ctx.query.q as string, uri as string);
        else products = await api.search(ctx.query.q as string);
    } else {
        if (uri) products = await api.listUri(uri as string);
        else products = await api.list();
    }
    return {products};
};

export default ProductList;