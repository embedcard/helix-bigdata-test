package com.helixleisure.candidates.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Random;

import org.apache.thrift.TSerializer;

import com.helixleisure.schema.Location;

import static com.helixleisure.candidates.test.Data.*;

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
	
	public static TSerializer getSerializer() {
		if (ser == null) ser = new TSerializer();
		return ser;
	}
	
	public static Timestamp generateRandomTimeStamp() {
		Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));
		return rand;
	}
	
	public static String getAsFileSystemString(String s) {
		String result = s.replaceAll("\\:", "").replaceAll(" ", "").replaceAll("\\.", "");
		return result;
	}
	
	
	public static void initTestData() throws Exception {
		ROOT_FILE = new File(ROOT_FOLDER);
		ROOT_FILE.mkdirs();
		TSerializer ser = getSerializer();
		for (int location=0;location<LOCATIONS.length;location++) {
			Timestamp timestamp = Timestamp.valueOf("2015-05-01 00:00:00");
			write(ser.serialize(makeLocationProperty(timestamp, location, LOCATIONS[location])),getAsFileSystemString("property_location_"+location+"_name_time_"+timestamp));
			Location locationInfo = new Location();
			locationInfo.setCity(LOCATION_CITY[location]);
			locationInfo.setCountry(LOCATION_COUNTRY[location]);
			write(ser.serialize(makeLocationProperty(timestamp, location, locationInfo)),getAsFileSystemString("property_location_"+location+"_location_time_"+timestamp));
			
			for (int i=0;i<10000;i++) {
				timestamp = generateRandomTimeStamp();
				String game = GAMES[R.nextInt(GAMES.length)];
				write(ser.serialize(makeGameplay(location, game, timestamp)),getAsFileSystemString("edge_gameplay_location_"+location+"_game_"+game+"_time_"+timestamp));
			}
		}
	}
	
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
	
	public static void main(String[] args) throws Exception {
		initTestData();
	}
}
