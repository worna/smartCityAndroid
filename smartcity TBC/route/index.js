const SportHallRouter = require('./sportHall');
const CourseRouter = require('./course');
const SportHallCustomerRouter = require('./sportHallCustomer');
const CustomerRouter = require('./customer');

const router = require("express").Router();

router.use("/sportHall", SportHallRouter);
router.use("/course", CourseRouter);
router.use("/sportHallCustomer", SportHallCustomerRouter);
router.use("/customer", CustomerRouter);


module.exports = router;