import ProductResponse from "../../api/product/ProductResponse";
import {NextPage} from "next";
import Layout from "../../components/Layout/Layout";
import {Alert, Button, Container, Input, Table} from "reactstrap";
import ServerRepo from "../../util/ServerRepo";
import ProductApi from "../../api/product/ProductApi";
import Toolbar from "../../components/ToolBar";
import {ChangeEvent, useContext, useState} from "react";
import AppContext from "../../components/AppContext/AppContext";
import Router from "next/router";
import ReviewResponse from "../../api/review/ReviewResponse";
import CustomerResponse from "../../api/customer/CustomerResponse";
import ReviewApi from "../../api/review/ReviewApi";
import CustomerApi from "../../api/customer/CustomerApi";
import ReviewRequest from "../../api/review/ReviewRequest";

type Props = {
    review: ReviewResponse | undefined,
    customers: CustomerResponse[],
    products: ProductResponse[]
}

const ReviewEdit: NextPage<Props> = ({
                                         review,
                                         products,
                                         customers
                                     }) => {

    const [comment, setComment] = useState(review ? review.comment : '');
    const [star, setStar] = useState(review ? review.star : '');
    const [productId, setProductId] = useState(review ? review.product.id : 0);
    const [customerId, setCustomerId] = useState(review ? review.customer.id : 0);
    const [error, setError] = useState('');
    const {server} = useContext(AppContext);
    const api = server.getApi(ReviewApi);

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        switch (e.target.name) {
            case 'comment':
                setComment(e.target.value);
                break;
            case 'star':
                setStar(e.target.value);
                break;
            case 'productId':
                setProductId(e.target.value as unknown as number);
                break;
            case 'customerId':
                setCustomerId(e.target.value as unknown as number);
                break;
        }
    };

    const handleCancel = () => {
        if (review == null) {
            Router.push('/review/list');
        } else {
            Router.push('/review/list?uri=' + api.getLink(review, 'all').href, '/review/list');
        }
    }

    const handleSave = () => {
        let response: Promise<ReviewResponse>;
        const request = new ReviewRequest(productId,
            customerId,
            star as unknown as number,
            comment);
        if (review) {
            response = api.updateUri(api.getLink(review, 'self').href, request)
        } else response = api.create(request);

        response.then((cat: ReviewResponse) => {
            Router.push(`/review/[id]?uri=${api.getLink(cat, 'self').href}`, `/review/${cat.id}`)
        }).catch((error) => {
            setError(error.message)
        });
    };
    return (
        <Layout>
            <div>
                <h1>{review ? 'Editing' : 'New'} Review</h1>
                <Container>
                    <Table>
                        <tbody>
                        {review && <tr>
                            <th scope="row">
                                ID
                            </th>
                            <td>
                                {review.id}
                            </td>
                        </tr>}
                        <tr>
                            <th scope="row" colSpan={2}>
                                Comment
                            </th>
                        </tr>
                        <tr>
                            <td colSpan={2}>
                                <Input width={100} height={50} type={'textarea'} name={'comment'}
                                       value={comment} onChange={handleChange}/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Star
                            </th>
                            <td>
                                <Input type={"select"} name={'star'} value={star} onChange={handleChange}>
                                    <option value={0} key={0}>Choose Star</option>
                                    {
                                        [1, 2, 3, 4, 5].map(s =>
                                            <option key={s} value={s}>{s}</option>)
                                    }
                                </Input>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Product
                            </th>
                            <td>
                                <Input type={"select"} name={'productId'} value={productId} onChange={handleChange}>
                                    <option value={0} key={0}>Choose Product</option>
                                    {
                                        products.map(s =>
                                            <option key={s.id} value={s.id}>{s.name}</option>)
                                    }
                                </Input>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Customer
                            </th>
                            <td>
                                <Input type={'select'} name={'customerId'} value={customerId} onChange={handleChange}>
                                    <option value={0} key={0}>Choose Customer</option>
                                    {
                                        customers.map(c =>
                                            <option value={c.id} key={c.id}>{c.firstName}</option>)
                                    }
                                </Input>
                            </td>
                        </tr>
                        </tbody>
                    </Table>
                    {error.length > 0 ? <Alert color="danger">{error}</Alert> : ''}
                    <Toolbar>
                        <Button onClick={handleSave} color={'primary'}>Save</Button>
                        <Button color={'secondary'} onClick={handleCancel}>Cancel</Button>
                    </Toolbar>
                </Container>
            </div>
        </Layout>
    )
};

ReviewEdit.getInitialProps = async (ctx) => {
    const id = ctx.query.id;
    const uri = ctx.query.uri;
    const {server} = ServerRepo(ctx);
    const api = server.getApi(ReviewApi);

    let review;
    if (uri) review = await api.getUri(uri as string);
    else if (id) review = await api.get(id as string as unknown as number);

    const products = await server.getApi(ProductApi).list();
    const customers = await server.getApi(CustomerApi).list();

    return {review: review, products: products, customers: customers}
};

export default ReviewEdit;