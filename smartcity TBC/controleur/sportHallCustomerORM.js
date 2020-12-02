const CustomerORM = require("../ORM/model/Customer");
const SportHallCustomerORM = require("../ORM/model/SportHallCustomer");
const SportHallORM = require("../ORM/model/SportHall");
const sequelize = require("../ORM/sequelize");
const {Sequelize} = require("sequelize");
/**
 * @swagger
 * components:
 *  responses:
 *      AddedCustomerToSportHall:
 *          description: The customer has been added to the sport hall
 *      SportHallDoesNotExist:
 *          description: The sport hall doesn't exist
 *          content:
 *              application/json:
 *                  schema:
 *                      type: object
 *                      properties:
 *                          error:
 *                              type: string
 *      CustomerDoesNotExist:
 *          description: The customer doesn't exist
 *          content:
 *              application/json:
 *                  schema:
 *                      type: object
 *                      properties:
 *                          error:
 *                              type: string
 *
 *  requestBodies:
 *      AddCustomerToSportHall:
 *          content:
 *              application/json:
 *                  schema:
 *                      type: object
 *                      properties:
 *                          sportHall:
 *                              type: object
 *                              properties:
 *                                  id:
 *                                      type: integer
 *                              required:
 *                                  - id
 *                          customer:
 *                              type: object
 *                              properties:
 *                                  id:
 *                                      type: integer
 *                              required:
 *                                  - id
 *                      required:
 *                          - sportHall
 *                          - customer
 */
module.exports.insertSportHallCustomer = async (req, res) => {
    const {sportHall, customer} = req.body;
    try{
        await sequelize.transaction( {
            deferrable:  Sequelize.Deferrable.SET_DEFERRED
        }, async (t) => {

            const sportHallDB = await SportHallORM.findOne({where: {id: sportHall}});
            if(sportHallDB === null){
                throw new Error("Sport hall id not valid");
            }
            const customerDB = await CustomerORM.findOne({where: {id: customer}});
            if(customerDB === null){
                throw new Error("Customer id not valid");
            }
            await SportHallCustomerORM.create({
                id_sport_hall: sportHall,
                id_customer: customer
            }, {transaction: t});
        });

        res.sendStatus(201);

    } catch (e){
        if(e.message === "Sport hall id not valid"){
            res.status(404).json({error: "The sport hall id is not valid"});
        }else if(e.message === "Customer id not valid"){
            res.status(404).json({error: "The customer id is not valid"});
        } else{
            console.log(e);
            res.sendStatus(500);
        }
    }
}
