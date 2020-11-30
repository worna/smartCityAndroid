const pool = require('../modele/database');
const CustomerDB = require('../modele/customerDB');

module.exports.updateCustomer = async (req, res) => {
    if(req.session){
        const clientObj = req.session;
        const toUpdate = req.body;
        const newData = {};

        newData.firstName = toUpdate.firstName ? toUpdate.firstName : clientObj.firstName;
        newData.lastName = toUpdate.lastName ? toUpdate.lastName : clientObj.lastName;
        newData.birthDate = toUpdate.birthDate ? toUpdate.birthDate : clientObj.birthDate;
        newData.gender = toUpdate.gender ? toUpdate.gender : clientObj.gender;
        newData.phoneNumber = toUpdate.phoneNumber ? toUpdate.phoneNumber : clientObj.phoneNumber;
        newData.email = toUpdate.email ? toUpdate.email : clientObj.email;
        newData.password = toUpdate.password ? toUpdate.password : clientObj.password;
        newData.inscriptionDate = toUpdate.inscriptionDate ? toUpdate.inscriptionDate : clientObj.inscriptionDate;
        newData.isManager = toUpdate.isManager ? toUpdate.isManager : clientObj.isManager;
        newData.isInstructor = toUpdate.isInstructor ? toUpdate.isInstructor : clientObj.isInstructor;
        newData.language = toUpdate.language ? toUpdate.language : clientObj.language;

        const client = await pool.connect();
        try{
            await CustomerDB.updateCustomer(
                client,
                clientObj.id,
                newData.firstName,
                newData.lastName,
                newData.birthDate,
                newData.gender,
                newData.phoneNumber,
                newData.email,
                newData.password,
                newData.inscriptionDate,
                newData.isManager,
                newData.isInstructor,
                newData.language
            );
            res.sendStatus(204);
        }
        catch (e) {
            console.log(e);
            res.sendStatus(500);
        } finally {
            client.release();
        }
    } else {
        res.sendStatus(401);
    }
}
