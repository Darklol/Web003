function getResult(x,y,r) {
    //test part
    // console.log("x:"+x);
    // console.log("y:"+y);
    // console.log("r:"+r);
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

function isCorrectForm(){
    //validateForm part
    const xIsCorrect = $(".active").length !==0;
    const yIsCorrect = ($(".y").val() !== null) &&
        ($(".y").val() !== undefined) &&
        $(".y").val() !== "";

    // let a = $(".y").val() !== null;
    // let b = ($(".y").val() !== undefined);
    // let c = $(".y").val() !== "";
    //Warning part
    if (xIsCorrect) {
        $(".invalid-x").addClass("d-none");
    } else {
        $(".invalid-x").removeClass("d-none");
    }

    if (yIsCorrect) {
        $(".invalid-y").addClass("d-none");
    } else {
        $(".invalid-y").removeClass("d-none");
    }

    const rIsCorrect = checkOneRequiredR()

    return xIsCorrect && yIsCorrect && rIsCorrect;

}

function clearForm(){
    $(".xbtn").removeClass('active');
    $(".y").val("");
    $(":checked").prop("checked",false);
    // $(".r-text").forEach(this.prop("checked",false));
}

function checkOneRequiredR() {
    const result = $(":checked").length !== 0;
    if (result) {
        $(".invalid-r").addClass("d-none");
    } else {
        $(".invalid-r").removeClass("d-none");
    }
    return result;
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

// $(".data-form").submit(function (e) {
//     if (!isCorrectForm()) {
//         e.preventDefault();
//     }
// });

$(".clear-form-btn").click = clearForm();

$(".r-radio").click(function (){
    deleteAllPointsFromPlot();
    drawPointsFromTable();
});

$(".graphics svg").click(clickPlotHandler);

function clearTheGraphics() {$(".point").remove();}