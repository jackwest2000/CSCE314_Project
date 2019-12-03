
// class used to store items as pairs. Intended for use in twin primes and hexagon crosses.
public class Pair<T> {
	private T m_left;
	private T m_right;
	
	// constructor taking two items as arguments
	Pair(T l, T r) 
	{
		m_left = l;
		m_right = r;
	}
	
	// return left and right items in the pair respectively
	public T left() { return m_left; }
	public T right() {return m_right; }

}
