package name.cgt.learngdx

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType

private const val width = 640f
private const val height = 480f

class Main : ApplicationAdapter() {

    private lateinit var camera: OrthographicCamera
    private lateinit var shape: ShapeRenderer

    override fun create() {
        camera = OrthographicCamera()
        camera.setToOrtho(false, width, height)
        shape = ShapeRenderer()

        paddle1 = Paddle(shape, camera).apply {
            x = 0f
            y = 0f
        }
        paddle2 = Paddle(shape, camera).apply {
            x = 630f
            y = 440f
        }
    }

    private lateinit var paddle1: Paddle
    private lateinit var paddle2: Paddle

    override fun render() {
        Gdx.gl.apply {
            glClearColor(0f, 0f, 0.2f, 1f)
            glClear(GL20.GL_COLOR_BUFFER_BIT)
        }

        paddle2.let { p2 ->
            move(p2)
        }

        paddle1.render()
        paddle2.render()
    }

    private fun move(p2: Paddle) {
        if (p2.y + 1 > height - 40) {
            p2.direction = -1
        } else if (p2.y - 1 < 0) {
            p2.direction = 1
        }
        p2.y += 1 * p2.direction
    }

}

class Paddle(private val shape: ShapeRenderer, private val camera: Camera) {
    var x: Float = 0.0f
    var y: Float = 0.0f
    var direction: Int = 1

    fun render() {
        shape.projectionMatrix = camera.combined
        shape.begin(ShapeType.Filled)
        shape.color = Color.WHITE
        shape.rect(x, y, 10f, 40f)
        shape.end()
    }
}