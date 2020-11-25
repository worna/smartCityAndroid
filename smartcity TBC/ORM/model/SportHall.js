const {DataTypes} = require('sequelize');
const sequelize = require('../sequelize');

const SportHall = sequelize.define('sportHall', {
    id: {
        type: DataTypes.INTEGER,
        autoIncrementIdentity: true,
        primaryKey: true,
    },
    name: {
        type: DataTypes.STRING
    },
    manager: {
        type: DataTypes.INTEGER,
        references: {
            model: Customer,
            key: 'id',
            deferrable: Deferrable.INITIALLY_IMMEDIATE
        },
    },
    phoneNumber:{
        type: DataTypes.STRING
    }
    email:{
        type: DataTypes.STRING
    }
}, {
    timestamps: false,
    freezeTableName: true
});

module.exports = SportHall;
