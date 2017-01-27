package ca.spykill.gameengine.math;

/**
 * A vector with 3 components
 */
public class Vector3
{
	/**
	 * The X component of this vector
	 */
	private double x;
	/**
	 * The Y component of this vector
	 */
	private double y;
	/**
	 * The Z component of this vector
	 */
	private double z;

	/**
	 * The magnitude of this vector
	 */
	private double magnitude;
	private double sqr_magnitude;

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
	public Vector3(double x, double y)
	{
		this(x, y, 0);
	}
	
	public Vector3(double x, double y, double z)
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
	public double X()
	{
		return x;
	}

	/**
	 * Setter for X
	 * @param x New value of X
	 */
	public void X(double x)
	{
		this.x = x;
		Recalculate();
	}
	
	/**
	 * Getter for Y
	 * @return Y
	 */
	public double Y()
	{
		return y;
	}

	/**
	 * Setter for Y
	 * @param y New value of y
	 */
	public void Y(double y)
	{
		this.y = y;
		Recalculate();
	}

	/**
	 * Getter for Z
	 * @return Z
	 */
	public double Z()
	{
		return z;
	}

	/**
	 * Sets the z value of this vector
	 * @param z The value to set z to
	 */
	public void Z(double z)
	{
		this.z = z;
		Recalculate();
	}
	
	/**
	 * Gets the calculated magnitude
	 * @return The magnitude of this vector
	 */
	public double Magnitude()
	{
		return magnitude;
	}

	/**
	 * Gets the calculated magnitude squared
	 * @return The squared magnitude of this vector
	 */
	public double MagnitudeSquared()
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
	public Vector3 scale(double s)
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
	public Vector3 n_scale(double s)
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
	public double dot(Vector3 b)
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
		double nx = y * b.z - z * b.y;
		double ny = z * b.x - x * b.z;
		double nz = x * b.y - y * b.x;
		
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
	 * Normalizes this vector (makes it magnitude 1)
	 * @return This, for ease of use
	 */
	public Vector3 normalize()
	{
		this.x /= this.magnitude;
		this.y /= this.magnitude;
		this.z /= this.magnitude;
		this.magnitude = 1;
		this.sqr_magnitude = 1;
		return this;
	}

	/**
	 * Creates a new vector that is the normalized version of this
	 * @return The new vector
	 */
	public Vector3 n_normalize()
	{
		return new Vector3(this.x / this.magnitude, this.y / this.magnitude, this.z / this.magnitude);
	}

	/**
	 * Projects this vector on vector <u>
	 * @param u The vector to project onto
	 * @return This, for ease of use
	 */
	public Vector3 project(Vector3 u)
	{
		return scale(u.dot(this) / u.sqr_magnitude);
	}

	/**
	 * Creates a new vector that is this projected on u
	 * @param u The vector to project onto
	 * @return The new vector
	 */
	public Vector3 n_project(Vector3 u)
	{
		double s = u.dot(this) / u.sqr_magnitude;
		return new Vector3(u.X() * s, u.Y() * s, u.Z() * s);
	}

	/**
	 * Projects this onto the plane defined by <normal>
	 * @param normal The normal of the plane to project onto
	 * @return This, for ease of use
	 */
	public Vector3 projectOnPlane(Vector3 normal)
	{
		double s = normal.dot(this) / normal.sqr_magnitude;
		this.x -= normal.X() * s;
		this.y -= normal.Y() * s;
		this.z -= normal.Z() * s;
		Recalculate();
		return this;
	}

	/**
	 * Creates a new vector that is this projected onto the plane defined by <normal>
	 * @param normal The normal of the plane to project onto
	 * @return The new vector
	 */
	public Vector3 n_projectOnPlane(Vector3 normal)
	{
		double s = normal.dot(this) / normal.sqr_magnitude;
		return new Vector3(this.x - normal.X() * s, this.y - normal.Y() * s, this.z - normal.Z() * s);
	}

	/**
	 * Creates a new vector that linearly interpolates between a and b by alpha
	 * @param a The vector at 0
	 * @param b The vector at 1
	 * @param alpha The interpolation value
	 * @return The new vector
	 */
	public static Vector3 lerp(Vector3 a, Vector3 b, float alpha)
	{
		double dx = b.X() - a.X();
		double dy = b.Y() - a.Y();
		double dz = b.Z() - a.Z();

		return new Vector3(a.X() + dx * alpha, a.Y() + dy * alpha, a.Z() + dz * alpha);
	}

	/**
	 * Creates a new vector that is the same as this vector
	 * @return The new vector
	 */
	public Vector3 clone()
	{
		return new Vector3(x, y, z);
	}

	/**
	 * Recalculates stored values of the vector
	 */
	private void Recalculate()
	{
		this.sqr_magnitude = x*x + y*y + z*z;
		this.magnitude = (float)Math.sqrt(sqr_magnitude);
	}

	@Override
	public String toString()
	{
		return "Vector3{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				'}';
	}
}
