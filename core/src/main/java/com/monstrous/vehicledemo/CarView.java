package com.monstrous.vehicledemo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import net.mgsx.gltf.scene3d.scene.Scene;


// used to update the car visual representation to the car data

public class CarView {

    private Scene scene;
    private Car car;
    private int body;
    private int lfWheel, rfWheel;
    private int lrWheel, rrWheel;
    private Vector3 tmpVec;
    private Matrix4 [] originalTransforms;

    public CarView(Scene scene, Car car) {
        this.scene = scene;
        this.car = car;
        tmpVec = new Vector3();

        // lookup nodes for front wheels
        lfWheel = -1;
        rfWheel = -1;
        for(int i = 0; i < scene.modelInstance.nodes.size; i++){
            Node node = scene.modelInstance.nodes.get(i);
            if(node.id.contentEquals("Muscle 2"))
                body = i;
            else if(node.id.contentEquals("Muscle 2 wheel front left"))
                lfWheel = i;
            else if(node.id.contentEquals("Muscle 2 wheel front right"))
                rfWheel = i;
            else if(node.id.contentEquals("Muscle 2 wheel rear left"))
                lrWheel = i;
            else if(node.id.contentEquals("Muscle 2 wheel rear right"))
                rrWheel = i;
        }
        if(lfWheel == -1 || rfWheel == -1 || lrWheel == -1 || rrWheel == -1 || body == -1)
            Gdx.app.error("Car View", "expected nodes not found in gltf file");

        // store original transforms for all the component parts
        originalTransforms = new Matrix4[5];

        originalTransforms[0] = new Matrix4( scene.modelInstance.nodes.get(body).localTransform);
        originalTransforms[1] = new Matrix4( scene.modelInstance.nodes.get(lfWheel).localTransform);
        originalTransforms[2] = new Matrix4( scene.modelInstance.nodes.get(rfWheel).localTransform);
        originalTransforms[3] = new Matrix4( scene.modelInstance.nodes.get(lrWheel).localTransform);
        originalTransforms[4] = new Matrix4( scene.modelInstance.nodes.get(rrWheel).localTransform);

    }

    public void update() {
        moveBody(body, originalTransforms[0]);
        steerWheel(lfWheel, originalTransforms[1]);
        steerWheel(rfWheel, originalTransforms[2]);
        turnWheel(lrWheel, originalTransforms[3]);
        turnWheel(rrWheel, originalTransforms[4]);
    }

    private void moveBody(int nodeID, Matrix4 orig) {
        Node node = scene.modelInstance.nodes.get(nodeID);
        node.localTransform.set(orig);
        node.localTransform.mulLeft(car.transform);
        node.calculateWorldTransform();
    }

    private void steerWheel(int nodeID, Matrix4 orig) {
        // rotate wheel node for steering
        Node node = scene.modelInstance.nodes.get(nodeID);
        node.localTransform.set(orig);
        node.localTransform.rotate(Vector3.Y, car.steerAngle);
        node.localTransform.rotate(Vector3.X, car.wheelTurnAngle);              // also roll wheel around axle
        node.localTransform.mulLeft(car.transform);
        node.calculateWorldTransform();
    }

    private void turnWheel(int nodeID, Matrix4 orig) {
        // rotate wheel around axle
        Node node = scene.modelInstance.nodes.get(nodeID);
        node.localTransform.set(orig);
        node.localTransform.rotate(Vector3.X, car.wheelTurnAngle);  // degrees
        node.localTransform.mulLeft(car.transform);
        node.calculateWorldTransform();
    }
}
