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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Random;

import org.apache.thrift.TSerializer;

import com.helixleisure.schema.Location;

import static com.helixleisure.candidates.test.DataHelper.*;

/**
 * 
 * This class generates some sample data and put them into '/tmp/candidates/' folder.
 * The output is in binary format.
 * 
 * @author Helix Leisure Pte. Ltd.
 */
public class SampleDataGenerator {
	public static final String ROOT_FOLDER = "/tmp/candidates/";

	public static TSerializer ser;
	private static final Random R = new Random();
	private static long offset = Timestamp.valueOf("2015-06-01 8:00:00").getTime();
	private static long end = Timestamp.valueOf("2015-08-01 00:00:00").getTime();
	private static long diff = end - offset + 1;
	
	
	public static final String[] LOCATIONS = new String[] {
		"Fun Zone",
		"Party World",
		"Play Land"
	};
	
	public static final String[] LOCATION_CITY = new String[] {
		"Perth",
		"Singapore",
		"Dubai"
	};
	
	public static final String[] LOCATION_COUNTRY = new String[] {
		"Australia",
		"Singapore",
		"United Arab Emirates"
	};
	
	public static final String[] GAMES = new String[] {
		"Snapshot",
		"Speed of Light",
		"Color Match",
		"Pinata",
		"High Five",
		"The Vault",
		"Shooting Mania",
		"Mega Spin",
		"Stacker",
		"Balloon Buster"
	};

	private static File ROOT_FILE;
	
	/**
	 * Create and returns the Thrift {@link TSerializer} which is used for serializing the Thrift objects.
	 * @return
	 */
	public static TSerializer getSerializer() {
		if (ser == null) ser = new TSerializer();
		return ser;
	}
	
	/**
	 * Generates a random timestamp
	 * @return
	 */
	public static Timestamp generateRandomTimeStamp() {
		Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));
		return rand;
	}
	
	/**
	 * Removes all special characters to use the string as a file name.
	 * @param s
	 * @return
	 */
	public static String getAsFileSystemString(String s) {
		String result = s.replaceAll("\\:", "").replaceAll(" ", "").replaceAll("\\.", "");
		return result;
	}
	
	/**
	 * Generates some test data for each location including games and game plays and writes them into a file
	 * @throws Exception
	 */
	public static void initTestData() throws Exception {
		// creating the folder
		ROOT_FILE = new File(ROOT_FOLDER);
		ROOT_FILE.mkdirs();
		// getting the thrift serializer
		TSerializer ser = getSerializer();
		// iterate over all locations
		for (int location=0;location<LOCATIONS.length;location++) {
			// using an initial timestamp for storing the properties for the location
			Timestamp timestamp = Timestamp.valueOf("2015-05-01 00:00:00");
			// creating the location properties
			write(ser.serialize(makeLocationProperty(timestamp, location, LOCATIONS[location])),getAsFileSystemString("property_location_"+location+"_name_time_"+timestamp));
			Location locationInfo = new Location();
			locationInfo.setCity(LOCATION_CITY[location]);
			locationInfo.setCountry(LOCATION_COUNTRY[location]);
			write(ser.serialize(makeLocationProperty(timestamp, location, locationInfo)),getAsFileSystemString("property_location_"+location+"_location_time_"+timestamp));
			// generate some game plays for each location
			for (int i=0;i<1000;i++) {
				// now using a random time stamp to represent the actual events
				timestamp = generateRandomTimeStamp();
				String game = GAMES[R.nextInt(GAMES.length)];
				write(ser.serialize(makeGameplay(location, game, timestamp)),getAsFileSystemString("edge_gameplay_location_"+location+"_game_"+game+"_time_"+timestamp));
			}
		}
	}
	
	/**
	 * Writes the actual bytes to the file system
	 * @param bytes
	 * @param name
	 */
	public static void write(byte[] bytes, String name) {
		File f = new File(ROOT_FILE,name);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
			fos.write(bytes);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {}
			}
		}
	}
	
	/**
	 * Main method
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		initTestData();
	}
}
