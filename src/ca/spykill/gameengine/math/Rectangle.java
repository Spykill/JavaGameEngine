package ca.spykill.gameengine.math;

import java.awt.datatransfer.FlavorTable;

public class Rectangle
{
	private double x;
	private double y;
	private double w;
	private double h;
	
	private double b;
	private double r;
	
	public Rectangle()
	{
		this(0, 0, 0, 0);
	}
	
	public Rectangle(double x, double y, double w, double h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		this.b = this.y + this.h;
		this.r = this.x + this.w;
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
		this.w = this.x + this.w;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
		this.b = this.y + this.h;
	}

	public double getW()
	{
		return w;
	}

	public void setW(double w)
	{
		this.w = w;
		this.r = this.x + w;
	}

	public double getH()
	{
		return h;
	}

	public void setH(double h)
	{
		this.h = h;
		this.b = this.y + h;
	}

	public double getBottom()
	{
		return b;
	}

	public void setBottom(double b)
	{
		this.h += b - this.b;
		if(this.h < 0)
		{
			this.y += this.h;
			this.h = -this.h;
		}
		this.b = b;
	}

	public double getRight()
	{
		return r;
	}

	public void setRight(double r)
	{
		this.w += r - this.r;
		if(this.w < 0)
		{
			this.x += this.w;
			this.w = -this.w;
		}
		this.r = r;
	}
	
	public double getArea()
	{
		return this.w * this.h;
	}
	
	public double getPerimeter()
	{
		return 2 * this.w + 2 * this.h;
	}
	
	public Vector3 getCentre()
	{
		return new Vector3(this.x + this.w * .5f, this.y + this.h * .5f);
	}

	public boolean containsPoint(double px, double py)
	{
		return (px < x || px > r || py < y || py > b);
	}

	public boolean containsPoint(Vector3 point)
	{
		return containsPoint(point.X(), point.Y());
	}

	public boolean intersects(Rectangle rect)
	{
		return rect.x > r || rect.y > b || rect.r < rect.x || rect.b < rect.y;
	}

	/**
	 * Translates the rectangle by <offset>
	 * @param offset Amount to translate by
	 * @return The reference to the this for ease of use
	 */
	public Rectangle translate(Vector3 offset)
	{
		this.x += offset.X();
		this.y += offset.Y();
		this.r = this.x + this.w;
		this.b = this.y + this.h;
		return this;
	}

	/**
	 * Creates a new rectangle with the same origin and dimensions as this rectangle
	 * @return The new rectangle
	 */
	public Rectangle clone()
	{
		return new Rectangle(x, y, w, h);
	}
}
