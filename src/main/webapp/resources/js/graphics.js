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
    $("tbody tr").each(function () {
        const point = $(this);
        const x = parseFloat(point.find(">:first-child").text());
        const y = parseFloat(point.find(">:nth-child(2)").text());
        // first empty row - bug in h:dataTable
        if (isNaN(x) || isNaN(y)) {
            return;
        }

        const color = getResult(x, y, getRValue()) ? 'green' : 'red';
        console.log(color);
        const plot = $(".graphics svg");
        console.log(plot);
        const existingContent = plot.html();
        const contentToInsert = `<circle class="point" r="4" cx="${fromTableToSvgX(x)}" cy="${fromTableToSvgY(y)}" fill="${color}"></circle>`;
        plot.html(existingContent + contentToInsert);
    });
}
function deleteAllPointsFromPlot() {
    $(".point").remove();
}