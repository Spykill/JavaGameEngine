package ca.spykill.gameengine.math;

/**
 * A vector with 3 components
 */
public class Vector3
{
	/**
	 * The X component of this vector
	 */
	private float x;
	/**
	 * The Y component of this vector
	 */
	private float y;
	/**
	 * The Z component of this vector
	 */
	private float z;

	/**
	 * The magnitude of this vector
	 */
	private float magnitude;
	private float sqr_magnitude;

	/**
	 * Initializes this vector to (0,0,0)
	 */
	public Vector3()
	{
		this(0, 0, 0);
	}
	
	/**
	 * Initializes this vector to (x,y,0)
	 */
	public Vector3(float x, float y)
	{
		this(x, y, 0);
	}
	
	public Vector3(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		Recalculate();
	}

	/**
	 * Getter for X
	 * @return X
	 */
	public float X()
	{
		return x;
	}

	/**
	 * Setter for X
	 * @param x New value of X
	 */
	public void X(float x)
	{
		this.x = x;
		Recalculate();
	}
	
	/**
	 * Getter for Y
	 * @return Y
	 */
	public float Y()
	{
		return y;
	}

	/**
	 * Setter for Y
	 * @param y New value of y
	 */
	public void Y(float y)
	{
		this.y = y;
		Recalculate();
	}

	/**
	 * Getter for Z
	 * @return Z
	 */
	public float Z()
	{
		return z;
	}

	/**
	 * Sets the z value of this vector
	 * @param z The value to set z to
	 */
	public void Z(float z)
	{
		this.z = z;
		Recalculate();
	}
	
	/**
	 * Gets the calculated magnitude
	 * @return The magnitude of this vector
	 */
	public float Magnitude()
	{
		return magnitude;
	}

	/**
	 * Gets the calculated magnitude squared
	 * @return The squared magnitude of this vector
	 */
	public float MagnitudeSquared()
	{
		return sqr_magnitude;
	}
	
	/**
	 * Adds this vector to <b>, modifying this vector in the process
	 * @param b The other vector to add
	 * @return This, for ease of use
	 */
	public Vector3 add(Vector3 b)
	{
		x += b.x;
		y += b.y;
		z += b.z;
		Recalculate();
		return this;
	}
	
	/**
	 * Creates a new Vector that is the sum of this and <b>
	 * @param b The other vector to add
	 * @return The new Vector
	 */
	public Vector3 n_add(Vector3 b)
	{
		return new Vector3(x + b.x, y + b.y, z + b.z);
	}

	
	/**
	 * Subtracts <b> from this vector, modifying this vector in the process
	 * @param b The other vector to subtract
	 * @return This, for ease of use
	 */
	public Vector3 sub(Vector3 b)
	{
		x -= b.x;
		y -= b.y;
		z -= b.z;
		Recalculate();
		return this;
	}

	/**
	 * Creates a new Vector that is the difference of this and <b>
	 * @param b The other vector to subtract
	 * @return The new Vector
	 */
	public Vector3 n_sub(Vector3 b)
	{
		return new Vector3(x - b.x, y - b.y, z - b.z);
	}
	
	/**
	 * Scales this vector by <s>, modifying this vector in the process
	 * @param s The scalar to multiply by
	 * @return This, for ease of use
	 */
	public Vector3 scale(float s)
	{
		x *= s;
		y *= s;
		z *= s;
		magnitude *= Math.abs(s);
		sqr_magnitude *= s*s;
		return this;
	}
	
	/**
	 * Creates a new Vector that is this vector scaled by <s> 
	 * @param s The scalar to multiply by
	 * @return The new vector
	 */
	public Vector3 n_scale(float s)
	{
		return new Vector3(x*s, y*s, z*s);
	}
	
	/**
	 * Negates this vector, modifying it in the process
	 * @return This, for ease of use
	 */
	public Vector3 neg()
	{
		x = -x;
		y = -y;
		z = -z;
		return this;
	}
	
	/**
	 * Creates a new Vector that is the negation of this Vector 
	 * @return The new vector
	 */
	public Vector3 n_neg()
	{
		return new Vector3(-x, -y, -z);
	}
	
	/**
	 * Computes the dot product of this and <b>
	 * @param b The other vector
	 * @return The dot product
	 */
	public float dot(Vector3 b)
	{
		return x * b.x + y * b.y + z * b.z;
	}
	
	/**
	 * Computes the cross product of this and b, modifying this
	 * @param b The other vector
	 * @return This, for ease of use
	 */
	public Vector3 cross(Vector3 b)
	{
		float nx = y * b.z - z * b.y;
		float ny = z * b.x - x * b.z;
		float nz = x * b.y - y * b.x;
		
		x = nx;
		y = ny;
		z = nz;
		Recalculate();
		
		return this;
	}
	
	/**
	 * Creates a new vector that is the cross product of this and <b>
	 * @param b The other vector
	 * @return The new vector
	 */
	public Vector3 n_cross(Vector3 b)
	{
		return new Vector3(y * b.z - z * b.y, z * b.x - x * b.z, x * b.y - y * b.x);
	}

	/**
	 * Recalculates stored values of the vector
	 */
	private void Recalculate()
	{
		this.sqr_magnitude = x*x + y*y + z*z;
		this.magnitude = (float)Math.sqrt(sqr_magnitude);
	}
}
