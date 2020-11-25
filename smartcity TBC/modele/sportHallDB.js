module.exports.getSportHall = async (id, client) => {
    return await client.query("SELECT * FROM sportHall WHERE id = $1", [id]);
}
module.exports.postSportHall = async (name, manager, phoneNumber, email, client) => {
    return await client.query("INSERT INTO sportHall (name, manager, phoneNumber, email) VALUES ($1, $2, $3, $4)", [name, manager, phoneNumber, email]);
}

module.exports.updatePhoneNumber = async (id, name, client) => {
    return await client.query("UPDATE sportHall SET name = $1 WHERE id=$2", [name, id]);
}

module.exports.updatePhoneNumber = async (id, manager, client) => {
    return await client.query("UPDATE sportHall SET manager = $1 WHERE id=$2", [manager, id]);
}

module.exports.updatePhoneNumber = async (id, phoneNumber, client) => {
    return await client.query("UPDATE sportHall SET phoneNumber = $1 WHERE id=$2", [phoneNumber, id]);
}

module.exports.updatePhoneNumber = async (id, email, client) => {
    return await client.query("UPDATE sportHall SET email = $1 WHERE id=$2", [email, id]);
}

module.exports.deleteSportHall = async (id, client) => {
    return await client.query("DELETE FROM sportHall WHERE id=$1", [id]);
}
