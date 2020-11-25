// let

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
    // let rValue = parseFloat($("tbody tr:first").find(">:nth-child(3)").text());
    // let rValue = parseFloat(point.find(">:nth-child(3)").text());
    // alert(rValue);
    let rValue = $(":checked").val()
    if(rValue === undefined){
        alert("Please choose R value");
        return null;
    }
    console.log("r = "+ rValue)
    return rValue;
}

$(".clear-form-btn").click = clearForm();

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