import {NextPage} from 'next';
import Layout from "../../components/Layout/Layout";
import {Button, Container, Table} from "reactstrap";
import ServerRepo from "../../util/ServerRepo";
import CategoryApi from "../../api/category/CategoryApi";
import CategoryResponse from "../../api/category/CategoryResponse";
import Link from "next/link";
import Toolbar from "../../components/ToolBar";

type Props = {
    categories: Array<CategoryResponse>
}

const CategoryList: NextPage<Props> = (props) => {

    return (
        <Layout>
            <div>
                <h1>List of Categories</h1>
                <Container>
                    <Table>
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>ID</th>
                            <th>Name</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            props.categories.map((row, i) =>
                                <tr key={row.id}>
                                    <td>{i + 1}</td>
                                    <td><Link href={'/category/[id]'} as={'/category/' + row.id}><a>{row.id}</a></Link>
                                    </td>
                                    <td><Link href={'/category/[id]'}
                                              as={'/category/' + row.id}><a>{row.name}</a></Link></td>
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

CategoryList.getInitialProps = async (ctx) => {
    const {server} = ServerRepo(ctx);
    const api = server.getApi(CategoryApi);
    const categories = await api.list();
    return {categories};
}

export default CategoryList;