module.exports.createCustomer = async (client, firstName, lastName, birthDate, gender, phoneNumber, email, password, inscriptionDate, isManager, isInstructor, language) => {
    return await client.query(`
        INSERT INTO customer(first_name, last_name, birth_date, gender, phone_number, email, password, inscription_date, is_manager, is_instructor, language)
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

module.exports.getCustomer = async (client, email) => {
    return await client.query(`
        SELECT * FROM customer WHERE email = $1 AND is_manager = 0 LIMIT 1;`, [email]);
}

module.exports.updateCustomer = async (client, id, firstName, lastName, birthDate, gender, phoneNumber, email, password, inscriptionDate, isManager, isInstructor, language) => {
   return await client.query(`
        UPDATE customer SET first_name = $1, last_name = $2, birth_date = $3, gender = $4, phone_number = $5, email = $6, password = $7, inscription_date = $8, is_manager = $9, is_instructor = $10, language = $11
        WHERE id = $12
   `, [firstName, lastName, birthDate, gender, phoneNumber, email, password, inscriptionDate, isManager, isInstructor, language, id]);
}