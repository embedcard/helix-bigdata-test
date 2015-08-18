package com.helixleisure.candidates.test;

import java.sql.Timestamp;

import com.helixleisure.schema.DataUnit;
import com.helixleisure.schema.GameID;
import com.helixleisure.schema.GamePlayEdge;
import com.helixleisure.schema.GameProperty;
import com.helixleisure.schema.GamePropertyValue;
import com.helixleisure.schema.Location;
import com.helixleisure.schema.LocationID;
import com.helixleisure.schema.LocationProperty;
import com.helixleisure.schema.LocationPropertyValue;
import com.helixleisure.schema.Pedigree;

public class Data {

	public static Pedigree makePedigree(Timestamp timestamp) {
		return new Pedigree(timestamp.toString());
	}

	public static com.helixleisure.schema.Data makeGameplay(int locationId, String gameName, Timestamp time) {
		return new com.helixleisure.schema.Data(makePedigree(time), DataUnit.game_play(new GamePlayEdge(LocationID
				.location_id(locationId), GameID.name(gameName), 1)));

	}

	public static com.helixleisure.schema.Data makeGameProperty(Timestamp time, String name, int gamePlays) {
		return new com.helixleisure.schema.Data(makePedigree(time), DataUnit.game_property(new GameProperty(GameID.name(name),
				GamePropertyValue.game_plays(gamePlays))));
	}

	public static com.helixleisure.schema.Data makeLocationProperty(Timestamp time, long locationId, String locationName) {
		return new com.helixleisure.schema.Data(makePedigree(time), DataUnit.location_property(new LocationProperty(LocationID
				.location_id(locationId), LocationPropertyValue.location_name(locationName))));
	}

	public static com.helixleisure.schema.Data makeLocationProperty(Timestamp time, long locationId, Location location) {
		return new com.helixleisure.schema.Data(makePedigree(time), DataUnit.location_property(new LocationProperty(LocationID
				.location_id(locationId), LocationPropertyValue.location(location))));
	}
}
