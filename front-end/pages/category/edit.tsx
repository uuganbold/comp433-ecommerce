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
import Link from "next/link";

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

    const handleSave = () => {
        const api = server.getApi(CategoryApi);
        if (props.category) {
            const id = props.category.id;
            api.update(id, new CategoryRequest(name)).then(() => {
                Router.push(`/category/[id]`, `/category/${id}`)
            }).catch((error) => {
                setError(error.message)
            });
        } else api.create(new CategoryRequest(name)).then((cat: CategoryResponse) => {
            Router.push(`/category/[id]`, `/category/${cat.id}`)
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
                        <Link href={'/category/list'}><Button color={'secondary'}>Cancel</Button></Link>
                    </Toolbar>
                </Container>
            </div>
        </Layout>
    )
};

CategoryEdit.getInitialProps = async (ctx) => {
    const id = ctx.query.id;
    let category;
    if (id) {
        const {server} = ServerRepo(ctx);
        const api = server.getApi(CategoryApi);
        category = await api.get(id as string as unknown as number);
    }
    return {category}
}

export default CategoryEdit;