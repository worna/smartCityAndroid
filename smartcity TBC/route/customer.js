const CustomerControleur = require("../controleur/customerDB");
const JWTMiddleWare = require("../middleware/IdentificationJWT");

const Router = require("express-promise-router");
const router = new Router;

router.post('/', CustomerControleur.inscriptionCustomer);
router.patch('/', JWTMiddleWare.identification, CustomerControleur.updateCustomer);

module.exports = router;