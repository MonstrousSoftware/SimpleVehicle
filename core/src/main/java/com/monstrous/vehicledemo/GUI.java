package com.monstrous.vehicledemo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GUI implements Disposable {

    private Skin skin;
    public Stage stage;
    private Car car;
    private Label rpmValue;
    private Label gearValue;
    private Label steerAngleValue;
    private Vector3 tmpVec = new Vector3();
    private float timer = 0;

    public GUI(Car car) {
        Gdx.app.log("GUI constructor", "");
        this.car = car;
        skin = new Skin(Gdx.files.internal("blue-pixel-skin/blue-pixel.json"));
        stage = new Stage(new ScreenViewport());
    }

    private void rebuild() {
        stage.clear();
        rpmValue = new Label("", skin);
        gearValue = new Label("", skin);
        steerAngleValue = new Label("", skin);

        Table table = new Table();
        table.top().left();               // make content move to top left
        table.setFillParent(true);        // size to match stage size

        table.add(new Label("RPM (W/S) : ", skin));
        table.add(rpmValue);
        table.row();
        table.add(new Label("Gear (UP/DN) :", skin));
        table.add(gearValue);
        table.row();
        table.add(new Label("Steer angle (A/D) : ", skin));
        table.add(steerAngleValue);
        table.row();

        stage.addActor(table);
    }

    private void update( float deltaTime ){
        timer -= deltaTime;
        if(timer <= 0) {
            rpmValue.setText((int)car.rpm);
            steerAngleValue.setText((int) car.steerAngle);
            car.transform.getTranslation(tmpVec);
            if(car.gear == -1)
                gearValue.setText("R");
            else if(car.gear == 0)
                gearValue.setText("N");
            else
                gearValue.setText(car.gear);
            timer = 0.25f;
        }
    }


    public void render(float deltaTime) {
        update(deltaTime);

        stage.act(deltaTime);
        stage.draw();
    }

    public void resize(int width, int height) {
        Gdx.app.log("GUI resize", "gui " + width + " x " + height);
        stage.getViewport().update(width, height, true);
        rebuild();
    }


    @Override
    public void dispose () {
        Gdx.app.log("GUI dispose()", "");
        stage.dispose();
        skin.dispose();
    }

}
