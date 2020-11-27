const CourserModele = require("../modele/courseDB");
const SportHallModele = require("../modele/sportHallDB");
const CustomerModele = require("../modele/customerDB");
const pool = require("../modele/database");

module.exports.getCourse = async (req, res) => {
    const client = await pool.connect();
    const idTexte = req.params.id; //attention ! Il s'agit de texte !
    const id = parseInt(idTexte);
    try{
        if(isNaN(id)){
            res.sendStatus(400);
        } else {
            const {rows: courses} = await CourserModele.getCourse(id, client);
            const course = courses[0];
            if(course !== undefined){
                res.json(course);
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
module.exports.postCourse = async (req, res) => {
    const client = await pool.connect();
    const body = req.body;
    const {sportHall, startingDateTime, endingDateTime, level, activity, room, instructor} = body;
    try{
        await client.query("BEGIN;");
        const sportHallExist = await SportHallModele.sportHallExist(client, sportHall);
        if(sportHallExist) {
            const instructorExist = await CustomerModele.customerExist(client, instructor);
            if (instructorExist) {
                await CourserModele.postCourse(sportHall, startingDateTime, endingDateTime, level, activity, room, instructor, client);
                await client.query("COMMIT");
                res.sendStatus(201);
            }else {
                await client.query("ROLLBACK");
                res.status(404).json({error: "The instructor id is not valid"});
            }
        } else {
            await client.query("ROLLBACK");
            res.status(404).json({error: "The sport hall id is not valid"});
        }
    } catch (error){
        res.sendStatus(500);
    } finally {
        client.release();
    }
}
module.exports.updateCourse = async (req, res) => {
    const {id, sportHall, startingDateTime, endingDateTime, level, activity, room, instructor} = req.body;
    const client = await pool.connect();
    try{
        await client.query("BEGIN;");
        const sportHallExist = await SportHallModele.sportHallExist(client, sportHall);
        if(sportHallExist) {
            const instructorExist = await CustomerModele.customerExist(client, instructor);
            if (instructorExist) {
                await CourserModele.updateCourse(id, sportHall, startingDateTime, endingDateTime, level, activity, room, instructor, client);
                await client.query("COMMIT");
                res.sendStatus(204);
            }else {
                await client.query("ROLLBACK");
                res.status(404).json({error: "The instructor id is not valid"});
            }
        } else {
            await client.query("ROLLBACK");
            res.status(404).json({error: "The sport hall id is not valid"});
        }
    }catch (error){
        res.send(error.toString());
        res.sendStatus(500);
    } finally {
        client.release();
    }
}

module.exports.deleteCourse= async (req, res) => {
    const client = await pool.connect();
    const {id} = req.body;
    try{
        await CourserModele.deleteCourse(id, client);
        res.sendStatus(204);
    }catch (error){
        res.sendStatus(500);
    } finally {
        client.release();
    }
}
