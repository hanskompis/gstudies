var App = {
  Models: {},
  Collections: {},
  Views: {},
  Routers: {},
  init: function() {
    new App.Routers.Main();
    Backbone.history.start();
  }
};

