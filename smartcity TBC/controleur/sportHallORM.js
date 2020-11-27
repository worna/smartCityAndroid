const SportHallORM = require('../ORM/model/SportHall');
const CustomerORM = require ('../ORM/model/Customer');
const sequelize = require("../ORM/sequelize");
const {Sequelize} = require("sequelize");

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
        res.send(error.toString());
        res.sendStatus(500);
    }
}

module.exports.postSportHall = async (req, res) => {
    const body = req.body;
    const {name, manager, phone_number, email} = body;
    try{
        await sequelize.transaction( {
            deferrable:  Sequelize.Deferrable.SET_DEFERRED
        }, async (t) => {
        const managerDB = await CustomerORM.findOne({where: {id: manager}});
        if(managerDB === null){
            throw new Error("Manager id not valid");
        }
        await SportHallORM.create({
            name,
            manager,
            phone_number,
            email,
        }, {transaction: t});

        });
        res.sendStatus(201);
    } catch (error){
        if(error.message === "Manager id not valid"){
            res.status(404).json({error: "The manager id is not valid"});
        }else{
        res.sendStatus(500);
        }
    }
}

module.exports.updateSportHall = async (req, res) => {
    const {id, name, manager, phone_number, email,} = req.body;
    try{
        await sequelize.transaction( {
            deferrable:  Sequelize.Deferrable.SET_DEFERRED
        }, async (t) => {
        const managerDB = await CustomerORM.findOne({where: {id: manager}});
        if(managerDB === null){
            throw new Error("Manager id not valid");
        }
        await SportHallORM.update({ name, manager, phone_number, email}, {where: {id}}, {transaction: t});
        });
        res.sendStatus(204);
    } catch (error){
        if(error.message === "Manager id not valid"){
            res.status(404).json({error: "The manager id is not valid"});
        }else{
        res.sendStatus(500);
        }
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

