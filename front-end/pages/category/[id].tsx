import CategoryResponse from "../../api/category/CategoryResponse";
import {NextPage} from "next";
import Layout from "../../components/Layout/Layout";
import {Alert, Button, Container, Table} from "reactstrap";
import Link from "next/link";
import ServerRepo from "../../util/ServerRepo";
import CategoryApi from "../../api/category/CategoryApi";
import Router from "next/router";
import Toolbar from "../../components/ToolBar";
import {useContext, useState} from "react";
import AppContext from "../../components/AppContext/AppContext";
import ApiError from "../../api/ApiError";

type Props = {
    category: CategoryResponse
}

const CategoryView: NextPage<Props> = (props) => {

    const {server} = useContext(AppContext);

    const [error, setError] = useState('');

    const api = server.getApi<CategoryApi>(CategoryApi);

    const handleDelete = () => {
        api.deleteObject(props.category).then(() => {
            Router.push('/category/list?uri=' + api.getLink(props.category, 'all').href, '/category/list');
        }).catch((error: ApiError) => {
            setError(error.message);
        });
    };

    return (
        <Layout>
            <div>
                <h1>Categories</h1>
                <Container>
                    <Table>
                        <tbody>
                        <tr>
                            <th scope="row">
                                ID
                            </th>
                            <td>
                                {props.category.id}
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Name
                            </th>
                            <td>
                                {props.category.name}
                            </td>
                        </tr>
                        </tbody>
                    </Table>
                    {error.length > 0 ? <Alert color="danger">{error}</Alert> : ''}
                    <Toolbar>
                        <Link href={'/category/edit?uri=' + api.getLink(props.category, 'self').href}
                              as={'/category/edit?id=' + props.category.id}><Button
                            color={'primary'}>Edit</Button></Link>
                        <Button onClick={handleDelete} color={'danger'}>Delete</Button>
                        <Link href={'/category/list?uri=' + api.getLink(props.category, 'all').href}
                              as={'/category/list'}><Button color={'secondary'}>List</Button></Link>
                    </Toolbar>
                </Container>
            </div>
        </Layout>
    )
};

CategoryView.getInitialProps = async (ctx) => {
    const {server} = ServerRepo(ctx);
    const api = server.getApi(CategoryApi);

    const uri = ctx.query.uri;
    let category;
    if (uri) {
        category = await api.getUri(uri as string);
    } else category = await api.get(ctx.query.id as string as unknown as number);
    return {category};
}

export default CategoryView;