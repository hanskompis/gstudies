App.Views.mainView = Backbone.View.extend({
   render : function(contents) {
       var content = Mustache.to_html($("#buttonPictureTemplate").html(),{});
       $(this.el).html(content);
       var paper = Raphael("mainContainer", 500, 500);
       paper.rect(1,1,490,490,10);
       paper.add(contents);       
   },
   
   events: {
       "click #visualizeButton" : "visualizeAction"
   },
   
   visualizeAction: function() {
       var self = this;
       alert("vitttuuuu");
   }
});
