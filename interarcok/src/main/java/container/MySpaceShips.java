package container;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MySpaceShips extends Owner
{
	private class ControllableSpaceShip extends SpaceShip
	{
		public ControllableSpaceShip( JSONObject whereIs ) throws JSONException
		{
			super(MySpaceShips.this, whereIs);
		}
	}

	ArrayList<ControllableSpaceShip> myShips = new ArrayList<ControllableSpaceShip>();
	Integer remainingMines;

	public MySpaceShips( JSONObject job ) throws JSONException
	{
		name = job.getString("name");
		remainingMines = job.getInt("remainingMines");

		JSONArray pls = job.getJSONArray("ships");
		for( int i = 0; i < pls.length() ; ++i )
		{
			JSONObject sps = pls.getJSONObject(i);

			myShips.add(new ControllableSpaceShip(sps));
		}

		Galaxy.teams.put(name, this);
	}

	@Override
	boolean areWe()
	{
		return true;
	}

	@Override
	ArrayList<SpaceShip> ships()
	{
		return new ArrayList<SpaceShip>(myShips);
	}
}