### Setup su Eclipse
Installare Eclipse Enterprise Edition,
aggiungere JBoss Tools dall'Eclipse Marketplace (help -> marketplace),
importare il progetto su Eclipse,
premere il testo run e fare il setup con Wildfly ultima versione

### Esempio

```js
const response = await fetch("http://95.216.204.15/dilaxia-api/auth/login", {
	method:"POST",
	headers: {
		"Content-Type": "application/json",
	},
	body: JSON.stringify({
		username:"admin",
		password:"admin"
	}),
});
console.log(await response.json());

```
