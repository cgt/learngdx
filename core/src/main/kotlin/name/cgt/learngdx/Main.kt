package name.cgt.learngdx

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.Rectangle

private const val WIDTH = 640f
private const val HEIGHT = 480f

class Main : ApplicationAdapter() {

    private lateinit var camera: OrthographicCamera
    private lateinit var shape: ShapeRenderer

    override fun create() {
        camera = OrthographicCamera()
        camera.setToOrtho(false, WIDTH, HEIGHT)
        shape = ShapeRenderer()

        paddle1 = Paddle(shape, camera)
        paddle2 = Paddle(shape, camera, startPosition = Pair(WIDTH - 10, HEIGHT - 40))
        ball = Ball(shape, camera)
    }

    private lateinit var paddle1: Paddle
    private lateinit var paddle2: Paddle
    private lateinit var ball: Ball

    override fun render() {
        Gdx.gl.apply {
            glClearColor(0f, 0f, 0.2f, 1f)
            glClear(GL20.GL_COLOR_BUFFER_BIT)
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            paddle1.moveUp(sprint = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            paddle1.moveDown(sprint = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
        }

        paddle2.patrol()
        ball.move()

        paddle1.render()
        paddle2.render()
        ball.render()
    }
}

class Ball(
    private val shape: ShapeRenderer,
    private val camera: Camera
) {
    private val box = Rectangle().apply {
        x = WIDTH / 2
        y = HEIGHT / 2
        width = 8f
        height = 8f
    }

    private var direction: Direction = Direction.LEFT

    fun move() {
        box.x += 1
    }

    fun render() {
        shape.projectionMatrix = camera.combined
        shape.begin(ShapeType.Filled)
        shape.color = Color.WHITE
        shape.rect(box.x, box.y, box.width, box.height)
        shape.end()
    }
}

class Paddle(
    private val shape: ShapeRenderer,
    private val camera: Camera,
    startPosition: Pair<Float, Float> = Pair(0f, 0f)
) {
    private val box = Rectangle().apply {
        x = startPosition.first
        y = startPosition.second
        width = 10f
        height = 40f
    }
    private var direction: Direction = Direction.DOWN

    fun patrol() {
        if (direction == Direction.UP) {
            moveUp(false)
        } else if (direction == Direction.DOWN) {
            moveDown(false)
        }
        if (direction == Direction.UP && box.y + 1 >= HEIGHT - 40) {
            direction = Direction.DOWN
        } else if (direction == Direction.DOWN && box.y - 1 <= 0) {
            direction = Direction.UP
        }
    }

    fun render() {
        shape.projectionMatrix = camera.combined
        shape.begin(ShapeType.Filled)
        shape.color = Color.WHITE
        shape.rect(box.x, box.y, box.width, box.height)
        shape.end()
    }

    fun moveUp(sprint: Boolean) {
        if (box.y + 1 < HEIGHT - 40) {
            box.y += 1
            if (sprint) {
                moveUp(sprint = false)
            }
        }
    }

    fun moveDown(sprint: Boolean) {
        if (box.y - 1 > 0) {
            box.y -= 1
            if (sprint) {
                moveDown(sprint = false)
            }
        }
    }
}

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
}
