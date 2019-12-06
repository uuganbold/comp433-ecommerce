import {FunctionComponent} from "react";
import {Button} from "reactstrap";

type Props = {
    id: number,
    name: string,
    quantity: number,
    handleClick: (id: number) => void,
    handleDelete: (id: number) => void
}

const OrderItemComponent: FunctionComponent<Props> = ({
                                                          id,
                                                          name,
                                                          quantity,
                                                          handleClick,
                                                          handleDelete
                                                      }) => {
    return (
        <tr key={id}>
            <td><a onClick={() => handleClick(id)}>{name}</a><Button close onClick={() => handleDelete(id)}/></td>
            <td>{quantity}</td>
        </tr>
    )
}

export default OrderItemComponent