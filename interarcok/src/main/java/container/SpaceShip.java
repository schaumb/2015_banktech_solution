package container;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import container.Galaxy.Package;
import container.Galaxy.Planet;

public class SpaceShip
{
	public String userName;
	public Planet planet;
	public Planet targetPlanet;
	public Integer arrivesAfterMs;
	public ArrayList<Package> packages = new ArrayList<Package>();

	public SpaceShip(HashMap<String,Planet> planets, JSONObject whereIs)
	{
		try
		{
			userName = whereIs.getString("userName");
			arrivesAfterMs = whereIs.optInt("arrivesAfterMs", 0);

			String tmp;

			tmp = whereIs.getString("planetName");
			if( tmp != null && !tmp.equals("null") )
			{
				System.out.println(whereIs);
				planet = planets.get(tmp);
			}
			else
			{
				planet = null;
			}

			tmp = whereIs.getString("targetPlanetName");
			if( tmp != null && !tmp.equals("null") )
			{
				targetPlanet = planets.get(tmp);
			}
			else
			{
				targetPlanet = null;
			}

			JSONArray pks = whereIs.getJSONArray("packages");
			for( int j = 0; j < pks.length() ; ++j )
			{
				JSONObject pack = pks.getJSONObject(j);
				Package newPackage = new Package(pack);

				newPackage.origin = planets.get(newPackage.origin_s);
				newPackage.target = planets.get(newPackage.target_s);
				packages.add(newPackage);
			}
		}
		catch(JSONException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
}
