package container;

import java.awt.geom.Point2D;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Planet
{
	public AtomicBoolean hasMine = new AtomicBoolean(false);
	public String claim = null;
	public CopyOnWriteArrayList<Integer> pkgs = new CopyOnWriteArrayList<Integer>();

	public Planet(JSONObject planet) throws JSONException
	{
		name = planet.getString("name");

		coord = new Point2D.Double(
				planet.getDouble("x"),
				planet.getDouble("y"));

		JSONArray pks = planet.optJSONArray("packages");
		if(pks != null)
		{
			for( int j = 0; j < pks.length() ; ++j )
			{
				JSONObject pack = pks.getJSONObject(j);

				Package p = new Package(pack, false);

				pkgs.add(p.packageId);

				if(p.lastOwnerName != null)
				{
					ownerName = p.lastOwnerName;
				}
			}
		}
		Galaxy.planets.put(name, this);
	}


	public final String name;
	final Point2D coord;
	public String ownerName;

	public Double distance(Planet p2)
	{
		return coord.distance(p2.coord);
	}

	public String getName()
	{
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !(obj instanceof Planet))
			return false;

		Planet other = (Planet) obj;
		if (!Objects.equals(name, other.name))
			return false;

		return true;
	}

	/*public void checkOwner()
	{
		owned = null;
		for(final Package p : Galaxy.packages.values())
		{
			if(p.lastPlanet.equals(this) && !p.isMoveing.get())
			{
				owned = p.lastOwner;
				break;
			}
		}
	}*/
}
