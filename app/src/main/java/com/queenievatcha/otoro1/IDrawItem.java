package com.queenievatcha.otoro1;

import android.graphics.Canvas;

public interface IDrawItem {
    void drawOnCanvas(Canvas canvas, float x, float y);

    int getHeight();
}
