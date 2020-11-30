module.exports.getManager = async (client, email, password) => {
    return await client.query(`
        SELECT * FROM customer WHERE email = $1 AND password = $2 AND is_manager = 1 LIMIT 1;
    `, [email, password]);
}
