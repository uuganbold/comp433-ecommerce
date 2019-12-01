import {NextPage} from 'next';
import Layout from "../../components/Layout/Layout";
import {Button, Container, Table} from "reactstrap";
import ServerRepo from "../../util/ServerRepo";
import CategoryApi from "../../api/category/CategoryApi";
import CategoryResponse from "../../api/category/CategoryResponse";
import Link from "next/link";
import Toolbar from "../../components/ToolBar";
import CustomerApi from "../../api/customer/CustomerApi";
import CustomerView from "../customer/[id]";


type Props = {
    reviews: Array<CategoryResponse>
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
                                    <td><Link href={'/product/[id]'}
                                              as={'/product/' + row.id}><a>{row.name}</a></Link></td>
                                    <td><Link href={'/customer/[id]'}
                                              as={'/customer/' + row.id}><a>{row.name}</a></Link></td>

                                    <td><Link href={'/review/[id]'}
                                              as={'/review/' + row.id}><a>{row.date}</a></Link></td>

                                    <td><Link href={'/review/[id]'}
                                              as={'/review/' + row.id}><a>{row.star}</a></Link></td>
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

// CategoryList.getInitialProps = async (ctx) => {
//     const {server} = ServerRepo(ctx);
//     const api = server.getApi(CategoryApi);
//     const categories = await api.list();
//     return {categories};
// }

ReviewList.getInitialProps = async (ctx) => {
    const {server} = ServerRepo(ctx);
    const api = server.getApi(CustomerApi);
    const customer = await api.get(ctx.query.id as string as unknown as number);
    return {customer};
}

export default ReviewList;

