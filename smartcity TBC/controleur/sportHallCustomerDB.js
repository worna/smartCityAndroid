const pool = require('../modele/database');
const CustomerModele = require("../modele/customerDB");
const SportHallCustomerModele = require("../modele/sportHallCustomerDB");

module.exports.insertSportHallCustomer = async (req, res) => {
    const client = await pool.connect();
    const {idSportHall, client:clientObj} = req.body;
    try {
        await client.query("BEGIN;");
        const customerExist = await CustomerModele.customerExist(client, clientObj.id);
        if (customerExist === true) {
            await SportHallCustomerModele.insertSportHallCustomer(
                client,
                idSportHall,
                clientObj.id,
            );
            await client.query("COMMIT");
            res.sendStatus(201);
        } else {
            await client.query("ROLLBACK");
            res.status(404).json({error: "customer id doesn't exist"});
        }
    } catch (e) {
        await client.query("ROLLBACK;");
        console.log(e);
        res.sendStatus(500);
    } finally {
        client.release();
    }
}