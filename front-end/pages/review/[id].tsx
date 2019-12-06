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
import ReviewResponse from "../../api/review/ReviewResponse";
import ReviewApi from "../../api/review/ReviewApi";

type Props = {
    review: ReviewResponse
}

const ReviewView: NextPage<Props> = (props) => {

    const {server} = useContext(AppContext);
    const api = server.getApi(ReviewApi);

    const [error, setError] = useState('');

    const handleDelete = () => {
        api.deleteObject(props.review).then(() => {
            Router.push(`/review/list?uri=${api.getLink(props.review, 'all').href}`, `/review/list`);
        }).catch((error: ApiError) => {
            setError(error.message);
        });
    };

    return (
        <Layout>
            <div>
                <h1>Review</h1>
                <Container>
                    <Table>
                        <tbody>
                        <tr>
                            <th scope="row">
                                ID
                            </th>
                            <td>
                                {props.review.id}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Date
                            </th>
                            <td>
                                {props.review.date}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" colSpan={2}>
                                Comment
                            </th>
                        </tr>
                        <tr>
                            <td colSpan={2}>
                                {props.review.comment}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Star
                            </th>
                            <td>
                                {props.review.star}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" colSpan={2} style={{background: "#7386D5", color: "white"}}>
                                Product
                            </th>
                        </tr>
                        <tr>
                            <th scope="row">ID</th>
                            <td>
                                {props.review.product.id}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Name</th>
                            <td>
                                <Link href={`/product/[id]?uri=${api.getLink(props.review, 'product').href}`}
                                      as={`/product/${props.review.product.id}`}><a>{props.review.product.name}</a></Link>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" colSpan={2} style={{background: "#7386D5", color: "white"}}>
                                Customer
                            </th>
                        </tr>
                        <tr>
                            <th scope="row">ID</th>
                            <td>
                                {props.review.customer.id}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Name</th>
                            <td>
                                <Link href={`/customer/[id]?uri=${api.getLink(props.review, 'customer').href}`}
                                      as={`/customer/${props.review.customer.id}`}><a>{props.review.customer.firstName}</a></Link>
                            </td>
                        </tr>
                        </tbody>
                    </Table>
                    {error.length > 0 ? <Alert color="danger">{error}</Alert> : ''}
                    <Toolbar>
                        <Link href={`/review/edit?uri=${api.getLink(props.review, 'self').href}`}
                              as={`/review/edit?id=${props.review.id}`}><Button color={'primary'}>Edit</Button></Link>
                        <Button onClick={handleDelete} color={'danger'}>Delete</Button>
                        <Link href={`/review/list?uri=${api.getLink(props.review, 'all').href}`}
                              as={'/review/list'}><Button color={'secondary'}>List</Button></Link>
                    </Toolbar>
                </Container>
            </div>
        </Layout>
    )
};

ReviewView.getInitialProps = async (ctx) => {
    const {server} = ServerRepo(ctx);
    const api = server.getApi(ReviewApi);

    const uri = ctx.query.uri;
    let review;
    if (uri) review = await api.getUri(uri as string);
    else review = await api.get(ctx.query.id as string as unknown as number);
    return {review};
};

export default ReviewView;