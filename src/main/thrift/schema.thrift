namespace java com.helixleisure.schema

union LocationID {
	1: i64 location_id;
}

union GameID {
	1: string name;
}

struct Location {
	1: optional string city;
	2: optional string country; 
}

union LocationPropertyValue {
	1: string location_name;
	2: Location location;
}

struct LocationProperty {
	1: required LocationID id;
	2: required LocationPropertyValue property;
}

union GamePropertyValue {
	1: i32 game_plays;
}

struct GameProperty {
	1: required GameID id;
	2: required GamePropertyValue property;
}

struct GamePlayEdge {
	1: required LocationID location;
	2: required GameID game;
	3: required i64 nonce;
}

struct Pedigree {
	// timestamp of the event in yyyy-MM-dd-HH.mm.ss.nnnnnn
	1: required string timestamp;
}

union DataUnit {
	1: LocationProperty location_property;
	2: GameProperty game_property;
	3: GamePlayEdge game_play;
}

struct Data {
	1: required Pedigree pedigree;
	2: required DataUnit dataunit;
}