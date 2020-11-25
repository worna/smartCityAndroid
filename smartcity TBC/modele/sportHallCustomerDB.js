module.exports.insertSportHallCustomer = async (client, id_sportHall, id_customer) => {
    return await client.query(`
        INSERT INTO sportHallCustomer(id_sportHall, id_customer) VALUES
        ($1, $2)`, [id_sportHall, id_customer]
    );
}

