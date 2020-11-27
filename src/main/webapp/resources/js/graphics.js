const CANVAS_WIDTH = 300;
const CANVAS_HEIGHT = 300;
const CANVAS_R_VALUE = 120;
const DEFAULT_R_VALUE = 3;

function fromTableToSvgX(x) {
    return x / getRValue() * CANVAS_R_VALUE + CANVAS_WIDTH / 2;
}

function fromTableToSvgY(y) {
    return CANVAS_HEIGHT / 2 - y / getRValue() * CANVAS_R_VALUE;
}

function fromSvgToRX(x) {
    return getRValue() * (x - CANVAS_WIDTH / 2) / CANVAS_R_VALUE;
}

function fromSvgToRY(y) {
    return getRValue() * (CANVAS_HEIGHT / 2 - y) / CANVAS_R_VALUE;
}

function deleteAllPointsFromPlot() {
    $(".point").remove();
}

function drawPointsFromTable() {
        let totalPoint = 0;
        let ignoreFirstLineFlag = 0;
        $("tbody tr").each(function () {
            ignoreFirstLineFlag = ignoreFirstLineFlag + 1;
            if (ignoreFirstLineFlag != 1) {

                const point = $(this);
                const x = parseFloat(point.find(">:first-child").text());
                const y = parseFloat(point.find(">:nth-child(2)").text());

                /** When initializing, the data in the first row is always is x=1,y=1.5.
                 /*  So i use ignoreFirstLineFlag to make sure the graphics will draw correct points.
                 */

                const r = getRValue();
                const rFlag = parseFloat(point.find(">:nth-child(3)").text());
                if (isNaN(x) || isNaN(y) || r === undefined || isNaN(rFlag)) {
                    return;
                }
                const inArea = getResult(x, y, r);
                const color = inArea ? 'green' : 'red';
                const plot = $(".graphics svg");
                const existingContent = plot.html();
                const contentToInsert = `<circle class="point" r="4" cx="${fromTableToSvgX(x)}" cy="${fromTableToSvgY(y)}" fill="${color}"></circle>`;
                plot.html(existingContent + contentToInsert);
                totalPoint = totalPoint + 1;
                console.log("totalPoint:" + totalPoint);

                // test part
                // const time = point.find(">:nth-child(4)").text();
                // const isInArea = point.find(">:nth-child(5)").text();
                // console.log("x:" + x);
                // console.log("y:" + y);
                // console.log("r:" + rFlag);
                // console.log("time:" + time);
                // console.log("isInArea:" + isInArea);
                // console.log("r"+ r)
                // first empty row - bug in h:dataTable
                // console.log("result:" + inArea);
                // console.log(color);
                // console.log(plot);
            }
        });

}

function clickPlotHandler(e) {
    const offset = $(this).offset();
    const x = e.pageX - offset.left;
    const y = e.pageY - offset.top;
    if (checkOneRequiredR()) {
        const xValue = fromSvgToRX(x);
        const yValue = fromSvgToRY(y);
        const rValue = getRValue();

        console.log(xValue);
        console.log(yValue);
        console.log(rValue);

        $(".x-hidden").val(xValue);
        $(".y-hidden").val(yValue);
        $(".r-hidden").val(rValue);
        $(".hidden-submit-btn").click();
    }
}



