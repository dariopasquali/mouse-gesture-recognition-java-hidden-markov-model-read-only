package com.gt.gesture.leapCapture;
/*
Please feel free to use/modify this class. 
If you give me credit by keeping this information or
by sending me an email before using it or by reporting bugs , i will be happy.
Email : gtiwari333@gmail.com,
Blog : http://ganeshtiwaridotcomdotnp.blogspot.com/ 
*/

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;




import javax.swing.JFrame;
import javax.swing.JPanel;

import com.gt.gesture.features.RawFeature;
import com.leapmotion.leap.Controller;

/**
* 
* @author Ganesh
* 
*/

public class DataCapturePanel extends JPanel  implements MouseListener, KeyListener{
	private static final long serialVersionUID = 8652574423524244194L;
	private final int RADIUS = 5;
	private boolean drawingStarted = false;
	private Color BGCOLOR = Color.PINK;
	private final int MIN_POINTS = 10;
	private Vector<Double> rf_curTime;
	private Vector<Point> rf_drawPoint;
	
	Controller c = new Controller();
	SampleListener listener;
	
	/**
	 * Constructor
	 */
	public DataCapturePanel() {
		setBackground(BGCOLOR);
		this.getHeight();
		Point p = this.getLocation();
		listener = new SampleListener(p.getX(), 0, p.getY(), this);
		c.addListener(listener);
		addMouseListener(this);
		addKeyListener(this);
		rf_curTime = new Vector<Double>();
		rf_drawPoint = new Vector<Point>();			
	}

	/**
	 * Draw
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < rf_curTime.size(); i++) {
			// System.out.println("painting" + rf_curTime.size());
			((Graphics2D) g).drawOval((int) rf_drawPoint.get(i).getX(), (int) rf_drawPoint.get(i).getY(), RADIUS, RADIUS);
		}

	}

	/**
	 * Animate using previously recorded data
	 * 
	 * @param pointListRecorded
	 */
	public void animateCaptured(RawFeature pointListRecorded)
	{
		rf_curTime.clear();
		rf_drawPoint.clear();
		
		// System.out.println(pointListRecorded.getDrawPoint().length);
		for (int i = 0; i < pointListRecorded.getCurTime().length - 1; i++)
		{
			Point tmpPt = pointListRecorded.getDrawPoint()[i];
			double diffTime = pointListRecorded.getCurTime()[i + 1] - pointListRecorded.getCurTime()[i];
			
			add((int) tmpPt.getX(), (int) tmpPt.getY(), pointListRecorded.getCurTime()[i]);
			
			try
			{
				Thread.sleep((long) diffTime);
			} catch (InterruptedException e) {
				System.out.println(e.toString());
			}
			// System.out.println("animating..." + diffTime * 100);
		}
	}

	/**
	 * Add points to list and repaint
	 * 
	 * @param x
	 * @param y
	 */
	private void add(int x, int y, double curTime) {
		rf_curTime.add(curTime);
		rf_drawPoint.add(new Point(x, y));
		this.paintImmediately(getBounds());
		// repaint();
	}

	/** GETTERS **/

	/** Mouse Listeners and MouseMotionListeners **/
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		/*if(drawingStarted)
		{
			add(listener.getX(), listener.getZ(), System.currentTimeMillis());
			System.out.println((listener.getX()) +" "+ (listener.getZ()));
		}	*/
	}
	
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		if(!drawingStarted)
		{
			drawingStarted = true;
			this.setBackground(Color.white);
			rf_curTime.clear();
			rf_drawPoint.clear();
			
			listener.setRf_curTime(rf_curTime);
			listener.setRf_drawPoint(rf_drawPoint);
			
			listener.setDraw(true);
			
		}
		else
		{
			drawingStarted = false;
			this.setBackground(BGCOLOR);
			listener.setDraw(false);
		}
		
	}
	
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		
		
			
		
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
			
	}

	// ** FOR TESTING **//
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("MouseTest");
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = frame.getContentPane();
		contentPane.add(new DataCapturePanel());
		frame.setVisible(true);
	}

	public RawFeature getCapturedRawFeature() {
		// convert to double FRM Double
		RawFeature rf = new RawFeature();
		int len = getRf_curTime().size();
		double[] curTime = new double[len];
		Point[] drawPoint = new Point[len];
		for (int i = 0; i < getRf_curTime().size(); i++) {
			curTime[i] = getRf_curTime().get(i).doubleValue();
			drawPoint[i] = getRf_drawPoint().get(i);
		}
		return new RawFeature(curTime, drawPoint);
	}

	public boolean isDataAvailable() {
		return (getRf_curTime().size() > 10);
	}

	public boolean isDrawingStarted() {
		return drawingStarted;
	}

	public void setDrawingStarted(boolean drawingStarted) {
		this.drawingStarted = drawingStarted;
	}

	public Vector<Double> getRf_curTime() {
		return rf_curTime;
	}

	public void setRf_curTime(Vector<Double> rf_curTime) {
		this.rf_curTime = rf_curTime;
	}

	public Vector<Point> getRf_drawPoint() {
		return rf_drawPoint;
	}

	public void setRf_drawPoint(Vector<Point> rf_drawPoint) {
		this.rf_drawPoint = rf_drawPoint;
	}

	
	

	
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
		
	}

	

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}