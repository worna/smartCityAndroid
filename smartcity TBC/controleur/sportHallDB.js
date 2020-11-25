const SportHallModele = require("../modele/sportHallDB");
const pool = require("../modele/database");

module.exports.getSportHall = async (req, res) => {
    const client = await pool.connect();
    const idTexte = req.params.id; //attention ! Il s'agit de texte !
    const id = parseInt(idTexte);
    try{
        if(isNaN(id)){
            res.sendStatus(400);
        } else {
            const {rows: sportHalls} = await SportHallModele.getSportHall(id, client);
            const sportHall = sportHalls[0];
            if(sportHall !== undefined){
                res.json(sportHall);
            } else {
                res.sendStatus(404);
            }
        }
    } catch (error){
        res.sendStatus(500);
    } finally {
        client.release();
    }
}
module.exports.postSportHall = async (req, res) => {
    const client = await pool.connect();
    const body = req.body;
    const {name, manager, phoneNumber, email} = body;
    try{
        await SportHallModele.postSportHall(name, manager, phoneNumber, email, client);
        res.sendStatus(201);
    } catch (error){
        res.sendStatus(500);
    } finally {
        client.release();
    }
}

module.exports.updateName = async (req, res) => {
    const client = await pool.connect();
    const {id, name} = req.body;
    try{
        await SportHallModele.updateName(id, name, client);
        res.sendStatus(204);
    }catch (error){
        res.sendStatus(500);
    } finally {
        client.release();
    }
}

module.exports.updateManager = async (req, res) => {
    const client = await pool.connect();
    const {id, manager} = req.body;
    try{
        await SportHallModele.updateManager(id, manager, client);
        res.sendStatus(204);
    }catch (error){
        res.sendStatus(500);
    } finally {
        client.release();
    }
}

module.exports.updatePhoneNumber = async (req, res) => {
    const client = await pool.connect();
    const {id, phoneNumber} = req.body;
    try{
        await SportHallModele.updatePhoneNumber(id, phoneNumber, client);
        res.sendStatus(204);
    }catch (error){
        res.sendStatus(500);
    } finally {
        client.release();
    }
}

module.exports.updateEmail = async (req, res) => {
    const client = await pool.connect();
    const {id, email} = req.body;
    try{
        await SportHallModele.updateEmail(id, email, client);
        res.sendStatus(204);
    }catch (error){
        res.sendStatus(500);
    } finally {
        client.release();
    }
}

module.exports.deleteSportHall= async (req, res) => {
    const client = await pool.connect();
    const {id} = req.body;
    try{
        await SportHallModele.deleteSportHall(id, client);
        res.sendStatus(204);
    }catch (error){
        res.sendStatus(500);
    } finally {
        client.release();
    }
}
