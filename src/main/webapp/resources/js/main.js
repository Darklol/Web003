function getResult(x,y,r) {
    console.log("x:"+x);
    console.log("y:"+y);
    console.log("r:"+r);

    // //check part
    // let checkFirst = (firstQuarter && (y <= -x + r))
    // let checkThird = (thirdQuarter && (x >= -r) && (y >= -r/2));
    // let checkFourth = (fourthQuarter && (x * x + y * y <= r * r));

    //Quarter part
    let firstQuarter = x >= 0 && y >= 0;
    let thirdQuarter = x <= 0 && y <= 0;
    let fourthQuarter = x >= 0 && y <= 0;
    //check part
    return (thirdQuarter && (x >= -r) && (y >= -r/2)) ||
        (fourthQuarter && (x * x + y * y <= r * r)) ||
        (firstQuarter && (y <= -x + r));
}

$(".xbtn").click(function (){
    $(".xbtn").removeClass('active');
    $(this).addClass('active')
})

function clearForm(){
    $(".xbtn").removeClass('active');
    $(".y").val("");
    $(":checked").prop("checked",false);
    // $(".r-text").forEach(this.prop("checked",false));
}

function getRValue(){
    let rValue = parseFloat($(":checked").val());
    if (isNaN(rValue)) {
        rValue = parseFloat($("tbody tr").last().find(">:nth-child(3)").text());
    }
    return rValue;
}

clearForm();
drawPointsFromTable();

$(".clear-form-btn").click = clearForm();

$(".r-radio").click(function (){
    deleteAllPointsFromPlot();
    drawPointsFromTable();
});