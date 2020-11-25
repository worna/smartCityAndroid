module.exports.createClient = async (client, firstName, lastName, birthDate, gender, phoneNumber, email, password, inscriptionDate, isManager, isInstructor, language) => {
    return await client.query(`
        INSERT INTO client(firstName, lastName, birthDate, gender, phoneNumber, email, password, inscriptionDate, isManager, isInstructor, language)
        VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11)`, [firstName, lastName, birthDate, gender, phoneNumber, email, password, inscriptionDate, isManager, isInstructor, language]
    );
}
module.exports.customerExist = async (client, idCustomer) => {
    const {rows} = await client.query(
        "SELECT count(id) AS nbr FROM customer WHERE id = $1",
        [idCustomer]
    );
    return rows[0].nbr > 0;
}
