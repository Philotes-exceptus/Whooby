const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({

    reg_no : {type : String,
              required : true},
    email: {
        type: String,
        minLength: 10,
        required: true
    },
    password: String

});

const user = mongoose.model('User', userSchema);

module.exports = user