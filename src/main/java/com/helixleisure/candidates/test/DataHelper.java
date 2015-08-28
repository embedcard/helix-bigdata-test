/*
 * Copyright 2015 Helix Leisure Pte. Ltd. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helixleisure.candidates.test;

import java.sql.Timestamp;

import com.helixleisure.schema.Data;
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

/**
 *
 * Helper class for making access to the thrift objects easier.
 * 
 * @author Helix Leisure Pte. Ltd.
 */
public class DataHelper {

	/**
	 * Generates a {@link Pedigree} object with the given {@link Timestamp}.
	 * @param timestamp
	 * @return {@link Pedigree}
	 */
	public static Pedigree makePedigree(Timestamp timestamp) {
		return new Pedigree(timestamp.toString());
	}

	/**
	 * Generates one game play with the given parameter.
	 * @param locationId
	 * @param gameName
	 * @param time
	 * @return {@link Data} Object with a {@link GamePlayEdge}
	 */
	public static Data makeGameplay(long locationId, String gameName, Timestamp time) {
		return new Data(makePedigree(time), DataUnit.game_play(new GamePlayEdge(LocationID
				.location_id(locationId), GameID.name(gameName), 1)));

	}

	/**
	 * Generates a game property with the given parameter.
	 * @param time
	 * @param name
	 * @param gamePlays
	 * @return {@link Data} object with a {@link GameProperty}
	 */
	public static Data makeGameProperty(Timestamp time, String name, int gamePlays) {
		return new Data(makePedigree(time), DataUnit.game_property(new GameProperty(GameID.name(name),
				GamePropertyValue.game_plays(gamePlays))));
	}

	/**
	 * Generates a location property with the given parameter.
	 * @param time
	 * @param locationId
	 * @param locationName
	 * @return {@link Data} object with a {@link LocationProperty}
	 */
	public static Data makeLocationProperty(Timestamp time, long locationId, String locationName) {
		return new Data(makePedigree(time), DataUnit.location_property(new LocationProperty(LocationID
				.location_id(locationId), LocationPropertyValue.location_name(locationName))));
	}

	/**
	 * Generates a location property with the given parameter.
	 * @param time
	 * @param locationId
	 * @param location
	 * @return {@link Data} object with a {@link LocationProperty}
	 */
	public static Data makeLocationProperty(Timestamp time, long locationId, Location location) {
		return new Data(makePedigree(time), DataUnit.location_property(new LocationProperty(LocationID
				.location_id(locationId), LocationPropertyValue.location(location))));
	}
}
