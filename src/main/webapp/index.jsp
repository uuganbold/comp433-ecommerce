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
        logTemplate = '<div class="log"><div class="level"></div><div class="message"></div></div>';

        constructor() {
            this.panel = document.getElementById("log")
        }

        error = function (message) {
            console.error(message);
            const log = this.htmlToElement(this.logTemplate);
            const levelDiv = log.querySelector(".level");
            levelDiv.classList.add("error");
            levelDiv.innerHTML = "ERROR";
            const messageDiv = log.querySelector(".message");
            messageDiv.innerHTML = message;
            this.panel.appendChild(log);

        };
        log = function (message) {
            console.log(message);
            const log = this.htmlToElement(this.logTemplate);
            const levelDiv = log.querySelector(".level");
            levelDiv.innerHTML = "LOG";
            const messageDiv = log.querySelector(".message");
            messageDiv.innerHTML = message;
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

        logger.log("Starting to test categories");

        logger.log("Calling: /" + baseURL + "/categories");
        response = await fetch(baseURL + '/categories', {headers: HEADER, method: 'GET'});
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json));
            logger.log("Category number:" + json.length)
            Array.prototype.push.apply(categories, json);
            logger.log("Category number local:" + categories.length);
        } else {
            logger.error(JSON.stringify(await response.json()))
        }

        logger.log("Adding categories /" + baseURL + "/categories");
        response = await fetch(baseURL + '/categories', {
            headers: HEADER,
            method: 'POST',
            body: JSON.stringify({"name": "New Category" + Math.random()})
        });
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json));
            logger.log("New category id:" + json.id);
            categories.push(json);
            logger.log("Category number local:" + categories.length);
        } else {
            logger.error(JSON.stringify(await response.json()))
        }

        temp = json.id;
        logger.log("getting category /" + baseURL + "/category/" + temp);
        response = await fetch(baseURL + '/category/' + temp, {headers: HEADER, method: 'GET'});
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json));
            logger.log("Category id:" + json.id);
            if (temp != json.id) {
                logger.error("Cannot match category id");
            }
            logger.log("Category number local:" + categories.length);
        } else {
            logger.error(JSON.stringify(await response.json()))
        }

        temp = json.id;
        logger.log("updating category /" + baseURL + "/category/" + temp);
        response = await fetch(baseURL + '/category/' + temp, {
            headers: HEADER,
            method: 'PUT',
            body: JSON.stringify({"name": "New Category" + Math.random()})
        });
        if (response.ok) {
            json = await response.json();
            logger.log(JSON.stringify(json));
            logger.log("Category id:" + json.id);
            if (temp != json.id) {
                logger.error("Cannot match category id");
            }
            categories.splice(categories.length - 1);
            categories.push(json);
            logger.log("Category number local:" + categories.length);
        } else {
            logger.error(JSON.stringify(await response.json()))
        }

        let i = categories.length;
        while (i--) {
            let c = categories[i];
            console.log(c);
            if (c.name.startsWith('New Category')) {
                temp = c.id;
                logger.log("deleting category /" + baseURL + "/category/" + temp);
                response = await fetch(baseURL + '/category/' + temp, {headers: HEADER, method: 'DELETE'});
                if (response.ok) {
                    categories.splice(i, 1);
                } else {
                    logger.error(JSON.stringify(await response.json()))
                }
                logger.log("Category number local:" + categories.length);
            }
        }


        /**
         * Testing review web services
         */
    }

    async function test() {
        logger.log("#######TESTING WEB SERVICE IMPLEMENTED WITH SPRING#######")
        await testApi('app');
        logger.log("#######TESTING WEB SERVICE IMPLEMENTED WITH CXF#######")
        await testApi('cxf');
    }

    test()

</script>
</body>
</html>
