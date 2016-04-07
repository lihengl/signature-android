var readings = _.fill(Array(180), {
  accelerometer: {x: 0, y: 0, z: 0},
  gyroscope: {x: 0, y: 0, z: 0},
  gravity: {x: 0, y: 0, z: 0},
  rotation: {s: 0, x: 0, y: 0, z: 0}
});


var parseReading = function (messageSegments) {
  var value = {}, values = [];
  try { values = JSON.parse(messageSegments[1]); }
  catch (e) { return null; }
  value = {x: values[0], y: values[1], z: values[2]};
  switch (messageSegments[0]) {
    case 'ACL': return {accelerometer: value};
    case 'GRS': return {gyroscope: value};
    case 'GVT': return {gravity: value};
    case 'ROT': return {rotation: _.assign(value, {s: values[3]})};
    default:
      console.error('Unexpected message segments: ', messageSegments);
      return null;
  }
};


io.connect('http://localhost:3000').on('update', function (message) {
  var reading = parseReading(message.split(': '));
  if (_.isNull(reading)) { return; }
  readings.shift();
  readings.push(_.defaults(reading, _.last(readings)));
});
