const User = require('./user')

const login = (req, res) => {

    const query = {
        reg_no: req.body.reg_no,
        email: req.body.email, 
        password: req.body.password
    }

    

    User.findOne(query, (err, result) => {

        if (result != null) {

            const objToSend = {
                reg_no: res.reg_no,
                email: res.email
            }

            res.status(200).send(JSON.stringify(objToSend))

        } else {
            res.status(404).send()
            console.log("error in searching");
        }

    })

}


const signUp = (req, res) => {

    const newUser = {
        reg_no: req.body.reg_no,
        email: req.body.email,
        password: req.body.password
    }

    const query = { reg_no: newUser.reg_no}
    User.findOne(query, (err, result) => {

        if (result == null) {
            User.create(newUser, (err, result) => {
                res.status(200).send()
                console.log("new user created")
            })
        } else {
            res.status(400).send()
            console.log(err)
        }

    })

}



module.exports = {
    login:login,
    signUp:signUp
}