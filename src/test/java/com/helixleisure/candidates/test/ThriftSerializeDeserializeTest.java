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

import junit.framework.TestCase;

import org.apache.commons.codec.binary.Hex;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TJSONProtocol;
import org.junit.Test;

import com.helixleisure.schema.Data;

/**
 * This test class shows how to serialize and deserialize thrift objects
 * @author Helix Leisure
 */
public class ThriftSerializeDeserializeTest extends TestCase {
	protected TSerializer ser;
	protected TDeserializer des;

	protected void setUp() throws Exception {
		super.setUp();
		ser = new TSerializer();
		des = new TDeserializer();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Shows how to serialize one object to binary format.
	 * @throws Exception
	 */
	@Test
	public void testSerialize() throws Exception {
		Data locationProperty = DataHelper.makeLocationProperty(Timestamp.valueOf("2015-05-01 10:00:00"), 12345L, "Acme Inc.");
		assertEquals("Data(pedigree:Pedigree(timestamp:2015-05-01 10:00:00.0), dataunit:<DataUnit location_property:LocationProperty(id:<LocationID location_id:12345>, property:<LocationPropertyValue location_name:Acme Inc.>)>)",locationProperty.toString());
		String expectedHex = "0c00010b000100000015323031352d30352d30312031303a30303a30302e30000c00020c00010c00010a00010000000000003039000c00020b00010000000941636d6520496e632e00000000";
		byte[] serialize = ser.serialize(locationProperty);
		assertEquals(expectedHex, Hex.encodeHexString(serialize));
	}
	
	/**
	 * Shows how to deserialize one object from binary format.
	 * @throws Exception
	 */
	@Test
	public void testDeserialize() throws Exception {
		String hex = "0c00010b000100000015323031352d30352d30312031303a30303a30302e30000c00020c00010c00010a00010000000000003039000c00020b00010000000941636d6520496e632e00000000";
		byte[] bytes = Hex.decodeHex(hex.toCharArray());
		// create an empty object for deserialization
		Data data = new Data();
		des.deserialize(data, bytes);
		assertEquals("Data(pedigree:Pedigree(timestamp:2015-05-01 10:00:00.0), dataunit:<DataUnit location_property:LocationProperty(id:<LocationID location_id:12345>, property:<LocationPropertyValue location_name:Acme Inc.>)>)",data.toString());
	}
	
	/**
	 * Shows how to serialize one object as a JSON Thrift format.
	 * @throws Exception
	 */
	@Test
	public void testSerializeAsJSON() throws Exception {
		ser = new TSerializer(new TJSONProtocol.Factory());
		Data data = DataHelper.makeGameplay(12345L, "Snapshot", Timestamp.valueOf("2015-06-01 12:34:12"));
		assertEquals("Data(pedigree:Pedigree(timestamp:2015-06-01 12:34:12.0), dataunit:<DataUnit game_play:GamePlayEdge(location:<LocationID location_id:12345>, game:<GameID name:Snapshot>, nonce:1)>)",data.toString());
		String expected = "{\"1\":{\"rec\":{\"1\":{\"str\":\"2015-06-01 12:34:12.0\"}}},\"2\":{\"rec\":{\"3\":{\"rec\":{\"1\":{\"rec\":{\"1\":{\"i64\":12345}}},\"2\":{\"rec\":{\"1\":{\"str\":\"Snapshot\"}}},\"3\":{\"i64\":1}}}}}}";
		String serialize = new String(ser.serialize(data));
		assertEquals(expected, serialize);
	}
	
	/**
	 * Shows how to deserialize one object from a JSON Thrift format.
	 * @throws Exception
	 */
	@Test
	public void testDeserializeAsJSON() throws Exception {
		des = new TDeserializer(new TJSONProtocol.Factory());
		String json = "{\"1\":{\"rec\":{\"1\":{\"str\":\"2015-06-01 12:34:12.0\"}}},\"2\":{\"rec\":{\"3\":{\"rec\":{\"1\":{\"rec\":{\"1\":{\"i64\":12345}}},\"2\":{\"rec\":{\"1\":{\"str\":\"Snapshot\"}}},\"3\":{\"i64\":1}}}}}}";
		Data data = new Data();
		des.deserialize(data, json.getBytes());
		assertEquals("Data(pedigree:Pedigree(timestamp:2015-06-01 12:34:12.0), dataunit:<DataUnit game_play:GamePlayEdge(location:<LocationID location_id:12345>, game:<GameID name:Snapshot>, nonce:1)>)",data.toString());
	}
}
