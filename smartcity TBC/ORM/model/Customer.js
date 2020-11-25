const {DataTypes} = require('sequelize');
const sequelize = require('../sequelize');

const Customer = sequelize.define('customer', {
    id: {
        type: DataTypes.INTEGER,
        autoIncrementIdentity: true,
        primaryKey: true
    },
    firstName: {
        type: DataTypes.STRING
    },
    lastName: {
        type: DataTypes.STRING
    },
    birthDate: {
        type: DataTypes.DATE
    },
    gender: {
        type: DataTypes.INTEGER
    },
    phoneNumber: {
        type: DataTypes.STRING
    },
    email: {
        type: DataTypes.STRING
    },
    password: {
        type: DataTypes.STRING
    },
    inscriptionDate: {
        type: DataTypes.DATE
    },
    isManager: {
        type: DataTypes.INTEGER
    },
    isInstructor: {
        type: DataTypes.INTEGER
    },
    language: {
        type: DataTypes.STRING
    }
},{
    timestamps: false,
    freezeTableName: true
});

module.exports = Customer;

