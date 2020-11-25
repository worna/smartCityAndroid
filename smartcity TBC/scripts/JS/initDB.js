const pool = require("../../modele/database");
const fs = require("fs");
const path = require("path");

async function initDB(){
    const sportHall = await pool.connect();
    try{
        const query = fs.readFileSync(path.join(__dirname, "../SQL/createDB.SQL"), "utf-8");
        await sportHall.query(query);
    } catch (e) {
        console.log(e);
    } finally {
        sportHall.release();
        await pool.end();
    }
}

initDB().then(() => console.log("done"));