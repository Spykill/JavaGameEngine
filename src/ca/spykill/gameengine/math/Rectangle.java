package ca.spykill.gameengine.math;

import java.awt.datatransfer.FlavorTable;

public class Rectangle
{
	private float x;
	private float y;
	private float w;
	private float h;
	
	private float b;
	private float r;
	
	public Rectangle()
	{
		this(0, 0, 0, 0);
	}
	
	public Rectangle(float x, float y, float w, float h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		this.b = this.y + this.h;
		this.r = this.x + this.w;
	}

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
		this.w = this.x + this.w;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
		this.b = this.y + this.h;
	}

	public float getW()
	{
		return w;
	}

	public void setW(float w)
	{
		this.w = w;
		this.r = this.x + w;
	}

	public float getH()
	{
		return h;
	}

	public void setH(float h)
	{
		this.h = h;
		this.b = this.y + h;
	}

	public float getBottom()
	{
		return b;
	}

	public void setBottom(float b)
	{
		this.h += b - this.b;
		if(this.h < 0)
		{
			this.y += this.h;
			this.h = -this.h;
		}
		this.b = b;
	}

	public float getRight()
	{
		return r;
	}

	public void setRight(float r)
	{
		this.w += r - this.r;
		if(this.w < 0)
		{
			this.x += this.w;
			this.w = -this.w;
		}
		this.r = r;
	}
	
	public float getArea()
	{
		return this.w * this.h;
	}
	
	public float getPerimeter()
	{
		return 2 * this.w + 2 * this.h;
	}
	
	public Vector3 getCentre()
	{
		return new Vector3(this.x + this.w * .5f, this.y + this.h * .5f);
	}
}
