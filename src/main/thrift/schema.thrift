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
namespace java com.helixleisure.schema

/**
* Represents a Location Node (e.g. a physical store with arcade games) 
*/
union LocationID {
	1: i64 location_id;
}

/**
* Represents a Game Node (e.g. Snapshot)
*/
union GameID {
	1: string name;
}

/**
* Represents locational information for a specific location. It's being used in the LocationPropertyValue.
*/
struct Location {
	1: optional string city;
	2: optional string country; 
}

/**
* Represents the location property values for a specific location.
*/
union LocationPropertyValue {
	1: string location_name;
	2: Location location;
}

/**
* Represents the location property for a specific location.
*/
struct LocationProperty {
	1: required LocationID id;
	2: required LocationPropertyValue property;
}

/**
* Represents game property values for a game.
*/
union GamePropertyValue {
	1: i32 game_plays;
}

/**
* Represents the game property for a specific game.
*/
struct GameProperty {
	1: required GameID id;
	2: required GamePropertyValue property;
}

/**
* Represents the game play edge. Used to make a relationship between game and location
*/
struct GamePlayEdge {
	1: required LocationID location;
	2: required GameID game;
	3: required i64 nonce;
}

/**
* Represents additional information about the event like a timestamp.
*/
struct Pedigree {
	// timestamp of the event in yyyy-MM-dd-HH.mm.ss.nnnnnn
	1: required string timestamp;
}

/**
* Represents the all possible objects in the event. As this is a union exactly one MUST be set.
*/
union DataUnit {
	1: LocationProperty location_property;
	2: GameProperty game_property;
	3: GamePlayEdge game_play;
}

/**
* Represents the overall structure of the event. This is the starting point for every event.
*/
struct Data {
	1: required Pedigree pedigree;
	2: required DataUnit dataunit;
}