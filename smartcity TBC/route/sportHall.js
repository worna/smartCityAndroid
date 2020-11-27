const SportHallControleur = require("../controleur/sportHallORM");
const Router = require("express-promise-router");
const router = new Router;


router.get('/:id', SportHallControleur.getSportHall);
router.post('/', SportHallControleur.postSportHall);
router.patch('/', SportHallControleur.updateSportHall);
router.delete('/', SportHallControleur.deleteSportHall);

module.exports = router;
