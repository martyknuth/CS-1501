public class FreqNode
{
	private FreqNode child;
	private FreqNode sibling;
	private char c;
	private int frequency;
	public FreqNode()
	{
		setChild(null);
		setSibling(null);
	}
	
	public FreqNode(FreqNode child,FreqNode sibling,char c)
	{
		setChild(child);
		setSibling(sibling);
		setChar(c);
	}
	public void setChild(FreqNode child)
	{
		this.child = child;
	}
	public void setSibling(FreqNode sibling)
	{
		this.sibling = sibling;
	}
	public void setChar(char c)
	{
		this.c = c;
	}
	public void increment()
	{
		frequency++;
	}
	public FreqNode getChild()
	{
		return child;
	}
	public FreqNode getSibling()
	{
		return sibling;
	}
	public char getC()
	{
		return c;
	}
	public int getFreq()
	{
		return frequency;
	}
}