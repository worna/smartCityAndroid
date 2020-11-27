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

module.exports.updateSportHall = async (req, res) => {
    const {id, name, manager, phoneNumber, email} = req.body;
    const client = await pool.connect();
    try{
        await SportHallModele.updateSportHall(id, name, manager, phoneNumber, email, client);
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
