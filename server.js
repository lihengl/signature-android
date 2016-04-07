var express = require('express');
var http = require('http');
var socket = require('socket.io');

var server = http.Server(express());
var io = socket(server);

var Writable = require('stream').Writable;
var BrowserStream = new Writable();


server.listen(3000);


BrowserStream._write = function (chunk, enc, next) {
  io.sockets.emit('sense', chunk.toString('utf8'));
  next();
};

process.stdin.pipe(BrowserStream);
process.stdin.pipe(process.stdout);
