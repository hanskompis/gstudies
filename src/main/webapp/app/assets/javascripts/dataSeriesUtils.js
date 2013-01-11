var dataSeriesUtils = {
    response : null,
    PASSED : 0, 
    FAILED : 1, 
    ALL : 2,
    
    //    DSCreditGains7MonthsPass : null,
    //    DSCreditGains13MonthsPass : null,
    //    DSCreditGains19MonthsPass : null,
    //    DSCreditGains7MonthsFail : null,
    //    DSCreditGains13MonthsFail : null,
    //    DSCreditGains19MonthsFail : null, 
    //    DSCreditGains7MonthsAll : null,
    //    DSCreditGains13MonthsAll : null, 
    //    DSCreditGains19MonthsAll : null,
    //    
    //    DSCreditGains7MonthsNormPass : null,
    //    DSCreditGains13MonthsNormPass : null,
    //    DSCreditGains19MonthsNormPass : null,
    //    DSCreditGains7MonthsNormFail : null,
    //    DSCreditGains13MonthsNormFail : null,
    //    DSCreditGains19MonthsNormFail : null,
    //    DSCreditGains7MonthsNormAll : null,
    //    DSCreditGains13MonthsNormAll : null,
    //    DSCreditGains19MonthsNormAll : null,
    
    //    largestValueOfCreditGains7Months : null,
    //    largestValueOfCreditGains13Months : null,
    //    largestValueOfCreditGains19Months : null,
    //    largestCategoryOfCreditGains7Months : null,
    //    
    //    largestValueOfCreditGains7MonthsNorm : null,
    //    largestValueOfCreditGains13MonthsNorm : null,
    //    largestValueOfCreditGains19MonthsNorm : null,


    
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
    
    studentsOnGroup : function (categorizedArray) {
        var sum = 0;
        for(var i = 0; i < categorizedArray.length; i++){
            sum = sum + categorizedArray[i][1];
        }
        return sum;
    },
    
    normalizeDataSeries : function (dataSeries) {
        console.log(JSON.stringify(dataSeries));
        var normalizedDataSeries = dataSeries;
        var largestAmountOfStudents = this.findLargestAmountOfStudents(normalizedDataSeries);
        for(var i = 0; i < normalizedDataSeries.length; i++){
            var factor = largestAmountOfStudents/this.studentsOnGroup(normalizedDataSeries[i].data);
            console.log(factor);
            for(var j = 0; j < normalizedDataSeries[i].data.length; j++){
                normalizedDataSeries[i].data[j][1] = Math.round(normalizedDataSeries[i].data[j][1]*factor);
            }
        }
        console.log(JSON.stringify(normalizedDataSeries));
        return normalizedDataSeries;
    }
}
