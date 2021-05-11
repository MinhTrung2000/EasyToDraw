package control.myawt;

import java.awt.Color;

public interface SKGraphic2D {

    /**
     * Strokes the outline of a <code>Shape</code> using the settings of the
     * current <code>Graphics2D</code> context. The rendering attributes applied
     * include the <code>Clip</code>, <code>Transform</code>, <code>Paint</code>
     * , <code>Composite</code> and <code>Stroke</code> attributes.
     *
     * @param s the <code>Shape</code> to be rendered
     * @see #setStroke
     * @see #setPaint
     * @see #setColor
     * @see #transform
     *
     *
     * @see #setClip
     * @see #setComposite
     */
    void draw(SKShapeInterface s);

    /**
     * Renders the text of the specified <code>String</code>, using the current
     * text attribute state in the <code>Graphics2D</code> context. The baseline
     * of the first character is at position (<i>x</i>,&nbsp;<i>y</i>) in the
     * User Space. The rendering attributes applied include the
     * <code>Clip</code>, <code>Transform</code>, <code>Paint</code>,
     * <code>Font</code> and <code>Composite</code> attributes. For characters
     * in script systems such as Hebrew and Arabic, the glyphs can be rendered
     * from right to left, in which case the coordinate supplied is the location
     * of the leftmost character on the baseline.
     *
     * @param str the string to be rendered
     * @param x the x coordinate of the location where the <code>String</code>
     * should be rendered
     * @param y the y coordinate of the location where the <code>String</code>
     * should be rendered
     * @throws NullPointerException if <code>str</code> is <code>null</code>
     * @since JDK1.0
     */
    void drawString(String str, int x, int y);

    /**
     * Renders the text specified by the specified <code>String</code>, using
     * the current text attribute state in the <code>Graphics2D</code> context.
     * The baseline of the first character is at position (<i>x</i>,&nbsp;
     * <i>y</i>) in the User Space. The rendering attributes applied include the
     * <code>Clip</code>, <code>Transform</code>, <code>Paint</code>,
     * <code>Font</code> and <code>Composite</code> attributes. For characters
     * in script systems such as Hebrew and Arabic, the glyphs can be rendered
     * from right to left, in which case the coordinate supplied is the location
     * of the leftmost character on the baseline.
     *
     * @param str the <code>String</code> to be rendered
     * @param x the x coordinate of the location where the <code>String</code>
     * should be rendered
     * @param y the y coordinate of the location where the <code>String</code>
     * should be rendered
     * @throws NullPointerException if <code>str</code> is <code>null</code>
     * @see #setPaint
     * @see #setColor
     * @see #setFont
     *
     * @see #setComposite
     * @see #setClip
     */
    void drawString(String str, double x, double y);

    /**
     * Fills the interior of a <code>Shape</code> using the settings of the
     * <code>Graphics2D</code> context. The rendering attributes applied include
     * the <code>Clip</code>, <code>Transform</code>, <code>Paint</code>, and
     * <code>Composite</code>.
     *
     * @param s the <code>Shape</code> to be filled
     * @see #setPaint
     * @see #setColor
     * @see #transform
     *
     * @see #setComposite
     * @see #setClip
     */
    void fill(SKShapeInterface s);

    /**
     * Sets the <code>Paint</code> attribute for the <code>Graphics2D</code>
     * context. Calling this method with a <code>null</code> <code>Paint</code>
     * object does not have any effect on the current <code>Paint</code>
     * attribute of this <code>Graphics2D</code>.
     *
     * @param paint the <code>Paint</code> object to be used to generate color
     * during the rendering process, or <code>null</code>
     * @see #setColor
     * @see GGradientPaint
     * @see GTexturePaint
     */
    void setPaint(SKShapeInterface paint);

    /**
     * Sets the <code>Stroke</code> for the <code>Graphics2D</code> context.
     *
     * @param s the <code>Stroke</code> object to be used to stroke a
     * <code>Shape</code> during the rendering process
     * @see GBasicStroke
     * @see #getStroke
     */
    void setStroke(SKBasicStrokeInterface s);

    /**
     * Concatenates the current <code>Graphics2D</code> <code>Transform</code>
     * with a translation transform. Subsequent rendering is translated by the
     * specified distance relative to the previous position. This is equivalent
     * to calling transform(T), where T is an <code>AffineTransform</code>
     * represented by the following matrix:
     *
     * <pre>
     *          [   1    0    tx  ]
     *          [   0    1    ty  ]
     *          [   0    0    1   ]
     * </pre>
     *
     * @param tx the distance to translate along the x-axis
     * @param ty the distance to translate along the y-axis
     */
    void translate(double tx, double ty);

    /**
     * Concatenates the current <code>Graphics2D</code> <code>Transform</code>
     * with a scaling transformation Subsequent rendering is resized according
     * to the specified scaling factors relative to the previous scaling. This
     * is equivalent to calling <code>transform(S)</code>, where S is an
     * <code>AffineTransform</code> represented by the following matrix:
     *
     * <pre>
     *          [   sx   0    0   ]
     *          [   0    sy   0   ]
     *          [   0    0    1   ]
     * </pre>
     *
     * @param sx the amount by which X coordinates in subsequent rendering
     * operations are multiplied relative to previous rendering operations.
     * @param sy the amount by which Y coordinates in subsequent rendering
     * operations are multiplied relative to previous rendering operations.
     */
    void scale(double sx, double sy);

    /**
     * Composes an <code>AffineTransform</code> object with the
     * <code>Transform</code> in this <code>Graphics2D</code> according to the
     * rule last-specified-first-applied. If the current <code>Transform</code>
     * is Cx, the result of composition with Tx is a new <code>Transform</code>
     * Cx'. Cx' becomes the current <code>Transform</code> for this
     * <code>Graphics2D</code>. Transforming a point p by the updated
     * <code>Transform</code> Cx' is equivalent to first transforming p by Tx
     * and then transforming the result by the original <code>Transform</code>
     * Cx. In other words, Cx'(p) = Cx(Tx(p)). A copy of the Tx is made, if
     * necessary, so further modifications to Tx do not affect rendering.
     *
     * @param Tx the <code>AffineTransform</code> object to be composed with the
     * current <code>Transform</code>
     *
     * @see GAffineTransform
     */
    void transform(SKAffineTranformInterface Tx);

    /**
     * Returns the background color used for clearing a region.
     *
     * @return the current <code>Graphics2D</code> <code>Color</code>, which
     * defines the background color.
     */
    Color getBackground();

    /**
     * Returns the current <code>Stroke</code> in the <code>Graphics2D</code>
     * context.
     *
     * @return the current <code>Graphics2D</code> <code>Stroke</code>, which
     * defines the line style.
     * @see #setStroke
     */
    SKBasicStrokeInterface getStroke();

    Color getColor();

    void setColor(Color selColor);

    void fillRect(int x, int y, int width, int height);

    void clearRect(int x, int y, int width, int height);

    void drawRect(int x, int y, int width, int height);

    void drawLine(int x1, int y1, int x2, int y2);

    void setClip(SKShapeInterface shape);

    void setClip(SKShapeInterface shape, boolean saveContext);

    void setClip(int x, int y, int width, int height);

    void resetClip();

    void setAntialiasing();

    void setTransparent();

    /**
     * Saves the state of the current transformation matrix.
     */
    void saveTransform();

    /**
     * Restores the transformation matrix to the state where
     * <b>saveTransform()</b> was called.
     */
    void restoreTransform();

    /**
     * start a new general path we'll add lines etc. to
     */
    void startGeneralPath();

    /**
     * add straight line to current general path
     *
     * @param x1 first point x coordinate
     * @param y1 first point y coordinate
     * @param x2 second point x coordinate
     * @param y2 second point y coordinate
     */
    void addStraightLineToGeneralPath(double x1, double y1,
            double x2, double y2);

    /**
     * end current general path and draw it
     */
    void endAndDrawGeneralPath();
}
