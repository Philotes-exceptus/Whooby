const express = require('express');

const router = express.Router();
const controller = require('./controller')

router.post('/signup',controller.signUp);

router.post('/login',controller.login);

module.exports = router