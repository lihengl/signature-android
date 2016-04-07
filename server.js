var express = require('express');
var http = require('http');
var socket = require('socket.io');
var _ = require('lodash');


var server = http.Server(express());
var io = socket(server);

var Writable = require('stream').Writable;
var BrowserStream = new Writable();


server.listen(3000);


BrowserStream._write = function (chunk, enc, next) {
  var logMessage = chunk.toString('utf8');
  var sensorKeyword = 'SENSOR-';
  if (!_.includes(logMessage, sensorKeyword)) { return next(); }
  io.sockets.emit('update', logMessage.split(sensorKeyword)[1]);
  next();
};


process.stdin.pipe(BrowserStream);
process.stdin.pipe(process.stdout);
