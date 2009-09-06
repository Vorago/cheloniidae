// Cheloniidae Turtle Graphics
// Created by Spencer Tipping and licensed under the terms of the MIT source code license

package cheloniidae;

import java.awt.image.BufferedImage;
import java.awt.event.*;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.util.SortedSet;
import java.util.Iterator;
import java.util.Random;

public class TurtleWindow<T extends Turtle> extends Frame implements Viewport {
  public class RenderOperation extends Thread {
    protected final Viewport viewport;
    public RenderOperation (Viewport v) {viewport = v;}

    public void run () {
      int linesDrawnSoFar = 0;

      final Graphics2D c = context ();
      c.setColor (getBackground ());
      c.fillRect (0, 0, getWidth (), getHeight ());

      SortedSet<RenderAction> actions        = turtles.actions (viewport);
      Iterator<RenderAction>  actionIterator = actions.iterator ();

      while (! shouldCancel () && actionIterator.hasNext ()) {
        actionIterator.next ().render (viewport);
        if (++linesDrawnSoFar % drawingRefreshInterval == 0) repaint ();
      }

      repaint ();
    }
  }

  public class IntermediateRenderOperation extends Thread {
    public void run () {
      final Graphics2D c = context ();
      c.setColor (getBackground ());
      c.fillRect (0, 0, getWidth (), getHeight ());

      for (int i = 0; ! shouldCancel && i < intermediatePointCloud.length; ++i)
        if (intermediatePointCloud[i] != null) {
          Vector vprime = transformPoint (intermediatePointCloud[i]);
          if (vprime.z > 0.0) {
            vprime = projectPoint (vprime);
            if (vprime.x >= 0.0 && vprime.x < getWidth () && vprime.y >= 0.0 && vprime.y < getHeight ())
              offscreen.setRGB ((int) vprime.x, (int) vprime.y, 0);
          }
        }

      repaint ();
    }
  }

  protected int            drawingRefreshInterval     = 10000;

  protected BufferedImage  offscreen                  = null;
  protected Graphics2D     cachedContext              = null;
  protected TurtleGroup<T> turtles                    = new TurtleGroup<T> ();

  protected Vector         virtualPOV                 = new Vector (0, 0, -500.0);
  protected Vector         virtualPOVUp               = new Vector (0, 1, 0);
  protected Vector         virtualPOVForward          = new Vector (0, 0, 1);

  protected Vector         minimumExtent              = new Vector (0, 0, 0);
  protected Vector         maximumExtent              = new Vector (0, 0, 0);

  protected Vector[]       intermediatePointCloud     = new Vector[5000];

  protected int            mouseDownX                 = 0;
  protected int            mouseDownY                 = 0;
  protected boolean        mouseDown                  = false;
  protected Thread         graphicsRequestRunner      = null;
  protected boolean        graphicsRequestCancelFlag  = false;
  protected boolean        fisheye3D                  = false;
  protected long           lastChange                 = 0;
  protected boolean        shouldCancel               = false;
  
  public TurtleWindow () {initialize ();}

  public int          drawingRefreshInterval     ()                                {return drawingRefreshInterval;}
  public TurtleWindow drawingRefreshInterval     (int _drawingRefreshInterval)     {drawingRefreshInterval = _drawingRefreshInterval; return this;}
  public int          intermediatePointCloudSize ()                                {return intermediatePointCloud.length;}
  public TurtleWindow intermediatePointCloudSize (int _intermediatePointCloudSize) {intermediatePointCloud = new Vector[_intermediatePointCloudSize]; return this;}

  protected void handleResize () {
    offscreen = new BufferedImage (super.getWidth (), super.getHeight (), BufferedImage.TYPE_3BYTE_BGR);
    cachedContext = null;
    enqueueGraphicsRefreshRequest (new RenderOperation (this));
  }

  protected void initialize () {
    final TurtleWindow t = this;

    super.addWindowListener      (new WindowListener      () {public void windowClosing     (WindowEvent e)    {dispose ();}
                                                              public void windowActivated   (WindowEvent e)    {}
                                                              public void windowClosed      (WindowEvent e)    {}
                                                              public void windowDeactivated (WindowEvent e)    {}
                                                              public void windowDeiconified (WindowEvent e)    {}
                                                              public void windowIconified   (WindowEvent e)    {}
                                                              public void windowOpened      (WindowEvent e)    {}});

    super.addComponentListener   (new ComponentListener   () {public void componentResized  (ComponentEvent e) {handleResize ();}
                                                              public void componentMoved    (ComponentEvent e) {}
                                                              public void componentHidden   (ComponentEvent e) {}
                                                              public void componentShown    (ComponentEvent e) {}});

    super.addMouseListener       (new MouseListener       () {public void mouseReleased     (MouseEvent e)     {mouseDown = false;
                                                                                                                lastChange = System.currentTimeMillis ();
                                                                                                                enqueueGraphicsRefreshRequest (new RenderOperation (t));}
                                                              public void mousePressed      (MouseEvent e)     {mouseDown = true;
                                                                                                                mouseDownX = e.getX ();
                                                                                                                mouseDownY = e.getY ();}
                                                              public void mouseClicked      (MouseEvent e)     {}
                                                              public void mouseEntered      (MouseEvent e)     {}
                                                              public void mouseExited       (MouseEvent e)     {}});

    super.addMouseMotionListener (new MouseMotionListener () {public void mouseDragged (MouseEvent e) {
                                  if (mouseDown) {
                                    final Vector virtualPOVRight = virtualPOVForward.cross (virtualPOVUp);
                                    final Vector center          = new Vector (maximumExtent).multiply (0.5).addScaled (minimumExtent, 0.5);
                                    final double factor          = e.isControlDown () ? 0.1 : 1.0;

                                    // A normal drag translates the view locally.
                                    if (! e.isShiftDown ())
                                      virtualPOV.addScaled (virtualPOVUp, factor * (mouseDownY - e.getY ())).addScaled (virtualPOVRight, factor * (e.getX () - mouseDownX));
                                    else {
                                      final double pitchAngle = (e.getY () - mouseDownY) * factor;
                                      final double turnAngle  = (e.getX () - mouseDownX) * factor;

                                      virtualPOV = virtualPOV.subtract (center).rotateAbout (virtualPOVUp,    turnAngle).
                                                                                rotateAbout (virtualPOVRight, pitchAngle).add (center);

                                      virtualPOVForward = virtualPOVForward.rotateAbout (virtualPOVUp,    turnAngle).
                                                                            rotateAbout (virtualPOVRight, pitchAngle).normalize ();
                                      virtualPOVUp      = virtualPOVUp.rotateAbout      (virtualPOVRight, pitchAngle).normalize ();
                                    }

                                    lastChange = System.currentTimeMillis ();

                                    mouseDownX = e.getX ();
                                    mouseDownY = e.getY ();
                                    enqueueGraphicsRefreshRequest (new IntermediateRenderOperation ());
                                  }
                                                              }
                                                              public void mouseMoved (MouseEvent e) {}});

    super.addMouseWheelListener  (new MouseWheelListener  () {public void mouseWheelMoved (MouseWheelEvent e) {
                                                                virtualPOV.addScaled (virtualPOVForward, (e.isControlDown () ? -1 : -10) * e.getWheelRotation ());
                                                                lastChange = System.currentTimeMillis ();
                                                                enqueueGraphicsRefreshRequest (new IntermediateRenderOperation ());
                                                              }});

    super.setSize       (600, 372);
    super.setTitle      ("Cheloniidae");
    super.setVisible    (true);
    super.setBackground (Color.WHITE);

    handleResize ();
  }

  public void update (Graphics g) {paint (g);}
  public void paint  (Graphics g) {g.drawImage (offscreen, 0, 0, null);}

  public TurtleWindow add (final T t) {turtles.turtles ().add (t); t.window (this); return this;}

  public void enqueueGraphicsRefreshRequest (Thread t) {
    if (offscreen != null) {
      if (graphicsRequestRunner != null && graphicsRequestRunner.isAlive ()) {
        cancel ();
        try {graphicsRequestRunner.join ();}
        catch (InterruptedException e) {}
      }

      shouldCancel = false;
      (graphicsRequestRunner = t).start ();
    }
  }

  public TurtleWindow pause (long milliseconds) {
    enqueueGraphicsRefreshRequest (new RenderOperation (this));
    try {Thread.sleep (milliseconds);}
    catch (InterruptedException e) {}
    return this;
  }

  public TurtleWindow cancel () {shouldCancel = true; return this;}

  public boolean shouldCancel () {return shouldCancel;}
  public long    lastChange   () {return lastChange;}
  public double  scaleFactor  () {return getHeight ();}

  public void representativePoint (final Vector v) {
    final int index = Math.abs (new Random ().nextInt ()) % intermediatePointCloud.length;
    if (intermediatePointCloud[index] == null || new Random ().nextDouble () > 0.9) intermediatePointCloud[index] = v;
  }

  public Vector transformPoint (final Vector v)
    {minimumExtent.componentwiseMinimum (v);
     maximumExtent.componentwiseMaximum (v);
     return new Vector (v).subtract (virtualPOV).toCoordinateSpace (virtualPOVUp.cross (virtualPOVForward), virtualPOVUp, virtualPOVForward);}

  public Vector projectPoint (final Vector v)
    {return (fisheye3D ? new Vector (v).normalize () : new Vector (v).divide (v.z)).multiply (super.getHeight ()).add (new Vector (super.getWidth () >> 1, super.getHeight () >> 1, 0));}

  public Graphics2D context () {
    if (cachedContext != null) return cachedContext;
    else {
      final Graphics2D     g  = (Graphics2D) offscreen.getGraphics ();
      final RenderingHints rh = g.getRenderingHints ();
      rh.put (rh.KEY_ANTIALIASING, rh.VALUE_ANTIALIAS_ON);
      g.setRenderingHints (rh);
      return g;
    }
  }
}
