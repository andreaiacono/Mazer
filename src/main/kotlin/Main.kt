import kotlinx.browser.document
import kotlinx.browser.window
import maze.BinaryGenerator
import org.w3c.dom.*
import utils.Params

private val generator = BinaryGenerator()
private val params = Params()
private val canvasContainer = document.getElementById("canvas_container")!!
private val canvas = initializeCanvas()
private val context: CanvasRenderingContext2D
    get() {
        return canvas.getContext("2d") as CanvasRenderingContext2D
    }

fun main() {

    val fgColorButton = document.getElementById("fgcolor") as HTMLInputElement
    fgColorButton.addEventListener("change", {
        params.foregroundColor = fgColorButton.value
        draw()
    })

    val bgColorButton = document.getElementById("bgcolor") as HTMLInputElement
    bgColorButton.addEventListener("change", {
        params.backgroundColor = bgColorButton.value
        draw()
    })

    val exportButton = document.getElementById("export") as HTMLButtonElement
    exportButton.addEventListener("click", {
        exportAsImage()
    })

    val newButton = document.getElementById("new") as HTMLButtonElement
    newButton.addEventListener("click", {
        generator.drawMaze(params, context)
    })

    val lineWidthRange = document.getElementById("lineWidthRange") as HTMLInputElement
    lineWidthRange.addEventListener("change", {
        params.lineWidth = lineWidthRange.value.toDouble()
        draw()
    })

    val rowsRange = document.getElementById("rowsRange") as HTMLInputElement
    rowsRange.addEventListener("change", {
        params.rows = rowsRange.value.toInt()
        draw()
    })

    val colsRange = document.getElementById("colsRange") as HTMLInputElement
    colsRange.addEventListener("change", {
        params.cols = colsRange.value.toInt()
        draw()
    })

    window.onresize = {
        setCanvasSize()
        draw()
    }

    window.onload = {
        setCanvasSize()
        draw()
    }
}

private fun initializeCanvas(): HTMLCanvasElement {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    canvasContainer.appendChild(canvas)
    return canvas
}

private fun setCanvasSize() {
    with (context) {
        canvas.width = canvasContainer.clientWidth - canvasContainer.clientWidth / 15
        canvas.height = canvasContainer.clientHeight - canvasContainer.clientHeight / 15
    }
}

private fun draw() {
    generator.draw(params, context)
}

private fun exportAsImage() {
    val img = canvas.toDataURL("image/png")
    val newWindow = window.open("","Exported maze")
    newWindow!!.document.open()
    newWindow.document.write("<head><title>Mazer Image></title></head><body><img src=\"$img\"/></body>")
}

