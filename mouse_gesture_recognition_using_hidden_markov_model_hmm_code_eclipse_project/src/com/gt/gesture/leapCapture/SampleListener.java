package com.gt.gesture.leapCapture;
import java.awt.Point;
import java.util.Vector;

import javax.swing.JPanel;

import com.leapmotion.leap.*;


public class SampleListener extends Listener
{
	private double startX, startY, startZ;
	private int x, y, z;
	
	private JPanel panel=null;
	private Vector<Double> rf_curTime=null;
	private Vector<Point> rf_drawPoint=null;
	
	boolean draw=false;
	
	
	public SampleListener(double startX, double startY, double startZ, JPanel panel)
	{
		this.startX = startX;
		this.startY = startY;
		this.startZ = startZ;
		this.panel = panel;
	}	
	
	public void setRf_curTime(Vector<Double> rf_curTime) {
		this.rf_curTime = rf_curTime;
	}

	public void setRf_drawPoint(Vector<Point> rf_drawPoint) {
		this.rf_drawPoint = rf_drawPoint;
	}

	public void setPanel(JPanel panel)
	{
		this.panel = panel;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public void setDraw(boolean draw)
	{
		this.draw = draw;
	}
	
	private void add(int x, int y, double curTime) {
		rf_curTime.add(curTime);
		rf_drawPoint.add(new Point(x, y));
		panel.paintImmediately(panel.getBounds());
		// repaint();
	}	
	
	
	 public void onFrame(Controller controller)
    {
        Frame frame = controller.frame();        
        HandList hands = frame.hands();
        
        if(draw)
        {
        	com.leapmotion.leap.Vector pos = hands.get(0).palmPosition();
            x =  (int) pos.getX();
            y = (int) pos.getY();
            z = (int) pos.getZ();
            if(x!=0 && z!=0)
            {
            	this.add(this.getX(), this.getZ(), System.currentTimeMillis());
            	System.out.println((this.getX()) +" "+ (this.getZ()));
            }
        }      
    }
	
	public void onConnect(Controller controller)
	{
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
    }

   
    

}
