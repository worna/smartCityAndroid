const SportHallControleur = require("../controleur/sportHallDB");
const Router = require("express-promise-router");
const router = new Router;


router.get('/:id', SportHallControleur.getSportHall);
router.post('/', SportHallControleur.postSportHall);
router.patch('/', SportHallControleur.updateName);
router.patch('/', SportHallControleur.updateManager);
router.patch('/', SportHallControleur.updatePhoneNumber);
router.patch('/', SportHallControleur.updateEmail);
router.delete('/', SportHallControleur.deleteSportHall);

module.exports = router;
