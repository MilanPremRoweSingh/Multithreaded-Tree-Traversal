
import java.util.Random;

public class TreeTraversal {
	static TreeNode root;
	
	public static void main(String[] args) 
	{
		
		Random rand = new Random();
		
		int halfBSTSize = 100;
		int bstMid		= 100;
		TreeNode bstRoot = new TreeNode( null, true, bstMid );
		
		for ( int i = 0; i < halfBSTSize; i++ )
		{
			bstRoot.bstAdd( rand.nextInt( bstMid ) );
			bstRoot.bstAdd( rand.nextInt( bstMid*2 ) + bstMid);
		}

		root = bstRoot;
		
		InOrderWalkThread readThread0 = new InOrderWalkThread();
		readThread0.start();
		
		InOrderWalkThread readThread1 = new InOrderWalkThread();
		readThread1.start();
		
		modifyRoot();
		
		
		try 
		{
			readThread0.join();
		}
		catch( InterruptedException e )
		{
			System.out.println( "Read Thread 0 was interrupted" );
		}
		
		try 
		{
			readThread1.join();
		}
		catch( InterruptedException e )
		{
			System.out.println( "Read Thread 0 was interrupted" );
		}
		System.out.println( "A:\n" + readThread0.output );
		System.out.println( "B:\n" + readThread1.output );
		
		
	}

	public static void modifyRoot()
	{
		Random rand = new Random();
		long totalTime = 0l;

		TreeNode curr = root;
		float pred, succ;
		
		boolean firstLoop = true;
		while ( totalTime < 5000l )
		{
			long startTime = System.currentTimeMillis();
			curr = root;
			
			if ( !firstLoop )
			{
				long sleepValue = (long) rand.nextInt( 5 ) + 1;
				
				try 
				{
					Thread.sleep( sleepValue );
				}
				catch ( InterruptedException e )
				{
					System.out.println( "Main Thread interrupted while sleeping" );
				}
			}
			firstLoop = false;
			
			pred = getPred( curr );
			succ = getSucc( curr );
						
			while( true ) //Loop until an action happens
			{
				//Decide action
				int randVal = rand.nextInt( 10 ) + 1;
				
				if( ( curr.getLeftChild() != null ) && ( randVal == 1) ) //1/10 chance for left deletion
				{
					//LEFT DELETION
					curr.rmLeftChild();
					break;
				}
				
				if( ( curr.getRightChild() != null ) && ( randVal == 2) ) //1/10 chance for left deletion
				{
					//RIGHT DELETION
					curr.rmRightChild();
					break;
				}
				
				if( ( curr.getLeftChild() == null ) && ( randVal >= 3 && randVal <= 6 ) ) //4/10 chance for left creation
				{
					//LEFT CREATION
					curr.addLeftChild( ( pred + curr.getValue() ) / 2.0f); //Create new left child with value between pred and curr
					break;
				}
				
				if( ( curr.getRightChild() == null ) && ( randVal >= 7 && randVal <= 10 ) ) //4/10 chance for left creation
				{
					//RIGHT CREATION
					curr.addRightChild( ( succ + curr.getValue() ) / 2.0f); //Create new left child with value between pred and curr
					break;
				}
				
				if ( ( curr.getLeftChild() != null ) && ( curr.getRightChild() != null ) ) 
				{
					boolean goLeft = rand.nextBoolean(); //Flip coin
					if ( goLeft )
					{
						curr = curr.getLeftChild();
						pred = getPred( curr );
						succ = getSucc( curr );
					}
					else 
					{
						curr = curr.getRightChild();
						pred = getPred( curr );
						succ = getSucc( curr );
					}
				}
				else if( curr.getLeftChild() != null )
				{
					curr = curr.getLeftChild();
					pred = getPred( curr );
					succ = getSucc( curr );
				}
				else if( curr.getRightChild() != null )
				{
					curr = curr.getRightChild();
					pred = getPred( curr );
					succ = getSucc( curr );
				}
				else
				{
					//REACHED BOTTOM
					break;
				}
			}
			totalTime += ( System.currentTimeMillis() - startTime );
		}
		
	}
	
	public static float getPred( TreeNode node )
	{
		//to get pred get the value of the rightmost child of left child
		float pred;
		TreeNode temp = node;
	
		while( node.isLeftChild() )
		{	
			if ( node.getParent() == null )
				break;
			node = node.getParent();
		}
		if ( node.getParent() != null )
			pred = node.getParent().getValue();
		else
			pred = temp.getValue() - 1.0f;

		return pred;
	}
	
	public static float getSucc( TreeNode node )
	{
		//to get pred get the value of the leftmost child of right child
		float succ;
		TreeNode temp = node;
		
		while( !node.isLeftChild() )
		{	
			if ( node.getParent() == null )
				break;
			node = node.getParent();
		}
		if ( node.getParent() != null )
			succ = node.getParent().getValue();
		else
			succ = temp.getValue() + 1.0f;
		
		return succ;
	}

}
