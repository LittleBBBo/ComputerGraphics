import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import processing.opengl.PShader;

import java.io.File;

/**
 * Created by BodeNg on 2016/12/12.
 */
public class Shadow extends PApplet {
    PShape can;
    float angle;

    PShader lightShader;

    PShape createCan(float r, float h, int detail) {
        textureMode(NORMAL);
        PShape sh = createShape();
        sh.beginShape(QUAD_STRIP);
        sh.noStroke();
        for (int i = 0; i <= detail; i++) {
            float angle = TWO_PI / detail;
            float x = sin(i * angle);
            float z = cos(i * angle);
            float u = (float)(i) / detail;
            sh.normal(x, 0, z);
            sh.vertex(x * r, -h/2, z * r, u, 0);
            sh.vertex(x * r, +h/2, z * r, u, 1);
        }
        sh.endShape();
        return sh;
    }

    @Override
    public void setup() {
        size(640, 360, P3D);
        can = createCan(100, 200, 32);
        lightShader = loadShader("src/pixlightfrag.glsl", "src/pixlightvert.glsl");
    }

    @Override
    public void draw() {
        background(0);

        shader(lightShader);

        pointLight(255, 255, 255, width/2, height, 200);

        translate(width/2, height/2);
//        rotateY(angle);
        shape(can);
        angle += 0.01;
    }


//    @Override
//    public void stop() {
//        player.close();
//        minim.stop();
//        super.stop();
//    }

    public static void main(String args[]) {
        PApplet.main(new String[] { "Shadow" });
    }
}