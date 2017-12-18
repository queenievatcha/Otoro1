package com.queenievatcha.otoro1;

import android.graphics.Canvas;


public class DrawBlankSpace implements IDrawItem {

    private int blankSpace;

    public DrawBlankSpace(int blankSpace) {
        this.blankSpace = blankSpace;
    }

    @Override
    public void drawOnCanvas(Canvas canvas, float x, float y) {
    }

    @Override
    public int getHeight() {
        return blankSpace;
    }
}
