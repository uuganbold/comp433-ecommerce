import {NextPage} from 'next';
import Layout from "../../components/Layout/Layout";
import {Button, Container, Table} from "reactstrap";
import ServerRepo from "../../util/ServerRepo";
import Link from "next/link";
import SellerApi from "../../api/seller/SellerApi";
import SellerResponse from "../../api/seller/SellerResponse";
import Toolbar from "../../components/ToolBar";

type Props = {
    sellers: Array<SellerResponse>
}

const SellerList: NextPage<Props> = (props) => {

    return (
        <Layout>
            <div>
                <h1>List of Sellers</h1>
                <Container>
                    <Table>
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Website</th>
                            <th>Email</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            props.sellers.map((row, i) =>
                                <tr key={row.id}>
                                    <td>{i + 1}</td>
                                    <td><Link href={'/seller/[id]'} as={'/seller/' + row.id}><a>{row.id}</a></Link></td>
                                    <td><Link href={'/seller/[id]'} as={'/seller/' + row.id}><a>{row.name}</a></Link>
                                    </td>
                                    <td>{row.website}</td>
                                    <td>{row.email}</td>
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

SellerList.getInitialProps = async (ctx) => {
    const {server} = ServerRepo(ctx);
    const api = server.getApi(SellerApi);
    const sellers = await api.list();
    return {sellers};
}

export default SellerList;