const CourseControleur = require("../controleur/course");
const router = require("express").Router();

router.get('/:id', CourseControleur.getCourse);
router.post('/', CourseControleur.postCourse);
router.patch('/', CourseControleur.updateStartingDateTime);
router.patch('/', CourseControleur.updateEndingDateTime);
router.patch('/', CourseControleur.updateLevel);
router.patch('/', CourseControleur.updateActivity);
router.patch('/', CourseControleur.updateRoom);
router.delete('/', CourseControleur.deleteCourse);

module.exports = router;
