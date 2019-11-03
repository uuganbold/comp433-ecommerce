import Layout from "../components/Layout/Layout";
import {Alert, Form, FormGroup, Input, Label} from "reactstrap";
import {ChangeEvent, useContext, useState} from "react";
import AppContext from "../components/AppContext/AppContext";
import {MyPage} from "../MyPage";


const Home: MyPage = ({}) => {

    const {api, handleChangeApi} = useContext(AppContext);

    const [success, setSuccess] = useState(false);

    const handleChange = (e: ChangeEvent) => {
        handleChangeApi((e.target as HTMLInputElement).value);
        setSuccess(true);
    };
    return (
        <Layout>
            <div>
                <h1>Hello world!</h1>
                <Alert color="info">We have two implementations of the web service. Which one would you like to
                    use?</Alert>
                <Form>
                    <FormGroup tag="fieldset">
                        <legend>Implementations</legend>
                        <FormGroup check>
                            <Label check>
                                <Input type="radio" name="api" value="spring" checked={api == 'spring'}
                                       onChange={handleChange}/>{' '}
                                Spring MVC implementation
                            </Label>
                        </FormGroup>
                        <FormGroup check>
                            <Label check>
                                <Input type="radio" name="api" value="jaxrs" checked={api == 'jaxrs'}
                                       onChange={handleChange}/>{' '}
                                JAX-RS implementation
                            </Label>
                        </FormGroup>
                    </FormGroup>
                </Form>
                <Alert color="success" hidden={!success}>Web service implementation changed</Alert>
            </div>
        </Layout>
    )
};

export default Home;