import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

/**
 * Используется алгоритм Recursive Backtracker
 */
@SuppressWarnings("serial")
public class MazeGenerator extends JFrame
{
	private static final int windowWidth = 1600;
	private static final int windowHeigth = 1000;

	public static final int cellSize = 100;

	public static final int columns = windowWidth / cellSize;
	public static final int rows = windowHeigth / cellSize;

	public static List<Cell> cells = new ArrayList<Cell>( columns * rows );
	public static List<Cell> visitedCells = new ArrayList<>();
	Cell currentCell;

	boolean isDone = false;

	// -----------------------------------------------------------------------------------------------------------------
	public static void main( String[] args )
	{
		EventQueue.invokeLater( () -> new MazeGenerator().setVisible( true ) );
	}

	// -----------------------------------------------------------------------------------------------------------------
	public MazeGenerator()
	{
		super( "Генератор лабиринта" );

		setBounds( 100, 50, 1600, 900 );
		setMinimumSize( new Dimension( windowWidth, windowHeigth ) );
		setDefaultCloseOperation( EXIT_ON_CLOSE );

		for( int row = 0; row < rows; row++ )
		{
			for( int column = 0; column < columns; column++ )
			{
				cells.add( new Cell( column, row ) );
			}
		}
		currentCell = cells.get( 0 );
	}

	// -----------------------------------------------------------------------------------------------------------------
	@Override
	public void paint( Graphics graphics )
	{
		if( isDone )
		{
			return;
		}

		repaint();
		sleep( 200 ); // задержка для нормальной видимости. иначе все отрисуется быстро, а так не интересно))

		graphics.setColor( Color.GRAY );
		graphics.fillRect( 0, 0, this.getWidth(), this.getHeight() );

		for( Cell cell : cells )
		{
			cell.draw( graphics );
		}

		currentCell.visited = true;
		currentCell.drawCurrentCell( graphics );

		Cell nextCell = currentCell.checkNeighbors();
		if( nextCell != null )
		{
			nextCell.visited = true;
			visitedCells.add( currentCell );
			removeWalls( currentCell, nextCell );
			currentCell = nextCell;
		}
		else if( visitedCells.isEmpty() )
		{
			isDone = true;
		}
		else
		{
			currentCell = visitedCells.remove( visitedCells.size() - 1 );
		}

	}

	// -----------------------------------------------------------------------------------------------------------------
	void removeWalls( Cell current, Cell next )
	{
		int dx = current.x - next.x;
		if( dx == 1 )
		{
			current.walls.replace( Wall.LEFT, false );
			next.walls.replace( Wall.RIGHT, false );
		}
		if( dx == -1 )
		{
			current.walls.replace( Wall.RIGHT, false );
			next.walls.replace( Wall.LEFT, false );
		}

		int dy = current.y - next.y;
		if( dy == 1 )
		{
			current.walls.replace( Wall.TOP, false );
			next.walls.replace( Wall.BOTTOM, false );
		}
		if( dy == -1 )
		{
			current.walls.replace( Wall.BOTTOM, false );
			next.walls.replace( Wall.TOP, false );
		}
	}

	// -----------------------------------------------------------------------------------------------------------------
	void sleep( long ms )
	{
		try
		{

			Thread.sleep( ms );
		}
		catch( InterruptedException e )
		{

		}
	}
}
