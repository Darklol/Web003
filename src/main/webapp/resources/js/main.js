$(".xbtn").click(function (){
    $(".xbtn").removeClass('active');
    $(this).addClass('active')
})

function clearForm(){
    $(".xbtn").removeClass('active');
    $(".y").val("");
    alert("changed")
    $(".r-text").each(function (){
        $(this).attr("checked",false);
    })
}

$(".clear-form-btn").click = clearForm();