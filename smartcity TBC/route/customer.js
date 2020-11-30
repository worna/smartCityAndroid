const CustomerControleur = require("../controleur/customerDB");
const AuthMiddleware = require("../middleware/Identification.js");

const Router = require("express-promise-router");
const router = new Router;

router.patch('/', AuthMiddleware.identificationWithAuth, CustomerControleur.updateCustomer);

module.exports = router;