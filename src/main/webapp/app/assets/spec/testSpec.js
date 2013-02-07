var testDS = [{
    label : "1", 
    data : [[0,6],[10,2],[20,5]]
},
{
    label : "2", 
    data : [[0,8],[10,7],[20,6],[30,5]]
},
{
    label : "3", 
    data : [[0,10],[10,9],[20,8],[30,1],[40,2]]
}];

var testDSNorm = [{
    label : "1", 
    data : [[0,14],[10,5],[20,12]]
},
{
    label : "2", 
    data : [[0,9],[10,8],[20,7],[30,6]]
},
{
    label : "3", 
    data : [[0,10],[10,9],[20,8],[30,1],[40,2]]
}];

var cumulativeTestDS = [{
    label : "1", 
    data : [[0,0],[1,1],[2,4]]
},
{
    label : "2", 
    data : [[0,1],[1,1],[2,1],[3,4],[4,5]]
},
{
    label : "3", 
    data : [[0,0],[1,0],[2,2],[3,6]]
}   
];

var cumulativeNormPerseDS = [{
    label :  "1",
    data : [[0,0],[1,25],[2,100],[3,100],[4,100]]
},
{
    label : "2",
    data : [[0,20],[1,20],[2,20],[3,80],[4,100]]
},{
    label : "3",
    data : [[0,0],[1,0],[2,33],[3,100],[4,100]]
}, {
    label : "average",
    data : [[0,6.66],[1,15],[2,51.11],[3,93.33],[4,100]]
}
];

var sameArray = function(arr1, arr2){
    if(arr1.length !== arr2.length) {
        return false;
    }
    for(var i = 0; i < arr1.length ; i++){
        //        if(arr1[i][0] !== arr2[i][0] && arr1[i][1] !== arr2[i][1]){
        if (this.differentValue(arr1[i][0],arr2[i][0]) || this.differentValue(arr1[i][1],arr2[i][1])){
            return false;
        }
    }
    return true;
}

var differentValue = function (num1, num2) {
    var epsilon = 0.01;
    return (Math.abs(num1-num2))>epsilon;
}

var sameDataSeries = function(dataSeries1, dataSeries2) {
    //    console.log(json.Stringify(dataSeries1));
    //    console.log(json.Stringify(dataSeries2));

    for(var i = 0; i < dataSeries1.length ; i++){
        if(!sameArray(dataSeries1[i],dataSeries2[i])){
            return false;
        }
    }
    return true;
}

describe("responseObject", function() {
    it("is available", function() {
        expect(testResponse).not.toBeNull();
    });
    it("has correct course number", function() {
        expect(testResponse[0].courseId).toEqual("582206"); 
    });
});

describe("dataSeriesUtils", function() {
    it("finds largest amount of students", function() {
        expect(dataSeriesUtils.findLargestAmountOfStudents(testDS)).toBe(30); 
    });
    it("normalizes data series", function() {
        expect(sameDataSeries(testDSNorm,dataSeriesUtils.normalizeDataSeries(testDS))).toBe(true); 
    });
    it("percentage-normalizes data series", function() {
        expect(sameDataSeries(dataSeriesUtils.normalizePercentageCumulativeDataSeries(cumulativeTestDS),cumulativeNormPerseDS)).toBe(true); 
    });
    it("finds largest value of  single data series ", function() {
        expect(dataSeriesUtils.findLargestValueOfSet(cumulativeTestDS)).toBe(6); 
    });
    it("finds largest category of  single data series ", function() {
        expect(dataSeriesUtils.findLargestCategoryOfSet(cumulativeTestDS)).toBe(4); 
    });
});
