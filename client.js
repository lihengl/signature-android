io.connect('http://localhost:3000').on('sense', function (data) {
  console.warn(data);
});

window.setInterval(function () {
  console.clear();
}, 3000);
