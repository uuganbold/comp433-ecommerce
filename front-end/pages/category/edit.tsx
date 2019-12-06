import CategoryResponse from "../../api/category/CategoryResponse";
import {NextPage} from "next";
import Layout from "../../components/Layout/Layout";
import {Alert, Button, Container, Input, Table} from "reactstrap";
import ServerRepo from "../../util/ServerRepo";
import CategoryApi from "../../api/category/CategoryApi";
import Toolbar from "../../components/ToolBar";
import {ChangeEvent, useContext, useState} from "react";
import AppContext from "../../components/AppContext/AppContext";
import CategoryRequest from "../../api/category/CategoryRequest";
import Router from "next/router";

type Props = {
    category: CategoryResponse | undefined
}

const CategoryEdit: NextPage<Props> = (props) => {

    const [name, setName] = useState(props.category ? props.category.name : '');
    const [error, setError] = useState('');
    const {server} = useContext(AppContext);

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        setName(e.target.value);
    };

    const api = server.getApi(CategoryApi);

    const handleList = () => {
        if (props.category == null) {
            Router.push('/category/list');
        } else {
            Router.push('/category/list?uri=' + api.getLink(props.category, 'all').href, '/category/list');
        }
    }

    const handleSave = () => {
        if (props.category) {
            api.updateUri(api.getLink(props.category, 'self').href, new CategoryRequest(name)).then((cat: CategoryResponse) => {
                Router.push(`/category/[id]?uri=` + api.getLink(cat, 'self').href, `/category/${cat.id}`)
            }).catch((error) => {
                setError(error.message)
            });
        } else api.create(new CategoryRequest(name)).then((cat: CategoryResponse) => {
            Router.push(`/category/[id]?uri=` + api.getLink(cat, 'self').href, `/category/${cat.id}`)
        }).catch((error) => {
            setError(error.message)
        });
    };
    return (
        <Layout>
            <div>
                <h1>Editing Category</h1>
                <Container>
                    <Table>
                        <tbody>
                        {props.category && <tr>
                            <th scope="row">
                                ID
                            </th>
                            <td>
                                {props.category.id}
                            </td>
                        </tr>}
                        <tr>
                            <th scope="row">
                                Name
                            </th>
                            <td>
                                <Input value={name} onChange={handleChange}/>
                            </td>
                        </tr>
                        </tbody>
                    </Table>
                    {error.length > 0 ? <Alert color="danger">{error}</Alert> : ''}
                    <Toolbar>
                        <Button onClick={handleSave} color={'primary'}>Save</Button>
                        <Button color={'secondary'} onClick={handleList}>Cancel</Button>
                    </Toolbar>
                </Container>
            </div>
        </Layout>
    )
};

CategoryEdit.getInitialProps = async (ctx) => {
    const id = ctx.query.id;
    const uri = ctx.query.uri;
    let category;
    const {server} = ServerRepo(ctx);
    const api = server.getApi(CategoryApi);
    if (uri) {
        category = await api.getUri(uri as string);
    } else if (id) {
        category = await api.get(id as string as unknown as number);
    }
    return {category}
};

export default CategoryEdit;