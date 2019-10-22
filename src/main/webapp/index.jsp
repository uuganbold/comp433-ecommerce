<html>
<head>
    <style>
        #log {
            display: flex;
            flex-direction: column;
        }

        .log {
            display: flex;
            flex-direction: row;
            padding: 5px;
        }

        .log.message {
            flex-grow: 1;
        }

        .log div {
            padding: 3px;
        }

        .level {
            color: green;
        }

        .resource {
            color: dodgerblue;
        }

        .api {
            color: darkblue;
        }

        .error {
            color: red;
        }
    </style>
</head>
<body>
<h2>Web Service JS Testing Log</h2>
<div id="log">
</div>
<script type="application/javascript">
    class Logger {
        panel;
        logTemplate = '<div class="log"><div class="level"></div><div class="resource"></div><div class="api"></div><div class="message"></div></div>';

        constructor() {
            this.panel = document.getElementById("log")
        }

        error = function (message, resource, api) {
            console.error(message);
            const log = this.htmlToElement(this.logTemplate);
            const levelDiv = log.querySelector(".level");
            levelDiv.classList.add("error");
            levelDiv.innerHTML = "ERROR";
            const messageDiv = log.querySelector(".message");
            messageDiv.innerHTML = message;
            if (resource != null) {
                let resDiv = log.querySelector(".resource");
                resDiv.classList.add("error")
                resDiv.innerHTML = resource;
            }

            if (api != null) {
                let apiDev = log.querySelector(".api");
                apiDev.classList.add("error")
                apiDev.innerHTML = resource;
            }

            this.panel.appendChild(log);

        };
        log = function (message, resource, api) {
            console.log(message);
            const log = this.htmlToElement(this.logTemplate);
            const levelDiv = log.querySelector(".level");
            levelDiv.innerHTML = "LOG";
            const messageDiv = log.querySelector(".message");
            messageDiv.innerHTML = message;
            if (resource != null)
                log.querySelector(".resource").innerHTML = resource;
            if (api != null)
                log.querySelector(".api").innerHTML = api;
            this.panel.appendChild(log);

        };
        htmlToElement = function (html) {
            const template = document.createElement('template');
            html = html.trim(); // Never return a text node of whitespace as the result
            template.innerHTML = html;
            return template.content.firstChild;
        }
    }

    const logger = new Logger();

    async function testApi(baseURL) {
        /**
         * Testing category web services;
         */
        const HEADER = {'Accept': 'application/json', 'Content-Type': 'application/json'};
        let response;
        let json;
        let temp;

        let categories = [];
        let sellers = [];
        let customers = [];
        let products = [];
        let reviews = [];
        let orders = [];

        logger.log("Starting to test categories", "CATEGORY");

        logger.log("Calling: /" + baseURL + "/categories", "CATEGORY", "/categories");
        response = await fetch(baseURL + '/categories', {headers: HEADER, method: 'GET'});
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Category count:" + json.length)
            Array.prototype.push.apply(categories, json);
            logger.log("Category count local:" + categories.length);
        } else {
            logger.error(JSON.stringify(await response.json(), null, 2), "CATEGORY", "/categories")
        }

        logger.log("Adding categories /" + baseURL + "/categories", "CATEGORY", "POST /categories");
        response = await fetch(baseURL + '/categories', {
            headers: HEADER,
            method: 'POST',
            body: JSON.stringify({"name": "New Category" + Math.random()})
        });
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("New category id:" + json.id);
            categories.push(json);
            logger.log("Category count local:" + categories.length);
        } else {
            logger.error(JSON.stringify(await response.json()), "CATEGORY", "POST /categories")
        }

        temp = json.id;
        logger.log("getting category /" + baseURL + "/category/" + temp, "CATEGORY", "/category/{id}");
        response = await fetch(baseURL + '/category/' + temp, {headers: HEADER, method: 'GET'});
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Category id:" + json.id);
            if (temp != json.id) {
                logger.error("Cannot match category id");
            }
            logger.log("Category count local:" + categories.length);
        } else {
            logger.error(JSON.stringify(await response.json()), "CATEGORY", "/category/{id}")
        }

        temp = json.id;
        logger.log("updating category /" + baseURL + "/category/" + temp, "CATEGORY", "PUT /category/{id}");
        response = await fetch(baseURL + '/category/' + temp, {
            headers: HEADER,
            method: 'PUT',
            body: JSON.stringify({"name": "New Category" + Math.random()})
        });
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Category id:" + json.id);
            if (temp != json.id) {
                logger.error("Cannot match category id");
            }
            categories.splice(categories.length - 1);
            categories.push(json);
            logger.log("Category count local:" + categories.length);
        } else {
            logger.error(JSON.stringify(await response.json()), "CATEGORY", "PUT /category/{id}")
        }

        let i = categories.length;
        while (i--) {
            let c = categories[i];
            if (c.name.startsWith('New Category')) {
                temp = c.id;
                logger.log("deleting category /" + baseURL + "/category/" + temp, "CATEGORY", "DELETE /category/{id}");
                response = await fetch(baseURL + '/category/' + temp, {headers: HEADER, method: 'DELETE'});
                if (response.ok) {
                    categories.splice(i, 1);
                } else {
                    logger.error(JSON.stringify(await response.json()), "CATEGORY", "DELETE /category/{id}")
                }
                logger.log("Category count local:" + categories.length);
            }
        }

        /**
         * Testing seller web services
         */

        logger.log("Starting to test seller", "SELLER");

        logger.log("Calling: /" + baseURL + "/sellers", "SELLER", "/sellers");
        response = await fetch(baseURL + '/sellers', {headers: HEADER, method: 'GET'});
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Seller count:" + json.length)
            Array.prototype.push.apply(sellers, json);
            logger.log("Seller count local:" + sellers.length);
        } else {
            logger.error(JSON.stringify(await response.json()), "SELLER", "/sellers")
        }

        logger.log("Adding sellers /" + baseURL + "/sellers", "SELLER", "POST /sellers");
        response = await fetch(baseURL + '/sellers', {
            headers: HEADER,
            method: 'POST',
            body: JSON.stringify({"name": "New Seller" + Math.random(), "website": "http://localhost.com"})
        });
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("New seller id:" + json.id);
            sellers.push(json);
            logger.log("Seller count local:" + sellers.length);
        } else {
            logger.error(JSON.stringify(await response.json()), "SELLER", "POST /sellers")
        }

        temp = json.id;
        logger.log("getting seller /" + baseURL + "/seller/" + temp, "SELLER", "/seller/{id}");
        response = await fetch(baseURL + '/seller/' + temp, {headers: HEADER, method: 'GET'});
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Seller id:" + json.id);
            if (temp != json.id) {
                logger.error("Cannot match seller id");
            }
            logger.log("Seller count local:" + sellers.length);
        } else {
            logger.error(JSON.stringify(await response.json()), "SELLER", "/seller/{id}")
        }

        temp = json.id;
        logger.log("updating seller /" + baseURL + "/seller/" + temp, "SELLER", "PUT /seller/{id}");
        response = await fetch(baseURL + '/seller/' + temp, {
            headers: HEADER,
            method: 'PUT',
            body: JSON.stringify({
                "name": "New Seller" + Math.random(),
                "website": "http://newssite.com",
                "email": "test@email.com"
            })
        });
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Seller id:" + json.id);
            if (temp != json.id) {
                logger.error("Cannot match seller id");
            }
            sellers.splice(sellers.length - 1);
            sellers.push(json);
            logger.log("Seller count local:" + sellers.length);
        } else {
            logger.error(JSON.stringify(await response.json()), "SELLER", "PUT /seller/{id}")
        }

        sellers[sellers.length - 1].addresses = [];
        temp = sellers[sellers.length - 1].id;
        for (i = 0; i < 3; i++) {
            logger.log("Adding address to seller: /" + baseURL + "/seller/" + temp + "/addresses", "SELLER", "POST /seller/{id}/addresses");
            response = await fetch(baseURL + '/seller/' + temp + '/addresses', {
                headers: HEADER, method: 'POST',
                body: JSON.stringify({
                    "country": "Naturacountry",
                    "street": "540 Some Ave",
                    unit: "#100" + i,
                    city: "Beauticity"
                })
            });
            if (response.ok) {
                json = await response.json();
                logger.log(JSON.stringify(json, null, 2));
                logger.log("New Address id:" + json.id);
                sellers[sellers.length - 1].addresses.push(json);
                logger.log(JSON.stringify(sellers[sellers.length - 1]));
            } else {
                logger.error(JSON.stringify(await response.json()), "SELLER", "POST /seller/{id}/addresses")
            }
        }


        logger.log("get seller's addresses /" + baseURL + "/seller/" + temp + '/addresses', "SELLER", "/seller/{id}/addresses");
        response = await fetch(baseURL + '/seller/' + temp + '/addresses', {
            headers: HEADER,
            method: 'GET'
        });
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Address count:" + json.length);
            logger.log("Address count local:" + sellers[sellers.length - 1].addresses.length);
        } else {
            logger.error(JSON.stringify(await response.json()), "SELLER", "/seller/{id}/addresses")
        }

        i = sellers[sellers.length - 1].addresses.length;
        temp = sellers[sellers.length - 1].id;
        while (i--) {
            let c = sellers[sellers.length - 1].addresses[i];
            logger.log("deleting seller's address /" + baseURL + "/seller/" + temp + "/address/" + c.id, "SELLER", "DELETE /seller/{id}/address/{aid}");
            response = await fetch(baseURL + '/seller/' + temp + "/address/" + c.id, {
                headers: HEADER,
                method: 'DELETE'
            });
            if (response.ok) {
                sellers[sellers.length - 1].addresses.splice(i, 1);
            } else {
                logger.error(JSON.stringify(await response.json()), "SELLER", "DELETE /seller/{id}/address/{aid}")
            }
            logger.log("Seller's address count local:" + sellers[sellers.length - 1].addresses.length);

        }

        i = sellers.length;
        while (i--) {
            let c = sellers[i];
            if (c.name.startsWith('New Seller')) {
                temp = c.id;
                logger.log("deleting seller /" + baseURL + "/seller/" + temp, "SELLER", "DELETE /seller/{id}");
                response = await fetch(baseURL + '/seller/' + temp, {headers: HEADER, method: 'DELETE'});
                if (response.ok) {
                    sellers.splice(i, 1);
                } else {
                    logger.error(JSON.stringify(await response.json()), "SELLER", "DELETE /seller/{id}")
                }
                logger.log("Seller count local:" + sellers.length);
            }
        }


        /**
         * Testing customer web services
         */

        logger.log("Starting to test customer", "CUSTOMER");

        logger.log("Calling: /" + baseURL + "/customers", "CUSTOMER", "/customers");
        response = await fetch(baseURL + '/customers', {headers: HEADER, method: 'GET'});
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Customer count:" + json.length);
            logger.log("Customer count local:" + customers.length);
            customers = json
        } else {
            logger.error(JSON.stringify(await response.json()), "CUSTOMER", "/customers")
        }

        if (customers.length == 0) {
            logger.log("Adding customer /" + baseURL + "/customers", "CUSTOMER", "POST /customer");
            response = await fetch(baseURL + '/customers', {
                headers: HEADER,
                method: 'POST',
                body: JSON.stringify({
                    "firstName": "Good",
                    "lastName": "Customer",
                    "email": "good@customer.com",
                    "phonenumber": "100-100-1000"
                })
            });
            if (response.ok) {
                json = await response.json();
                logger.log(JSON.stringify(json, null, 2));
                logger.log("New customer id:" + json.id);
                customers.push(json);
                logger.log("Customer count local:" + customers.length);
            } else {
                logger.error(JSON.stringify(await response.json()), "CUSTOMER", "POST /customers")
            }
        }

        for (i = 0; i < 3; i++) {
            logger.log("Adding customer /" + baseURL + "/customers", "CUSTOMER", "POST /customer");
            response = await fetch(baseURL + '/customers', {
                headers: HEADER,
                method: 'POST',
                body: JSON.stringify({
                    "firstName": "New Customer" + i + Math.floor(Math.random() * 10),
                    "lastName": "Last",
                    "email": "good" + i + "@customer.com",
                })
            });
            if (response.ok) {
                json = await response.json();
                logger.log(JSON.stringify(json, null, 2));
                logger.log("New customer id:" + json.id);
                customers.push(json);
                logger.log("Customer count local:" + customers.length);
            } else {
                logger.error(JSON.stringify(await response.json()), "CUSTOMER", "POST /customers")
            }
        }

        logger.log("Calling: /" + baseURL + "/customers", "CUSTOMER", "/customers");
        response = await fetch(baseURL + '/customers', {headers: HEADER, method: 'GET'});
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Customer count:" + json.length);
            logger.log("Customer count local:" + customers.length);
            customers = json
        } else {
            logger.error(JSON.stringify(await response.json()), "CUSTOMER", "/customers")
        }


        temp = json[1].id;
        logger.log("getting customer /" + baseURL + "/customer/" + temp, "CUSTOMER", "/customer/{id}");
        response = await fetch(baseURL + '/customer/' + temp, {headers: HEADER, method: 'GET'});
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Customer id:" + json.id);
            if (temp != json.id) {
                logger.error("Cannot match customer id");
            }
            logger.log("Customer count local:" + customers.length);
        } else {
            logger.error(JSON.stringify(await response.json()), "CUSTOMER", "/customer/{id}")
        }


        logger.log("updating customer /" + baseURL + "/customer/" + temp, "CUSTOMER", "PUT /customer/{id}");
        response = await fetch(baseURL + '/customer/' + temp, {
            headers: HEADER,
            method: 'PUT',
            body: JSON.stringify({
                "firstName": "New Customer" + Math.floor(Math.random() * 10),
                "lastName": "Customer",
                "email": "besty" + temp + "@customer.com",
                "phonenumber": "999-999-9999"
            })
        });
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Customer id:" + json.id);
            if (temp != json.id) {
                logger.error("Cannot match customer id");
            }
            customers[1] = json;
            logger.log("Customer count local:" + customers.length);
        } else {
            logger.error(JSON.stringify(await response.json()), "CUSTOMER", "PUT /customer/{id}")
        }

        temp = customers[0].id;
        logger.log("get customer's addresses /" + baseURL + "/customer/" + temp + '/addresses', "CUSTOMER", "/customer/{id}/addresses");
        response = await fetch(baseURL + '/customer/' + temp + '/addresses', {
            headers: HEADER,
            method: 'GET'
        });
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Address count:" + json.length);
            customers[0].addresses = json;
        } else {
            logger.error(JSON.stringify(await response.json()), "CUSTOMER", "/customer/{id}/addresses")
        }


        if (customers[0].addresses.length == 0) {
            for (i = 0; i < 2; i++) {
                logger.log("Adding address to customer: /" + baseURL + "/customer/" + temp + "/addresses", "CUSTOMER", "POST /customer/{id}/addresses");
                response = await fetch(baseURL + '/customer/' + temp + '/addresses', {
                    headers: HEADER, method: 'POST',
                    body: JSON.stringify({
                        "country": "Count",
                        "street": "000 Some Ave" + i,
                        unit: "#999" + i,
                        city: "Cit"
                    })
                });
                if (response.ok) {
                    json = await response.json();
                    logger.log(JSON.stringify(json, null, 2));
                    logger.log("New Address id:" + json.id);
                    customers[0].addresses.push(json);
                    logger.log(JSON.stringify(customers[0]));
                } else {
                    logger.error(JSON.stringify(await response.json()), "CUSTOMER", "POST /customer/{id}/addresses")
                }
            }
        }


        customers[1].addresses = [];
        temp = customers[1].id;
        for (i = 0; i < 5; i++) {
            logger.log("Adding address to customer: /" + baseURL + "/customer/" + temp + "/addresses", "CUSTOMER", "POST /customer/{id}/addresses");
            response = await fetch(baseURL + '/customer/' + temp + '/addresses', {
                headers: HEADER, method: 'POST',
                body: JSON.stringify({"country": "Count", "street": "000 Some Ave" + i, unit: "#999" + i, city: "Cit"})
            });
            if (response.ok) {
                json = await response.json();
                logger.log(JSON.stringify(json, null, 2));
                logger.log("New Address id:" + json.id);
                customers[1].addresses.push(json);
                logger.log(JSON.stringify(customers[1]));
            } else {
                logger.error(JSON.stringify(await response.json()), "CUSTOMER", "POST /customer/{id}/addresses")
            }
        }


        logger.log("get customer's addresses /" + baseURL + "/customer/" + temp + '/addresses', "CUSTOMER", "/customer/{id}/addresses");
        response = await fetch(baseURL + '/customer/' + temp + '/addresses', {
            headers: HEADER,
            method: 'GET'
        });
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Address count:" + json.length);
            logger.log("Address count local:" + customers[1].addresses.length);
            customers[1].addresses = json;
        } else {
            logger.error(JSON.stringify(await response.json()), "CUSTOMER", "/customer/{id}/addresses")
        }

        i = customers[1].addresses.length;
        temp = customers[1].id;
        while (i-- > 2) {
            let c = customers[1].addresses[i];
            logger.log("deleting customer's address /" + baseURL + "/customer/" + temp + "/address/" + c.id, "CUSTOMER", "DELETE /customer/{id}/address/{aid}");
            response = await fetch(baseURL + '/customer/' + temp + "/address/" + c.id, {
                headers: HEADER,
                method: 'DELETE'
            });
            if (response.ok) {
                customers[1].addresses.splice(i, 1);
            } else {
                logger.error(JSON.stringify(await response.json()), "CUSTOMER", "DELETE /customer/{id}/address/{aid}")
            }
            logger.log("Customer's address count local:" + customers[1].addresses.length);

        }


        temp = customers[0].id;
        logger.log("get customer's payments /" + baseURL + "/customer/" + temp + '/payments', "CUSTOMER", "/customer/{id}/payments");
        response = await fetch(baseURL + '/customer/' + temp + '/payments', {
            headers: HEADER,
            method: 'GET'
        });
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Payment count:" + json.length);
            customers[0].payments = json;
        } else {
            logger.error(JSON.stringify(await response.json()), "CUSTOMER", "/customer/{id}/payments")
        }


        if (customers[0].payments.length == 0) {
            for (i = 0; i < 2; i++) {
                logger.log("Adding payment to customer: /" + baseURL + "/customer/" + temp + "/payments", "CUSTOMER", "POST /customer/{id}/payments");
                response = await fetch(baseURL + '/customer/' + temp + '/payments', {
                    headers: HEADER, method: 'POST',
                    body: JSON.stringify({
                        "nameOnCard": "Good Customer",
                        "cardNumber": "1234-5678-9101-123" + i,
                        expireMonth: i + 1,
                        expireYear: 2020 + i,
                        addressId: customers[0].addresses[0].id
                    })
                });
                if (response.ok) {
                    json = await response.json();
                    logger.log(JSON.stringify(json, null, 2));
                    logger.log("New Payment id:" + json.id);
                    customers[0].payments.push(json);
                    logger.log(JSON.stringify(customers[0]));
                } else {
                    logger.error(JSON.stringify(await response.json()), "CUSTOMER", "POST /customer/{id}/payments")
                }
            }
        }


        customers[1].payments = [];
        temp = customers[1].id;
        for (i = 0; i < 5; i++) {
            logger.log("Adding payment to customer: /" + baseURL + "/customer/" + temp + "/payments", "CUSTOMER", "POST /customer/{id}/payments");
            response = await fetch(baseURL + '/customer/' + temp + '/payments', {
                headers: HEADER, method: 'POST',
                body: JSON.stringify({
                    "nameOnCard": "New Customer",
                    "cardNumber": "0000-0000-0000-000" + i,
                    expireMonth: i + 1,
                    expireYear: 2020 + i,
                    addressId: customers[1].addresses[0].id
                })
            });
            if (response.ok) {
                json = await response.json();
                logger.log(JSON.stringify(json, null, 2));
                logger.log("New Payment id:" + json.id);
                customers[1].payments.push(json);
                logger.log(JSON.stringify(customers[1]));
            } else {
                logger.error(JSON.stringify(await response.json()), "CUSTOMER", "POST /customer/{id}/payments")
            }
        }


        logger.log("get customer's payments /" + baseURL + "/customer/" + temp + '/payments', "CUSTOMER", "/customer/{id}/payments");
        response = await fetch(baseURL + '/customer/' + temp + '/payments', {
            headers: HEADER,
            method: 'GET'
        });
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Payment count:" + json.length);
            logger.log("Payment count local:" + customers[1].payments.length);
            customers[1].payments = json;
        } else {
            logger.error(JSON.stringify(await response.json()), "CUSTOMER", "/customer/{id}/payments")
        }

        i = customers[1].payments.length;
        temp = customers[1].id;
        while (i-- > 2) {
            let c = customers[1].payments[i];
            logger.log("deleting customer's payment /" + baseURL + "/customer/" + temp + "/payment/" + c.id, "CUSTOMER", "DELETE /customer/{id}/payment/{aid}");
            response = await fetch(baseURL + '/customer/' + temp + "/payment/" + c.id, {
                headers: HEADER,
                method: 'DELETE'
            });
            if (response.ok) {
                customers[1].payments.splice(i, 1);
            } else {
                logger.error(JSON.stringify(await response.json()), "CUSTOMER", "DELETE /customer/{id}/payment/{aid}")
            }
            logger.log("Customer's payment count local:" + customers[1].payments.length);

        }

        i = customers.length;
        while (i--) {
            let c = customers[i];
            if (c.firstName.startsWith('New Customer')) {
                temp = c.id;
                logger.log("deleting customer /" + baseURL + "/customer/" + temp, "CUSTOMER", "DELETE /customer/{id}");
                response = await fetch(baseURL + '/customer/' + temp, {headers: HEADER, method: 'DELETE'});
                if (response.ok) {
                    customers.splice(i, 1);
                } else {
                    logger.error(JSON.stringify(await response.json()), "CUSTOMER", "DELETE /customer/{id}")
                }
                logger.log("Customer count local:" + customers.length);
            }
        }



        /**
         * Testing product web services;
         */

        logger.log("Starting to test products", "PRODUCT");

        logger.log("Calling: /" + baseURL + "/products", "PRODUCT", "/products");
        response = await fetch(baseURL + '/products', {headers: HEADER, method: 'GET'});
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Product count:" + json.length)
            products = json;
            logger.log("Product count local:" + products.length);
        } else {
            logger.error(JSON.stringify(await response.json()), "PRODUCT", "/products")
        }

        logger.log("Searching: /" + baseURL + "/products?q=Levi", "PRODUCT", "/products?q={query}");
        response = await fetch(baseURL + '/products?q=Levi', {headers: HEADER, method: 'GET'});
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Product count:" + json.length)
            logger.log("Product count local:" + products.length);
        } else {
            logger.error(JSON.stringify(await response.json()), "PRODUCT", "/products?q={query}")
        }

        logger.log("Adding products /" + baseURL + "/products", "PRODUCT", "POST /products");
        response = await fetch(baseURL + '/products', {
            headers: HEADER,
            method: 'POST',
            body: JSON.stringify({
                "name": "New Product" + Math.random(),
                "description": "New Prodct Description" + Math.random(),
                "listPrice": Math.floor(Math.random() * 20) + 1,
                "availableQuantity": Math.floor(Math.random() * 1000) + 1,
                "sellerId": Math.floor(Math.random() * 2) + 1,
                "categoryId": Math.floor(Math.random() * 3) + 1
            })
        });
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("New product id:" + json.id);
            products.push(json);
            logger.log("Product count local:" + products.length);
        } else {
            logger.error(JSON.stringify(await response.json()), "PRODUCT", "POST /products")
        }

        temp = json.id;
        logger.log("getting product /" + baseURL + "/product/" + temp, "PRODUCT", "/product/{id}");
        response = await fetch(baseURL + '/product/' + temp, {headers: HEADER, method: 'GET'});
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Product id:" + json.id);
            if (temp != json.id) {
                logger.error("Cannot match product id");
            }
            logger.log("Product count local:" + products.length);
        } else {
            logger.error(JSON.stringify(await response.json()), "PRODUCT", "/product/{id}")
        }

        temp = json.id;
        logger.log("updating product /" + baseURL + "/product/" + temp, "PRODUCT", "PUT /product/{id}");
        response = await fetch(baseURL + '/product/' + temp, {
            headers: HEADER,
            method: 'PUT',
            body: JSON.stringify({
                "name": "New Product Up" + Math.random(),
                "description": "New Prodct Description Up" + Math.random(),
                "listPrice": Math.floor(Math.random() * 20) + 1,
                "availableQuantity": Math.floor(Math.random() * 1000) + 1,
                "sellerId": Math.floor(Math.random() * 2) + 1,
                "categoryId": Math.floor(Math.random() * 3) + 1
            })
        });
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Product id:" + json.id);
            if (temp != json.id) {
                logger.error("Cannot match product id");
            }
            products[products.length - 1] = json
            logger.log("Product count local:" + products.length);
        } else {
            logger.error(JSON.stringify(await response.json()), "PRODUCT", "PUT /product/{id}")
        }

        i = products.length;
        while (i--) {
            let c = products[i];
            if (c.name.startsWith('New Product')) {
                temp = c.id;
                logger.log("deleting product /" + baseURL + "/product/" + temp, "PRODUCT", "DELETE /product/{id}");
                response = await fetch(baseURL + '/product/' + temp, {headers: HEADER, method: 'DELETE'});
                if (response.ok) {
                    products.splice(i, 1);
                } else {
                    logger.error(JSON.stringify(await response.json()), "PRODUCT", "DELETE /product/{id}")
                }
                logger.log("Product count local:" + products.length);
            }
        }


        /**
         * Testing review web services;
         */

        logger.log("Starting to test review", "REVIEW");

        for (i = 0; i < 5; i++) {
            logger.log("Adding review /" + baseURL + "/reviews", "REVIEW", "POST /reviews");
            response = await fetch(baseURL + '/reviews', {
                headers: HEADER,
                method: 'POST',
                body: JSON.stringify({
                    "productId": Math.floor(Math.random() * 2) + 1,
                    "customerId": customers[0].id,
                    "star": Math.floor(Math.random() * 5) + 1,
                    "comment": "it is all about this product" + Math.random()
                })
            });
            if (response.ok) {
                json = await response.json();
                logger.log(JSON.stringify(json, null, 2));
                logger.log("New Review id:" + json.id);
                reviews.push(json);
                logger.log("Review count local:" + reviews.length);
            } else {
                logger.error(JSON.stringify(await response.json()), "REVIEW", "POST /reviews")
            }
        }


        logger.log("Calling: /" + baseURL + "/reviews", "REVIEW", "/reviews");
        response = await fetch(baseURL + '/reviews', {headers: HEADER, method: 'GET'});
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Review count:" + json.length)
            reviews = json;
        } else {
            logger.error(JSON.stringify(await response.json()), "REVIEW", "/reviews")
        }


        temp = reviews[0].id;
        logger.log("getting review /" + baseURL + "/reviews/" + temp, "REVIEW", "/review/{id}");
        response = await fetch(baseURL + '/review/' + temp, {headers: HEADER, method: 'GET'});
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Review id:" + json.id);
            if (temp != json.id) {
                logger.error("Cannot match review id");
            }
            logger.log("Review count local:" + reviews.length);
            reviews[0] = json;
        } else {
            logger.error(JSON.stringify(await response.json()), "REVIEW", "/review/{id}")
        }

        temp = json.id;
        logger.log("updating review /" + baseURL + "/review/" + temp, "REVIEW", "PUT /review/{id}");
        response = await fetch(baseURL + '/review/' + temp, {
            headers: HEADER,
            method: 'PUT',
            body: JSON.stringify({
                "productId": Math.floor(Math.random() * 2) + 1,
                "customerId": customers[0].id,
                "star": Math.floor(Math.random() * 5) + 1,
                "comment": "it is review about this product" + Math.random()
            })
        });
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Review id:" + json.id);
            if (temp != json.id) {
                logger.error("Cannot match review id");
            }
            reviews[reviews.length - 1] = json
            logger.log("Review count local:" + reviews.length);
        } else {
            logger.error(JSON.stringify(await response.json()), "REVIEW", "PUT /review/{id}")
        }

        i = reviews.length;
        while (i-- > 2) {
            let c = reviews[i];
            temp = c.id;
            logger.log("deleting review /" + baseURL + "/review/" + temp, "REVIEW", "DELETE /review/{id}");
            response = await fetch(baseURL + '/review/' + temp, {headers: HEADER, method: 'DELETE'});
            if (response.ok) {
                reviews.splice(i, 1);
            } else {
                logger.error(JSON.stringify(await response.json()), "REVIEW", "DELETE /review/{id}")
            }
            logger.log("Review count local:" + reviews.length);

        }


        /**
         * Testing order web services;
         */

        logger.log("Starting to test order", "ORDER");

        for (i = 0; i < 5; i++) {
            logger.log("Adding order /" + baseURL + "/orders", "ORDER", "POST /orders");
            response = await fetch(baseURL + '/orders', {
                headers: HEADER,
                method: 'POST',
                body: JSON.stringify({
                    "customerId": customers[0].id,
                    "items": [
                        {
                            "productId": products[0].id,
                            "quantity": Math.floor(Math.random() * 10) + 3
                        },
                        {
                            "productId": products[1].id,
                            "quantity": Math.floor(Math.random() * 10) + 3
                        },
                    ],
                    "addressId": customers[0].addresses[Math.floor(Math.random() * 2)].id,
                    "paymentId": customers[0].payments[Math.floor(Math.random() * 2)].id
                })
            });
            if (response.ok) {
                json = await response.json();
                logger.log(JSON.stringify(json, null, 2));
                logger.log("New Order id:" + json.id);
                orders.push(json);
                logger.log("Order count local:" + orders.length);
            } else {
                logger.error(JSON.stringify(await response.json(), null, 2), "ORDER", "POST /orders")
            }

        }

        logger.log("Calling: /" + baseURL + "/order/" + orders[0].id, "ORDER", "/order/{id}");
        response = await fetch(baseURL + '/order/' + orders[0].id, {headers: HEADER, method: 'GET'});
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
        } else {
            logger.error(JSON.stringify(await response.json()), "ORDER", "/orders")
        }

        logger.log("Calling: /" + baseURL + "/orders", "ORDER", "/orders");
        response = await fetch(baseURL + '/orders', {headers: HEADER, method: 'GET'});
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Order count:" + json.length)
            orders = json;
        } else {
            logger.error(JSON.stringify(await response.json()), "ORDER", "/orders")
        }

        logger.log("Cancelling order: /" + baseURL + "/order/" + orders[0].id + "/cancel", "ORDER", "PUT /order/{id}/cancel");
        response = await fetch(baseURL + "/order/" + orders[0].id + "/cancel", {headers: HEADER, method: 'PUT'});
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Order status:" + json.status)
        } else {
            logger.error(JSON.stringify(await response.json()), "ORDER", "PUT /order/{id}/cancel")
        }

        logger.log("Shipping order: /" + baseURL + "/order/" + orders[1].id + "/ship", "ORDER", "PUT /order/{id}/ship");
        response = await fetch(baseURL + "/order/" + orders[1].id + "/ship", {headers: HEADER, method: 'PUT'});
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Order status:" + json.status)
        } else {
            logger.error(JSON.stringify(await response.json()), "ORDER", "PUT /order/{id}/ship")
        }

        logger.log("Delivering order: /" + baseURL + "/order/" + orders[1].id + "/deliver", "ORDER", "PUT /order/{id}/deliver");
        response = await fetch(baseURL + "/order/" + orders[1].id + "/deliver", {headers: HEADER, method: 'PUT'});
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json, null, 2));
            logger.log("Order status:" + json.status)
        } else {
            logger.error(JSON.stringify(await response.json()), "ORDER", "PUT /order/{id}/deliver")
        }


        /*
                logger.log("Calling: /" + baseURL + "/reviews","REVIEW","/reviews");
                response = await fetch(baseURL + '/reviews', {headers: HEADER, method: 'GET'});
                if (response.ok) {
                    json = await response.json();
                    logger.log(JSON.stringify(json,null,2));
                    logger.log("Review count:" + json.length)
                    reviews=json;
                } else {
                    logger.error(JSON.stringify(await response.json()), "REVIEW","/reviews")
                }


                temp = reviews[0].id;
                logger.log("getting review /" + baseURL + "/reviews/" + temp,"REVIEW","/review/{id}");
                response = await fetch(baseURL + '/review/' + temp, {headers: HEADER, method: 'GET'});
                if (response.ok) {
                    json = await response.json();
                    logger.log(JSON.stringify(json,null,2));
                    logger.log("Review id:" + json.id);
                    if (temp != json.id) {
                        logger.error("Cannot match review id");
                    }
                    logger.log("Review count local:" + reviews.length);
                    reviews[0]=json;
                } else {
                    logger.error(JSON.stringify(await response.json()),"REVIEW","/review/{id}")
                }

                temp = json.id;
                logger.log("updating review /" + baseURL + "/review/" + temp,"REVIEW","PUT /review/{id}");
                response = await fetch(baseURL + '/review/' + temp, {
                    headers: HEADER,
                    method: 'PUT',
                    body: JSON.stringify({
                        "productId":Math.floor(Math.random()*2)+1,
                        "customerId":customers[0].id,
                        "star":Math.floor(Math.random()*5)+1,
                        "comment":"it is review about this product"+Math.random()
                    })
                });
                if (response.ok) {
                    json = await response.json();
                    logger.log(JSON.stringify(json,null,2));
                    logger.log("Review id:" + json.id);
                    if (temp != json.id) {
                        logger.error("Cannot match review id");
                    }
                    reviews[reviews.length-1]=json
                    logger.log("Review count local:" + reviews.length);
                } else {
                    logger.error(JSON.stringify(await response.json()),"REVIEW","PUT /review/{id}")
                }

                i = reviews.length;
                while (i-->2) {
                    let c = reviews[i];
                    temp = c.id;
                    logger.log("deleting review /" + baseURL + "/review/" + temp,"REVIEW","DELETE /review/{id}");
                    response = await fetch(baseURL + '/review/' + temp, {headers: HEADER, method: 'DELETE'});
                    if (response.ok) {
                        reviews.splice(i, 1);
                    } else {
                        logger.error(JSON.stringify(await response.json()),"REVIEW","DELETE /review/{id}")
                    }
                    logger.log("Review count local:" + reviews.length);

                }*/
    }

    async function test() {
        logger.log("#######TESTING WEB SERVICE IMPLEMENTED WITH SPRING#######");
        await testApi('app');
        logger.log("#######TESTING WEB SERVICE IMPLEMENTED WITH CXF#######");
        await testApi('cxf');
    }

    test()

</script>
</body>
</html>
