package name.cgt.learngdx

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle

class Main : ApplicationAdapter() {

    private lateinit var bucketImage: Texture
    private lateinit var camera: OrthographicCamera
    private lateinit var batch: SpriteBatch
    private lateinit var bucket: Rectangle

    override fun create() {
        bucketImage = Texture(Gdx.files.internal("bucket.png"))

        camera = OrthographicCamera()
        camera.setToOrtho(false, 640f, 480f)

        batch = SpriteBatch()

        bucket = Rectangle().apply {
            x = 0f
            y = 0f
            width = 80f
            height = 80f
        }
    }

    override fun render() {
        Gdx.gl.apply {
            glClearColor(0f, 0f, 0.2f, 1f)
            glClear(GL20.GL_COLOR_BUFFER_BIT)
        }
        camera.update()
        batch.apply {
            projectionMatrix = camera.combined
            begin()
            draw(bucketImage, bucket.x, bucket.y)
            end()
        }
    }

    override fun dispose() {
        bucketImage.dispose()
        batch.dispose()
    }
}