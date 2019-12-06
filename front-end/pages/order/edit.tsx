import {NextPage} from "next";
import Layout from "../../components/Layout/Layout";
import {Alert, Button, Container, Form, FormGroup, Input, Label, Table} from "reactstrap";
import ServerRepo from "../../util/ServerRepo";
import Toolbar from "../../components/ToolBar";
import {ChangeEvent, FormEvent, useContext, useEffect, useState} from "react";
import AppContext from "../../components/AppContext/AppContext";
import Link from "next/link";
import CustomerResponse from "../../api/customer/CustomerResponse";
import AddressResponse from "../../api/valueobjects/AddressResponse";
import PaymentResponse from "../../api/valueobjects/PaymentResponse";
import CustomerApi from "../../api/customer/CustomerApi";
import ProductApi from "../../api/product/ProductApi";
import ProductResponse from "../../api/product/ProductResponse";
import OrderItemComponent from "../../components/Order/OrderItemComponent";
import OrderRequest from "../../api/order/OrderRequest";
import OrderItemRequest from "../../api/order/OrderItemRequest";
import OrderApi from "../../api/order/OrderApi";
import OrderResponse from "../../api/order/OrderResponse";
import Router, {useRouter} from "next/router";
import {CartItem, getCart, saveCart} from "../../util/CartItem";

type Props = {
    customers: CustomerResponse[],
    products: ProductResponse[]
}

const OrderEdit: NextPage<Props> = ({
                                        customers,
                                        products
                                    }) => {

    const [customerId, setCustomerId] = useState(0);
    const [addressId, setAddressId] = useState(0);
    const [paymentId, setPaymentId] = useState(0);
    const [loaded, setLoaded] = useState(0);

    let addArr: AddressResponse[] = [];
    const [addresses, setAddresses] = useState(addArr);

    let payArr: PaymentResponse[] = [];
    const [payments, setPayments] = useState(payArr);

    let initItems: CartItem[] = [];
    const [items, setItems] = useState(initItems);

    useEffect(() => {
        if (loaded == 0) {
            setLoaded(1);
            setItems(getCart());
        } else {
            saveCart(items);
        }

    }, [items]);

    const [productId, setProductId] = useState(0);
    const [quantity, setQuantity] = useState<number>(0);


    const [error, setError] = useState('');
    const {server} = useContext(AppContext);
    const api = server.getApi(OrderApi);
    const customerApi = server.getApi(CustomerApi);

    const handleChange = async (e: ChangeEvent<HTMLInputElement>) => {
        switch (e.target.name) {
            case 'customerId':
                const cusId: number = e.target.value as unknown as number;
                const customer = customers.find(c => c.id = cusId) as CustomerResponse;
                setCustomerId(cusId);
                setAddressId(0);
                setPaymentId(0);
                if (cusId != 0) {
                    setAddresses(await customerApi.getAddressesUri(customerApi.getLink(customer, "addresses").href));
                    setPayments(await customerApi.getPaymentsUri(customerApi.getLink(customer, 'payments').href));
                }
                break;
            case 'addressId':
                setAddressId(e.target.value as unknown as number);
                break;
            case 'paymentId':
                setPaymentId(e.target.value as unknown as number);
                break;
            case 'productId':
                setProductId(e.target.value as unknown as number);
                break;
            case 'quantity':
                setQuantity(e.target.value as unknown as number);
                break;
        }
    };

    const handleItemClick = (id: number) => {
        const p = items.find(i => i.id === id) as CartItem;
        setProductId(p.id);
        setQuantity(p.quantity);
    };

    const handeItemRemove = (id: number) => {
        const p = items.filter(i => i.id != id);
        setItems(p);
    };

    const handleItemAdd = (e: FormEvent) => {
        e.preventDefault();
        if (productId == 0) return;
        const item: CartItem = {
            id: Number(productId),
            quantity: Number(quantity),
            name: (products.find(p => p.id == productId) as ProductResponse).name
        };
        let found = false;
        const is = items.map(i => {
            if (i.id === item.id) {
                found = true;
                item.quantity = item.quantity + i.quantity;
                return item;
            } else return i;
        });

        if (!found) is.push(item);
        setItems(is);

    };
    const uri = useRouter().query.uri;
    const handleSave = () => {

        const request = new OrderRequest(
            customerId as unknown as number,
            items.map(i => new OrderItemRequest(i.id, i.quantity)),
            addressId as unknown as number,
            paymentId as unknown as number,
        );
        let response: Promise<OrderResponse>;
        if (uri) response = api.createUri(uri as string, request);
        else response = api.create(request);
        response.then((cat: OrderResponse) => {
            saveCart([]);
            Router.push(`/order/[id]?uri=${api.getLink(cat, 'self').href}`, `/order/${cat.id}`)
        }).catch((error) => {
            setError(error.message)
        });
    };
    return (
        <Layout>
            <div>
                <h1>New Order</h1>
                <Container>
                    <Table>
                        <tbody>
                        <tr>
                            <th scope="row">
                                Customer
                            </th>
                            <td>
                                <Input type={"select"} name={'customerId'} value={customerId} onChange={handleChange}>
                                    <option value={0} key={0}>Choose Customer</option>
                                    {
                                        customers.map(s =>
                                            <option key={s.id} value={s.id}>{s.firstName}</option>)
                                    }
                                </Input>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Address
                            </th>
                            <td>
                                <Input type={"select"} name={'addressId'} value={addressId} onChange={handleChange}>
                                    <option value={0} key={0}>Choose Address</option>
                                    {
                                        addresses.map(s =>
                                            <option key={s.id} value={s.id}>{s.street}</option>)
                                    }
                                </Input>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                Payment Options
                            </th>
                            <td>
                                <Input type={"select"} name={'paymentId'} value={paymentId} onChange={handleChange}>
                                    <option value={0} key={0}>Choose Payment</option>
                                    {
                                        payments.map(s =>
                                            <option key={s.id} value={s.id}>{s.cardNumber}</option>)
                                    }
                                </Input>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" colSpan={2} style={{background: "#7386D5", color: "white"}}>
                                Order Items
                            </th>
                        </tr>
                        <tr>
                            <td colSpan={2}>
                                <Form inline onSubmit={handleItemAdd}>
                                    <FormGroup className="mb-2 mr-sm-2 mb-sm-0">
                                        <Label for="productId" className="mr-sm-2">Product</Label>
                                        <Input type={"select"} name={'productId'} id={'productId'} value={productId}
                                               onChange={handleChange}>
                                            <option value={0} key={0}>Choose Product</option>
                                            {
                                                products.map(s =>
                                                    <option key={s.id} value={s.id}>{s.name.substring(0, 30)}</option>)
                                            }
                                        </Input>
                                    </FormGroup>
                                    <FormGroup className="mb-2 mr-sm-2 mb-sm-0">
                                        <Label for="quantity" className="mr-sm-2">Quantity</Label>
                                        <Input type="number" name="quantity" id="quantity" value={quantity}
                                               placeholder="Input your quantity" onChange={handleChange}/>
                                    </FormGroup>
                                    <Button>Add</Button>
                                </Form>
                            </td>
                        </tr>
                        </tbody>
                    </Table>
                    <Table>
                        <tbody>
                        {
                            items.map(i =>
                                <OrderItemComponent key={i.id} id={i.id} name={i.name} quantity={i.quantity}
                                                    handleClick={handleItemClick} handleDelete={handeItemRemove}/>
                            )
                        }
                        </tbody>
                    </Table>
                    {error.length > 0 ? <Alert color="danger">{error}</Alert> : ''}
                    <Toolbar>
                        <Button onClick={handleSave} color={'primary'}>Save</Button>
                        <Link href={'/order/list'}><Button color={'secondary'}>Cancel</Button></Link>
                    </Toolbar>
                </Container>
            </div>
        </Layout>
    )
};

OrderEdit.getInitialProps = async (ctx) => {
    const {server} = ServerRepo(ctx);
    const customers = await server.getApi(CustomerApi).list();
    const products = await server.getApi(ProductApi).list();
    return {customers, products}
};

export default OrderEdit;