import {NextPage} from 'next';
import Layout from "../../components/Layout/Layout";
import {Button, Container, Table} from "reactstrap";
import ServerRepo from "../../util/ServerRepo";
import Link from "next/link";
import Toolbar from "../../components/ToolBar";
import ReviewResponse from "../../api/review/ReviewResponse";
import ReviewApi from "../../api/review/ReviewApi";

type Props = {
    reviews: Array<ReviewResponse>
}

const ReviewList: NextPage<Props> = (props) => {

    return (
        <Layout>
            <div>
                <h1>List of Reviews</h1>
                <Container>
                    <Table>
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>ID</th>
                            <th>Comment</th>
                            <th>Product Name</th>
                            <th>Customer Name</th>
                            <th>Date</th>
                            <th>Star</th>
                        </tr>
                        </thead>

                        <tbody>
                        {
                            props.reviews.map((row, i) =>
                                <tr key={row.id}>
                                    <td>{i + 1}</td>
                                    <td><Link href={'/review/[id]'} as={'/review/' + row.id}><a>{row.id}</a></Link>
                                    </td>
                                    <td><Link href={'/review/[id]'}
                                              as={'/review/' + row.id}><a>{row.comment.substring(0, 40)}</a></Link>
                                    </td>
                                    <td><Link href={'/product/[id]'}
                                              as={'/product/' + row.product.id}><a>{row.product.name}</a></Link></td>
                                    <td><Link href={'/customer/[id]'}
                                              as={'/customer/' + row.customer.id}><a>{row.customer.firstName}</a></Link>
                                    </td>
                                    <td>{row.date}</td>
                                    <td>{row.star}</td>
                                </tr>
                            )
                        }
                        </tbody>
                    </Table>
                    <Toolbar>
                        <Link href={'edit'}><Button color={'primary'}>New</Button></Link>
                    </Toolbar>
                </Container>
            </div>
        </Layout>
    )
};

ReviewList.getInitialProps = async (ctx) => {
    const {server} = ServerRepo(ctx);
    const api = server.getApi(ReviewApi);
    const reviews = await api.list();
    return {reviews};
}

export default ReviewList;