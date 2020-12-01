const pool = require('../modele/database');
const CustomerDB = require('../modele/customerDB');


module.exports.updateCustomer = async (req, res) => {
    if(req.session){
        //const clientObj = req.session;
        const toUpdate = req.body;
        const newData = {};
        let doUpdate = false;

        if(
            toUpdate.lastname !== undefined ||
            toUpdate.firstname !== undefined ||
            toUpdate.gender !== undefined ||
            toUpdate.birthdate !== undefined ||
            toUpdate.phonenumber !== undefined ||
            toUpdate.email !== undefined ||
            toUpdate.password !== undefined ||
            toUpdate.inscriptiondate !== undefined ||
            toUpdate.ismanager !== undefined ||
            toUpdate.isinstructor !== undefined ||
            toUpdate.language !== undefined
        ){
            doUpdate = true;
        }

        if(doUpdate){
            newData.lastname = toUpdate.lastname;
            newData.firstname = toUpdate.firstname;
            newData.gender = toUpdate.gender;
            newData.birthdate = toUpdate.birthdate;
            newData.phonenumber = toUpdate.phonenumber;
            newData.email = toUpdate.email;
            newData.password = toUpdate.password;
            newData.inscriptiondate = toUpdate.inscriptiondate;
            newData.ismanager = toUpdate.ismanager;
            newData.isinstructor = toUpdate.isinstructor;
            newData.language = toUpdate.language;

            const client = await pool.connect();
            try{
                await CustomerDB.updateCustomer(
                    client,
                    req.body.id,
                    newData.firstname,
                    newData.lastname,
                    newData.birthdate,
                    newData.gender,
                    newData.phonenumber,
                    newData.email,
                    newData.password,
                    newData.inscriptiondate,
                    newData.ismanager,
                    newData.isinstructor,
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
            res.sendStatus(400);
        }

    } else {
        res.sendStatus(401);
    }
};

module.exports.inscriptionCustomer = async (req, res) => {
    const lastname = req.body.lastname;
    const firstname = req.body.firstname;
    const birthdate = req.body.birthdate;
    const gender = req.body.gender;
    const phonenumber= req.body.phonenumber;
    const email = req.body.email;
    const password = req.body.password;
    const inscriptiondate = req.body.inscriptiondate;
    const ismanager = req.body.ismanager;
    const isinstructor = req.body.isinstructor;
    const language = req.body.language;

    if(lastname === undefined || firstname === undefined || birthdate === undefined || gender === undefined || phonenumber === undefined || email === undefined || password === undefined || inscriptiondate == undefined || ismanager === undefined || isinstructor === undefined || language === undefined){
        res.sendStatus(400);
    } else {
        const client = await pool.connect();
        try {
            await CustomerDB.createCustomer(client, lastname, firstname, birthdate, gender, phonenumber, email, password, inscriptiondate, ismanager, isinstructor, language);
            res.sendStatus(201);
        } catch (e) {
            console.log(e);
            res.sendStatus(500);
        } finally {
            client.release();
        }
    }
};
