import java.util.Random;

public class InOrderWalkThread extends Thread {
	volatile String output;
	
	InOrderWalkThread()
	{
		super();
		output = "";
	}
	
	public void run() {
		long totalTime = 0l;
		while ( totalTime < 5000l )
		{
			long startTime = System.currentTimeMillis();
			TreeNode curr = TreeTraversal.root;
			
			while ( curr != null )
			{
				TreeNode temp; //Reference left child locally so if deleted  between null check and assign we dont assign curr to null
				while ( true )
				{
					//localSleep(); //Sleep before entering left child
					temp = curr.getLeftChild();
					if ( temp == null )
						break;
					curr = temp;
				}
				
				while ( curr != null )
				{
					output+=  curr.getValue() + " ";

					localSleep(); //Sleep after reading and before entering child/ parent
					//Note my interpretation of the assignment text understands it as asking for a single sleep per value read
					//As opposed to sleeping before each move to a parent / child. The commented out local sleeps are where I would put the sleeps if this were intended
					
					temp = curr.getRightChild(); 
					if ( temp != null )
					{
						curr = temp; //Reference right child locally so if deleted  between null check and assign we dont assign curr to null
						
						while ( true )
						{
							//localSleep(); //Sleep before entering left child
							temp = curr.getLeftChild();
							if ( temp == null )
								break;
							curr = temp;
						}
					}
					else
					{
						//Not vulnerable to changes from other threads as Nodes will keep parent references even after 'deleted' from tree
						while( curr.getParent() != null && !curr.isLeftChild() ) 
						{
							//Sleep before entering parent
							//localSleep();
							curr = curr.getParent();
						}
						
						// Sleep before entering parent
						//localSleep();
						curr = curr.getParent();
					}
				}
			}
			
			output+=   '\n';
			
			totalTime += ( System.currentTimeMillis() - startTime );
		}
		
    }
	
	public void localSleep()
	{
		Random rand = new Random();
		long sleepValue = (long) rand.nextInt( 16 ) + 5l;
		
		try 
		{
			sleep( sleepValue );
		}
		catch ( InterruptedException e )
		{
			System.out.println( "Thread " + this.getId() + " interrupted while sleeping" );
		}
	}

	
}

