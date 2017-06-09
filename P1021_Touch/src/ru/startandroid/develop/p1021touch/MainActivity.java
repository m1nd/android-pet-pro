package ru.startandroid.develop.p1021touch;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
 
public class MainActivity extends Activity {
 
        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(new MyView(this));
        }
 
        class MyView extends View {
                Paint p;
                // ���������� ��� ��������� ��������
                float x = 100;
                float y = 100;
                int side = 100;
 
                // ���������� ��� ��������������
                boolean drag = false;
                float dragX = 0;
                float dragY = 0;
 
                public MyView(Context context) {
                        super(context);
                        p = new Paint();
                        p.setColor(Color.MAGENTA);
                }
 
                protected void onDraw(Canvas canvas) {
                        // ������ �������
                        canvas.drawRect(x, y, x + side, y + side, p);
                }
 
                @Override
                public boolean onTouchEvent(MotionEvent event) {
                        // ���������� Touch-�������
                        float evX = event.getX();
                        float evY = event.getY();
 
                        switch (event.getAction()) {
                        // ������� ��������
                        case MotionEvent.ACTION_DOWN:
                                // ���� ������� ���� ������ � �������� ��������
                                if (evX >= x && evX <= x + side && evY >= y && evY <= y + side) {
                                        // �������� ����� ��������������
                                        drag = true;
                                        // ������� ����� ����� ������� ����� �������� � ������ �������
                                        dragX = evX - x;
                                        dragY = evY - y;
                                }
                                break;
                        // �����
                        case MotionEvent.ACTION_MOVE:
                                // ���� ����� �������������� �������
                                if (drag) {
                                        // ����������� ����� ���������� ��� ���������
                                        x = evX - dragX;
                                        y = evY - dragY;
                                        // �������������� �����
                                        invalidate();
                                }
                                break;
                        // ������� ���������
                        case MotionEvent.ACTION_UP:
                                // ��������� ����� ��������������
                                drag = false;
                                break;
                        }
                        return true;
                }
        }
}