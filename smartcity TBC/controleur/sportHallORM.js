const SportHallORM = require('../ORM/model/SportHallORM');

module.exports.getSportHall = async (req, res) => {
    const idTexte = req.params.id;
    const id = parseInt(idTexte);
    try{
        if(isNaN(id)){
            res.sendStatus(400);
        } else {
            const sportHall = await SportHallORM.findOne({where: {id: id}});
            if(sportHall !== null){
                res.json(sportHall);
            } else {
                res.sendStatus(404);
            }
        }
    } catch (error){
        res.sendStatus(500);
    }
}

module.exports.postSportHall = async (req, res) => {
    const body = req.body;
    const {name, manager, phoneNumber, email} = body;
    try{
        await SportHallORM.create({
            name,
            manager,
            phoneNumber,
            email
        });
        res.sendStatus(201);
    } catch (error){
        console.log(error);
        res.sendStatus(500);
    }
}

module.exports.updateName = async (req, res) => {
    const {id, name} = req.body;
    try{
        await SportHallORM.update({name}, {where: {id}});
        res.sendStatus(204);
    } catch (error){
        res.sendStatus(500);
    }
}

module.exports.updateManager = async (req, res) => {
    const {id, manager} = req.body;
    try{
        await SportHallORM.update({manager}, {where: {id}});
        res.sendStatus(204);
    } catch (error){
        res.sendStatus(500);
    }
}

module.exports.updatePhoneNumber = async (req, res) => {
    const {id, phoneNumber} = req.body;
    try{
        await SportHallORM.update({phoneNumber}, {where: {id}});
        res.sendStatus(204);
    } catch (error){
        res.sendStatus(500);
    }
}

module.exports.updateEmail = async (req, res) => {
    const {id, email} = req.body;
    try{
        await SportHallORM.update({email}, {where: {id}});
        res.sendStatus(204);
    } catch (error){
        res.sendStatus(500);
    }
}

module.exports.deleteSportHall = async (req, res) => {
    const {id} = req.body;
    try{
        await SportHallORM.destroy({where: {id}});
        res.sendStatus(204);
    } catch (error){
        res.sendStatus(500);
    }
}

