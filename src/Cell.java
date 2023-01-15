import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Cell
{
	final int x;
	final int y;

	Map<Wall, Boolean> walls = new HashMap<>( 4 );

	boolean visited = false;

	// -----------------------------------------------------------------------------------------------------------------
	public Cell( int x, int y )
	{
		this.x = x;
		this.y = y;

		walls.put( Wall.TOP, true );
		walls.put( Wall.RIGHT, true );
		walls.put( Wall.BOTTOM, true );
		walls.put( Wall.LEFT, true );
	}

	// -----------------------------------------------------------------------------------------------------------------
	public void draw( Graphics graphics )
	{
		int x = this.x * MazeGenerator.cellSize;
		int y = this.y * MazeGenerator.cellSize;

		if( visited )
		{
			graphics.setColor( Color.BLACK );
			graphics.fillRect( x, y, MazeGenerator.cellSize, MazeGenerator.cellSize );
		}

		if( walls.get( Wall.TOP ) )
		{
			graphics.setColor( Color.ORANGE );
			graphics.drawLine( x, y, x + MazeGenerator.cellSize, y );
		}
		if( walls.get( Wall.RIGHT ) )
		{
			graphics.setColor( Color.ORANGE );
			graphics.drawLine( x + MazeGenerator.cellSize, y, x + MazeGenerator.cellSize, y + MazeGenerator.cellSize );
		}
		if( walls.get( Wall.BOTTOM ) )
		{
			graphics.setColor( Color.ORANGE );
			graphics.drawLine( x + MazeGenerator.cellSize, y + MazeGenerator.cellSize, x, y + MazeGenerator.cellSize );
		}
		if( walls.get( Wall.LEFT ) )
		{
			graphics.setColor( Color.ORANGE );
			graphics.drawLine( x, y + MazeGenerator.cellSize, x, y );
		}
	}

	// -----------------------------------------------------------------------------------------------------------------
	public void drawCurrentCell( Graphics graphics )
	{
		int x = this.x * MazeGenerator.cellSize;
		int y = this.y * MazeGenerator.cellSize;

		graphics.setColor( Color.ORANGE );
		graphics.fillRect( x + 2, y + 2, MazeGenerator.cellSize - 2, MazeGenerator.cellSize - 2 );
	}

	// -----------------------------------------------------------------------------------------------------------------
	Cell checkCell( int x, int y )
	{
		if( x < 0 || x > MazeGenerator.columns - 1 || y < 0 || y > MazeGenerator.rows - 1 )
		{
			return null;
		}

		int findIndex = x + y * MazeGenerator.columns;
		return MazeGenerator.cells.get( findIndex );
	}

	// -----------------------------------------------------------------------------------------------------------------
	Cell checkNeighbors()
	{
		List<Cell> neighbors = new ArrayList<>();

		Cell topCell = checkCell( x, y - 1 );
		if( topCell != null && !topCell.visited )
		{
			neighbors.add( topCell );
		}

		Cell rightCell = checkCell( x + 1, y );
		if( rightCell != null && !rightCell.visited )
		{
			neighbors.add( rightCell );
		}

		Cell bottomCell = checkCell( x, y + 1 );
		if( bottomCell != null && !bottomCell.visited )
		{
			neighbors.add( bottomCell );
		}

		Cell leftCell = checkCell( x - 1, y );
		if( leftCell != null && !leftCell.visited )
		{
			neighbors.add( leftCell );
		}

		if( neighbors.isEmpty() )
		{
			return null;
		}
		else
		{
			int randomIndex = new Random().nextInt( neighbors.size() );
			Cell randomCell = neighbors.get( randomIndex );
			return randomCell;
		}
	}
}
