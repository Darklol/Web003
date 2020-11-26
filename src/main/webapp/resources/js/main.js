function getResult(x,y,r) {
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
    let rValue = $(":checked").val();
    if (isNaN(rValue)) {
        rValue = parseFloat($("tbody tr").last().find(">:nth-child(3)").text());
    }
    console.log("r："+ rValue)
    return rValue;
}

clearForm();
drawPointsFromTable();

$(".clear-form-btn").click = clearForm();

$(".r-radio").click(function (){
    deleteAllPointsFromPlot();
    drawPointsFromTable();
});