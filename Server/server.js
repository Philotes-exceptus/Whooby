const express = require('express')
const app = express()
const mongoose = require('mongoose');
const routes = require('./_routes');
require("dotenv").config();
app.use(express.json())

//connect to mongodb
const dbURI = process.env.dbURI

async function main() {
await mongoose.connect(dbURI, (err, db) => {
    if (err)
        console.log("error occured while connection")
    else {
        console.log('connected to db')
        //listen for request
        app.listen(3000)

        run();
    }
})
}

main()

function run() {

    //routes
    app.use(routes);
}