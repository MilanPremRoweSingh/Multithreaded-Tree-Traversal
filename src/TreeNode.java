
public class TreeNode {
	boolean isLeftChild;
	volatile TreeNode rightChild;
	volatile TreeNode leftChild;
	TreeNode parent;
	float value;
	
	TreeNode( TreeNode _parent, boolean _isLeftChild, float _value )
	{
		parent 		= _parent;
		isLeftChild = _isLeftChild;
		value		= _value;
		
		rightChild 	= null;
		leftChild	= null;
	}
	
	public void addLeftChild( float childValue )
	{
		this.leftChild = new TreeNode( this, true, childValue );
	}
	
	public void addRightChild( float childValue )
	{
		this.rightChild = new TreeNode( this, false, childValue );
	}
	
	public void rmLeftChild()
	{
		leftChild = null;
	}

	public void rmRightChild()
	{
		rightChild = null;
	}
	public TreeNode getLeftChild()
	{
		return leftChild;
	}
	
	public TreeNode getRightChild()
	{
		return rightChild;
	}
	
	public TreeNode getParent()
	{
		return parent;
	}
	
	public float getValue()
	{
		return value;
	}
	
	public boolean isLeftChild()
	{
		return isLeftChild;
	}
	
	public boolean bstAdd( float addVal ) // This function is used to generate a random BST for the purposes of testing
	{
		if ( addVal == value )
			return false; //No dups
		else if( addVal < value )
		{
			if( leftChild == null )
			{
				this.addLeftChild( addVal );
				return true;
			}
			else
				return leftChild.bstAdd( addVal );
		}
		else 
		{
			if( rightChild == null )
			{
				this.addRightChild( addVal );
				return true;
			}
			else
				return rightChild.bstAdd( addVal );
		}
	}
}
