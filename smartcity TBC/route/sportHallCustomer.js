const SportHallCustomerControleur = require("../controleur/sportHallCustomerDB");

const Router = require("express-promise-router");
const router = new Router;

router.post('/', SportHallCustomerControleur.insertSportHallCustomer);

module.exports = router;
