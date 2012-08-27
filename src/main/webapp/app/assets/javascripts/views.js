App.Views.mainView = Backbone.View.extend({
   render : function(contents) {
       var content = Mustache.to_html($("#buttonPictureTemplate").html(),{});
       $(this.el).html(content);
       var paper = Raphael("mainContainer", 1000, 600);
       paper.rect(1,1,990,590,10);
       //paper.path("M30,30L90,90");
       paper.add(contents);
   },
   
   events: {
       "click #visualizeButton" : "visualizeAction"
   },
   
   visualizeAction: function() {
       var self = this;
       //alert("vitttuuuu");
       $.getJSON("../visualize",function(data){
           self.render(data);
       })
   }
});
