const User = require('./user')
const path = require('path')
var nodemailer = require('nodemailer');
var hbs = require('nodemailer-express-handlebars');

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


            var transporter = nodemailer.createTransport({
                service: 'gmail',
                auth: {
                  user: 'soumyasagar135@gmail.com',
                  pass: 'sy_sagar123'
                }
              });
              
              const handlebarOptions = {
                viewEngine: {
                  extName: ".handlebars",
                  partialsDir: path.resolve('./views'),
                  defaultLayout: false,
                },
                viewPath: path.resolve('./views'),
                extName: ".handlebars",
              }
              
              transporter.use('compile', hbs(handlebarOptions));
              
              var mailOptions = {
                from: 'soumyasagar135@gmail.com',
                to: "sysagar07@gmail.com",
                subject: 'Sending Email using Node.js',
                template: 'email'

              };
              
              transporter.sendMail(mailOptions, function (error, info) {
                if (error) {
                  console.log(error);
                } else {
                  console.log('Email sent: ' + info.response);
                }
              });

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

    const query = { reg_no: newUser.reg_no }
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
    login: login,
    signUp: signUp
}