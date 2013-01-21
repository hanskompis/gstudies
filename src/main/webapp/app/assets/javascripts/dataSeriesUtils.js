var dataSeriesUtils = {
    response : null,
    PASSED : 0, 
    FAILED : 1, 
    ALL : 2,

    setDataSeries : function () {
        this.DSCreditGains7MonthsPass = this.getDataSeries(7,this.PASSED);
        this.DSCreditGains13MonthsPass = this.getDataSeries(13,this.PASSED);
        this.DSCreditGains19MonthsPass = this.getDataSeries(19,this.PASSED);
        this.DSCreditGains7MonthsFail = this.getDataSeries(7,this.FAILED);
        this.DSCreditGains13MonthsFail = this.getDataSeries(13,this.FAILED);
        this.DSCreditGains19MonthsFail = this.getDataSeries(19,this.FAILED);
        this.DSCreditGains7MonthsAll = this.getDataSeries(7,this.ALL);
        this.DSCreditGains13MonthsAll = this.getDataSeries(13,this.ALL);
        this.DSCreditGains19MonthsAll = this.getDataSeries(19,this.ALL);
        
        this.DSCreditGains7MonthsNormPass = this.normalizeDataSeries(this.DSCreditGains7MonthsPass);
        this.DSCreditGains13MonthsNormPass = this.normalizeDataSeries(this.DSCreditGains13MonthsPass);
        this.DSCreditGains19MonthsNormPass = this.normalizeDataSeries(this.DSCreditGains19MonthsPass);
        this.DSCreditGains7MonthsNormFail = this.normalizeDataSeries(this.DSCreditGains7MonthsFail);
        this.DSCreditGains13MonthsNormFail = this.normalizeDataSeries(this.DSCreditGains13MonthsFail);
        this.DSCreditGains19MonthsNormFail = this.normalizeDataSeries(this.DSCreditGains19MonthsFail);
        this.DSCreditGains7MonthsNormAll = this.normalizeDataSeries(this.DSCreditGains7MonthsAll);
        this.DSCreditGains13MonthsNormAll = this.normalizeDataSeries(this.DSCreditGains13MonthsAll);
        this.DSCreditGains19MonthsNormAll = this.normalizeDataSeries(this.DSCreditGains19MonthsAll);
                
        this.DSCreditGains7MonthsNormCumulPass = this.normalizeCumulativeDataSeries(this.getCumulativeDataSeries(7, this.PASSED));
        //                     console.log(JSON.stringify(this.DSCreditGains7MonthsNormCumulPass));
        this.DSCreditGains13MonthsNormCumulPass = this.normalizeCumulativeDataSeries(this.getCumulativeDataSeries(13, this.PASSED));
        this.DSCreditGains19MonthsNormCumulPass = this.normalizeCumulativeDataSeries(this.getCumulativeDataSeries(19, this.PASSED));
        this.DSCreditGains7MonthsNormCumulFail = this.normalizeCumulativeDataSeries(this.getCumulativeDataSeries(7, this.FAILED));
        this.DSCreditGains13MonthsNormCumulFail = this.normalizeCumulativeDataSeries(this.getCumulativeDataSeries(13, this.FAILED));
        this.DSCreditGains19MonthsNormCumulFail = this.normalizeCumulativeDataSeries(this.getCumulativeDataSeries(19, this.FAILED));
        this.DSCreditGains7MonthsNormCumulAll = this.normalizeCumulativeDataSeries(this.getCumulativeDataSeries(7, this.ALL));
        this.DSCreditGains13MonthsNormCumulAll = this.normalizeCumulativeDataSeries(this.getCumulativeDataSeries(13, this.ALL));
        this.DSCreditGains19MonthsNormCumulAll = this.normalizeCumulativeDataSeries(this.getCumulativeDataSeries(19, this.ALL));
        
        this.DSCreditGains7MonthsHistoPass = this.getDataSeriesForDiffHistograms(this.DSCreditGains7MonthsNormCumulPass);
        this.DSCreditGains7MonthsHistoFail = this.getDataSeriesForDiffHistograms(this.DSCreditGains7MonthsNormCumulFail);
        this.DSCreditGains7MonthsHistoAll = this.getDataSeriesForDiffHistograms(this.DSCreditGains7MonthsNormCumulAll);

        
        this.largestValueOfCreditGains7Months = this.findLargestValueOfDataSeries([this.DSCreditGains7MonthsPass,this.DSCreditGains7MonthsFail, this.DSCreditGains7MonthsAll]);
        this.largestValueOfCreditGains13Months = this.findLargestValueOfDataSeries([this.DSCreditGains13MonthsPass,this.DSCreditGains13MonthsFail, this.DSCreditGains13MonthsAll]);
        this.largestValueOfCreditGains19Months = this.findLargestValueOfDataSeries([this.DSCreditGains19MonthsPass,this.DSCreditGains19MonthsFail, this.DSCreditGains19MonthsAll]);
        
        this.largestCategoryOfCreditGains7Months = this.findLargestCategoryOfDataSeries([this.DSCreditGains7MonthsPass,this.DSCreditGains7MonthsFail, this.DSCreditGains7MonthsAll]);
        this.largestCategoryOfCreditGains13Months = this.findLargestCategoryOfDataSeries([this.DSCreditGains13MonthsPass,this.DSCreditGains13MonthsFail, this.DSCreditGains13MonthsAll]);
        this.largestCategoryOfCreditGains19Months = this.findLargestCategoryOfDataSeries([this.DSCreditGains19MonthsPass,this.DSCreditGains19MonthsFail, this.DSCreditGains19MonthsAll]);

        this.largestValueOfCreditGains7MonthsNorm = this.findLargestValueOfDataSeries([this.DSCreditGains7MonthsNormPass,this.DSCreditGains7MonthsNormFail, this.DSCreditGains7MonthsNormAll]);
        this.largestValueOfCreditGains13MonthsNorm = this.findLargestValueOfDataSeries([this.DSCreditGains13MonthsNormPass,this.DSCreditGains13MonthsNormFail, this.DSCreditGains13MonthsNormAll]);
        this.largestValueOfCreditGains19MonthsNorm = this.findLargestValueOfDataSeries([this.DSCreditGains19MonthsNormPass,this.DSCreditGains19MonthsNormFail, this.DSCreditGains19MonthsNormAll]);

        this.largestCategoryOfCreditGains7MonthsNorm = this.findLargestCategoryOfDataSeries([this.DSCreditGains7MonthsNormPass,this.DSCreditGains7MonthsNormFail, this.DSCreditGains7MonthsNormAll]);
        this.largestCategoryOfCreditGains13MonthsNorm = this.findLargestCategoryOfDataSeries([this.DSCreditGains13MonthsNormPass,this.DSCreditGains13MonthsNormFail, this.DSCreditGains13MonthsNormAll]);
        this.largestCategoryOfCreditGains19MonthsNorm = this.findLargestCategoryOfDataSeries([this.DSCreditGains19MonthsNormPass,this.DSCreditGains19MonthsNormFail, this.DSCreditGains19MonthsNormAll]);
   
        this.largestValueOfCreditGains7MonthsNormCumulPass = this.findLargestValueOfDataSeries([this.DSCreditGains7MonthsNormCumulPass]);
        this.largestValueOfCreditGains13MonthsNormCumulPass = this.findLargestValueOfDataSeries([this.DSCreditGains13MonthsNormCumulPass]);
        this.largestValueOfCreditGains19MonthsNormCumulPass = this.findLargestValueOfDataSeries([this.DSCreditGains19MonthsNormCumulPass]);
        
        this.largestValueOfCreditGains7MonthsNormCumulFail = this.findLargestValueOfDataSeries([this.DSCreditGains7MonthsNormCumulFail]);
        this.largestValueOfCreditGains13MonthsNormCumulFail = this.findLargestValueOfDataSeries([this.DSCreditGains13MonthsNormCumulFail]);
        this.largestValueOfCreditGains19MonthsNormCumulFail = this.findLargestValueOfDataSeries([this.DSCreditGains19MonthsNormCumulFail]);
        
        this.largestValueOfCreditGains7MonthsNormCumulAll = this.findLargestValueOfDataSeries([this.DSCreditGains7MonthsNormCumulAll]);
        this.largestValueOfCreditGains13MonthsNormCumulAll = this.findLargestValueOfDataSeries([this.DSCreditGains13MonthsNormCumulAll]);
        this.largestValueOfCreditGains19MonthsNormCumulAll = this.findLargestValueOfDataSeries([this.DSCreditGains19MonthsNormCumulAll]);
        
        this.largestCategoryOfCreditGains7MonthsNormCumulPass = this.findLargestCategoryOfDataSeries([this.DSCreditGains7MonthsNormCumulPass]);
        this.largestCategoryOfCreditGains13MonthsNormCumulPass = this.findLargestCategoryOfDataSeries([this.DSCreditGains13MonthsNormCumulPass]);
        this.largestCategoryOfCreditGains19MonthsNormCumulPass = this.findLargestCategoryOfDataSeries([this.DSCreditGains19MonthsNormCumulPass]);
        
        this.largestCategoryOfCreditGains7MonthsNormCumulFail = this.findLargestCategoryOfDataSeries([this.DSCreditGains7MonthsNormCumulFail]);
        this.largestCategoryOfCreditGains13MonthsNormCumulFail = this.findLargestCategoryOfDataSeries([this.DSCreditGains13MonthsNormCumulFail]);
        this.largestCategoryOfCreditGains19MonthsNormCumulFail = this.findLargestCategoryOfDataSeries([this.DSCreditGains19MonthsNormCumulFail]);
        
        this.largestCategoryOfCreditGains7MonthsNormCumulAll = this.findLargestCategoryOfDataSeries([this.DSCreditGains7MonthsNormCumulAll]);
        this.largestCategoryOfCreditGains13MonthsNormCumulAll = this.findLargestCategoryOfDataSeries([this.DSCreditGains13MonthsNormCumulAll]);
        this.largestCategoryOfCreditGains19MonthsNormCumulAll = this.findLargestCategoryOfDataSeries([this.DSCreditGains19MonthsNormCumulAll]);
        
    //        this.getDataSeriesForDiffHistograms(this.DSCreditGains7MonthsNormCumulPass);
    },
    
    getDataSeries : function (months, group) {
        var dataSeries = [];
        var amountYears = this.response.models.length;
        var ddata;
        for(var i = 0; i < amountYears; i++){
            if(months == 7){
                ddata = this.response.models[i].get("courseStatsObjs")[group].creditGainsSevenMonthsCategorizedArr;
                dataSeries.push({
                    data : ddata,
                    label : this.response.models[i].get("dateOfAccomplishment")
                })
            }
            else if(months == 13){
                ddata = this.response.models[i].get("courseStatsObjs")[group].creditGainsThirteenMonthsCategorizedArr;
                dataSeries.push({
                    data : ddata,
                    label : this.response.models[i].get("dateOfAccomplishment")
                })
            }
            else if(months == 19){
                ddata = this.response.models[i].get("courseStatsObjs")[group].creditGainsNineteenMonthsCategorizedArr;
                dataSeries.push({
                    data : ddata,
                    label : this.response.models[i].get("dateOfAccomplishment")
                })
            }
            else{
                alert("error getDataSeries");
            }
        }
        return dataSeries;
    },
    getCumulativeDataSeries : function (months,group){
        var dataSeries = [];
        var amountYears = this.response.models.length;
        var ddata;
        for(var i = 0; i < amountYears; i++){
            if(months == 7){
                ddata = this.response.models[i].get("courseStatsObjs")[group].creditGainsSevenMonthsNormCumulArr;
                dataSeries.push({
                    data : ddata,
                    label : this.response.models[i].get("dateOfAccomplishment")
                })
            }
            else if(months == 13){
                ddata = this.response.models[i].get("courseStatsObjs")[group].creditGainsThirteenMonthsNormCumulArr;
                dataSeries.push({
                    data : ddata,
                    label : this.response.models[i].get("dateOfAccomplishment")
                })
            }
            else if(months == 19){
                ddata = this.response.models[i].get("courseStatsObjs")[group].creditGainsNineteenMonthsNormCumulArr;
                dataSeries.push({
                    data : ddata,
                    label : this.response.models[i].get("dateOfAccomplishment")
                })
            }
            else{
                alert("error getCumulativeDataSeries");
            }
        }
        return dataSeries;
    },
    
    findLargestValue : function (categorizedArray) {
        var largest = 0;
        for(var i = 0; i < categorizedArray.length; i++){
            var currentValue = categorizedArray[i][1];
            if(currentValue > largest){
                largest = currentValue;
            }
        }
        return largest;
    },
    
    findLargestValueOfSet : function (setOfArrays) {
        var largest = 0;
        for(var i = 0; i < setOfArrays.length; i++){
            var currentValue = this.findLargestValue(setOfArrays[i].data);
            if(currentValue > largest){
                largest = currentValue;
            }
        }
        return largest;
    },
    
    findLargestValueOfDataSeries : function (dataSeries){
        var largest = 0;
        for(var i = 0; i < dataSeries.length; i++){
            var currentValue = this.findLargestValueOfSet(dataSeries[i]);
            if(currentValue > largest){
                largest = currentValue;
            }
        }
        return largest;
    },
    
    findLargestCategoryOfDataSeries : function (dataSeries){
        var largest = 0;
        for(var i = 0; i < dataSeries.length; i++){
            var currentValue = this.findLargestCategoryOfSet(dataSeries[i]);
            if(currentValue > largest){
                largest = currentValue;
            }
        }
        return largest;
    },
    
    findLargestCategoryOfSet : function (setOfArrays) {
        var largest = 0;
        for(var i = 0; i < setOfArrays.length; i++){
            var currentValue = setOfArrays[i].data[setOfArrays[i].data.length-1][0];
            if(currentValue > largest){
                largest = currentValue;
            }
        }
        return largest;
    },
    
    findLargestAmountOfStudents : function (dataSeries) {
        var largest = 0;
        var currentAmount;
        for(var i = 0; i < dataSeries.length; i++){
            currentAmount = this.studentsOnGroup(dataSeries[i].data);
            if(currentAmount > largest){
                largest = currentAmount;
            }
        }
        return largest;
    },
    findLargestAmountOfStudentsCumulative : function (dataSeries) {
        var largest = 0;
        var currentAmount;
        for(var i = 0; i < dataSeries.length; i++){
            currentAmount = this.studentsOnGroupCumulative(dataSeries[i].data);
            if(currentAmount > largest){
                largest = currentAmount;
            }
        }
        return largest;
    },
    
    studentsOnGroup : function (categorizedArray) {
        var sum = 0;
        for(var i = 0; i < categorizedArray.length; i++){
            sum = sum + categorizedArray[i][1];
        }
        return sum;
    },
    studentsOnGroupCumulative : function (cumulativeArray) {
        return cumulativeArray[cumulativeArray.length-1][1];
    },

    normalizeDataSeries : function (dataSeries) {
        console.log("orggis");
        console.log(JSON.stringify(dataSeries[0]));
//        var normalizedDataSeries = dataSeries;
        var normalizedDataSeries = JSON.parse(JSON.stringify(dataSeries));

        
        var largestAmountOfStudents = this.findLargestAmountOfStudents(normalizedDataSeries);
        for(var i = 0; i < normalizedDataSeries.length; i++){
            var factor = largestAmountOfStudents/this.studentsOnGroup(normalizedDataSeries[i].data);
            //       console.log(factor);
            for(var j = 0; j < normalizedDataSeries[i].data.length; j++){
                normalizedDataSeries[i].data[j][1] = Math.round(normalizedDataSeries[i].data[j][1]*factor);
            }
        }
        console.log("orggis normeerauksen jälkeen");
        console.log(JSON.stringify(dataSeries[0]));
        console.log("normeerattu");
        console.log(JSON.stringify(normalizedDataSeries[0]));
        return normalizedDataSeries;
    },
    
    longestArray : function (dataSeries) {
        var longest = 0;
        for(var i = 0; i < dataSeries.length ; i++ ){
            var current = dataSeries[i].data.length;
            if(current > longest){
                longest  = current;
            }    
        }
        return longest;
    },
    
    normalizeCumulativeDataSeries : function(dataSeries) {
        var normalizedDataSeries = dataSeries;
        var largestAmountOfStudents = this.findLargestAmountOfStudentsCumulative(normalizedDataSeries);
        for(var i = 0; i < normalizedDataSeries.length; i++){
            var factor = largestAmountOfStudents/this.studentsOnGroupCumulative(normalizedDataSeries[i].data);
            var differenceInLength = this.longestArray(normalizedDataSeries)-normalizedDataSeries[i].data.length;
            for(var j = 0; j < normalizedDataSeries[i].data.length; j++){
                normalizedDataSeries[i].data[j][1] = Math.round(normalizedDataSeries[i].data[j][1]*factor);
            }  
            normalizedDataSeries[i].data = this.addLastPairsToCumulativeSeries(normalizedDataSeries[i].data, differenceInLength);
        }
        normalizedDataSeries = this.addAverageDataSeries(normalizedDataSeries);
        return normalizedDataSeries;
    }, 
    
    addLastPairsToCumulativeSeries : function(dataSeries,amountPairs) {
        for(var i = 0; i < amountPairs ; i++){
            var lastPair = dataSeries[dataSeries.length-1];
            var toAdd = [];
            toAdd = [lastPair[0]+1,lastPair[1]];
            dataSeries.push(toAdd);
        }
        return dataSeries;
    },
    
    mean : function(array) {
        var sum = 0;
        for(var i = 0; i < array.length ; i++){
            sum = sum + array[i]
        }
        return sum/array.length;
        
    },
    
    addAverageDataSeries : function(dataSeries) {
        if(dataSeries.length == 1){
            dataseries.push({
                data : dataSeries.data,
                label : "average"
            })
            return dataSeries;
        }
        var averageData = [];   
        var amountPairs = dataSeries[0].data.length;
        for(var i = 0; i < amountPairs ; i++){
            var values = [];
            for(var j = 0; j < dataSeries.length ; j++){
                values.push(dataSeries[j].data[i][1]);
            }
            averageData.push([i,this.mean(values)]);
        }
        dataSeries.push({
            data : averageData,
            label : "average"
        })
        return dataSeries;
    },
    
    getAverageDataOfSet : function(dataSeries) {
        for(var i = 0; i < dataSeries.length ; i++){
            if(dataSeries[i].label==="average"){
                return dataSeries[i].data;
            }
        }  
    },
    
    getHistogramDataSeries : function(dataSeries, averageData) {
        var histoGramData = [];
        for(var i = 0; i < dataSeries.data.length ; i++){
            var value = averageData[i][1]-dataSeries.data[i][1];
            histoGramData.push([i,value]);
        }
        return {
            data : histoGramData,
            label : "average - "+dataSeries.label
        }
    },
    
    getDataSeriesForDiffHistograms : function (dataSeries){
        var averageData = this.getAverageDataOfSet(dataSeries);
        var histogramDataSeries = [];
        for(var i = 0; i < dataSeries.length; i++){
            if(dataSeries[i].label == "average"){
                continue;
            }
            histogramDataSeries.push(this.getHistogramDataSeries(dataSeries[i],averageData));
        }
        return histogramDataSeries;
    }
}
