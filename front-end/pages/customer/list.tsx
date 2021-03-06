import {NextPage} from 'next';
import Layout from "../../components/Layout/Layout";
import {Button, Container, Table} from "reactstrap";
import ServerRepo from "../../util/ServerRepo";
import Link from "next/link";
import CustomerApi from "../../api/customer/CustomerApi";
import CustomerResponse from "../../api/customer/CustomerResponse";
import Toolbar from "../../components/ToolBar";
import {useContext} from "react";
import AppContext from "../../components/AppContext/AppContext";

type Props = {
    customers: Array<CustomerResponse>
}

const CustomerList: NextPage<Props> = (props) => {

    const {server} = useContext(AppContext);
    const api = server.getApi(CustomerApi);

    return (
        <Layout>
            <div>
                <h1>List of Customers</h1>
                <Container>
                    <Table>
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>ID</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email</th>
                            <th>Phone Number</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            props.customers.map((row, i) =>
                                <tr key={row.id}>
                                    <td>{i + 1}</td>
                                    <td><Link href={`/customer/[id]?uri=${api.getLink(row, 'self').href}`}
                                              as={'/customer/' + row.id}><a>{row.id}</a></Link>
                                    </td>
                                    <td><Link href={`/customer/[id]?uri=${api.getLink(row, 'self').href}`}
                                              as={'/customer/' + row.id}><a>{row.firstName}</a></Link>
                                    </td>
                                    <td>{row.lastName}</td>
                                    <td>{row.email}</td>
                                    <td>{row.phonenumber}</td>
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

CustomerList.getInitialProps = async (ctx) => {
    const {server} = ServerRepo(ctx);
    const api = server.getApi(CustomerApi);
    const uri = ctx.query.uri;

    let customers;
    if (uri) {
        customers = await api.listUri(uri as string);
    } else customers = await api.list();
    return {customers};
};

export default CustomerList;