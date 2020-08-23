package name.cgt.learngdx

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera

class Main : ApplicationAdapter() {

    private lateinit var camera: OrthographicCamera

    override fun create() {
        camera = OrthographicCamera()
        camera.setToOrtho(false, 640f, 480f)

    }

    override fun render() {
        Gdx.gl.apply {
            glClearColor(0f, 0f, 0.2f, 1f)
            glClear(GL20.GL_COLOR_BUFFER_BIT)
        }
        camera.update()
    }
}