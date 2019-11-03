import * as React from "react";
import {FunctionComponent} from "react";

type Props = {}
const Toolbar: FunctionComponent<Props> = ({
                                               children
                                           }) => <div className='toolbar'>
    {children}
</div>

export default Toolbar;