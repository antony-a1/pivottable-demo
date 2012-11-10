/* DummyData.java

{{IS_NOTE
 Purpose:
  
 Description:
  
 History:
  May 7, 2012 3:37:39 PM , Created by simonpai
}}IS_NOTE

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.pivot.demo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Dummy data generation utilities.
 * @author simonpai
 */
public class DummyData {
	
	/**
	 * Randomly pick an entry from source data array.
	 */
	public static <T> T getRandomEntry(T[] source, Random rand) {
		return getRandomEntry(source, rand, 0);
	}
	
	/**
	 * Randomly pick an entry from source data array, with a given change of 
	 * receiving null value.
	 */
	public static <T> T getRandomEntry(T[] source, Random rand, double nullRate) {
		return roll(rand, nullRate) ? null : source[randInt(rand, source.length)];
	}
	
	
	
	public Iterable<Object[]> generate(final int size, 
			final DataGenerator<?> ... generators) {
		final int colsize = generators.length;
		return new java.util.AbstractList<Object[]>() {
			public Object[] get(int index) {
				Object[] objs = new Object[colsize];
				for (int j = 0; j < colsize; j++)
					objs[j] = generators[j].getData(null);
				return objs;
			}
			public int size() {
				return size;
			}
		};
	}
	
	
	
	// skeleton implementation //
	public interface DataGenerator<T> {
		public T getData(Random rand);
	}
	
	private static class SimpleDataGenerator<T> implements DataGenerator<T> {
		private final T[] _samples;
		private final double _nullRate;
		
		private SimpleDataGenerator(T[] samples, double nullRate) {
			_samples = samples;
			_nullRate = nullRate;
		}
		
		public T getData(Random rand) {
			return DummyData.getRandomEntry(_samples, rand, _nullRate);
		}
	}
	
	private static class SimpleStringGenerator extends SimpleDataGenerator<String> {
		private final boolean _quoted;
		
		private SimpleStringGenerator(String[] samples, double nullRate, boolean qouted) {
			super(samples, nullRate);
			_quoted = qouted;
		}
		@Override
		public String getData(Random rand) {
			return _quoted ? "'" + super.getData(rand) + "'" : super.getData(rand);
		}
	}
	
	private static class PrePostStringGenerator implements DataGenerator<String> {
		private final String _prefix, _postfix;
		private final long _size;
		private final double _nullRate;
		
		private PrePostStringGenerator(String prefix, String postfix, long size, double nullRate) {
			_prefix = prefix;
			_postfix = postfix;
			_size = size;
			_nullRate = nullRate;
		}
		public String getData(Random rand) {
			return roll(rand, _nullRate) ? null : ((_prefix == null ? "" : _prefix) + 
					getSerial(rand) + (_postfix == null ? "" : _postfix));
		}
		protected long getSerial(Random rand) {
			return randLong(rand, _size);
		}
	}
	
	private static class RangedIntegerGenerator implements DataGenerator<Integer> {
		private final int _min, _max;
		private final double _nullRate;
		
		private RangedIntegerGenerator(int min, int max, double nullRate) {
			_min = min;
			_max = max;
			_nullRate = nullRate;
		}
		public Integer getData(Random rand) {
			return roll(rand, _nullRate) ? null : (randInt(rand, _max - _min) + _min);
		}
	}
	private static class RangedDoubleGenerator implements DataGenerator<Double> {
		private final long _min, _max;
		private final int _divisor;
		private final double _nullRate;
		
		private RangedDoubleGenerator(long min, long max, int divisor, double nullRate) {
			if (divisor == 0)
				throw new IllegalArgumentException("Divisor cannot be 0.");
			_min = min;
			_max = max;
			_divisor = divisor;
			_nullRate = nullRate;
		}
		public Double getData(Random rand) {
			return roll(rand, _nullRate) ? null : 
				((double) (randLong(rand, _max - _min) + _min)) / _divisor;
		}
	}
	
	public static Iterable<List<Object>> iterable(long size, long seed) {
		return new RandomObjectListIterable(size, seed);
	}
	
	private static class RandomObjectListIterator extends 
			RandomDataIterator<List<Object>> {
		public RandomObjectListIterator(long size, long seed) {
			super(size, seed);
		}
		@Override
		protected List<Object> getData(Random random) {
			List<Object> list = new ArrayList<Object>(COLUMNS.length);
			for (DataGenerator<?> gen : DATA_GENERATORS)
				list.add(gen.getData(random));
			return list;
		}
	}
	
	private static class RandomObjectListIterable implements Iterable<List<Object>> {
		private final long _size, _seed;
		private RandomObjectListIterable(long size, long seed) {
			_size = size;
			_seed = seed;
		}
		public Iterator<List<Object>> iterator() {
			return new RandomObjectListIterator(_size, _seed);
		}
	}
	
	
	
	// helper //
	private static boolean roll(Random rand, double rate) {
		return rate > 0 && (rand == null ? Math.random() : rand.nextDouble()) < rate;
	}
	
	private static int randInt(Random rand, int limit) {
		return rand == null ? (int) (Math.random() * limit) : rand.nextInt(limit);
	}
	
	private static long randLong(Random rand, long limit) {
		return (long) (limit * (rand == null ? Math.random() : rand.nextDouble()));
	}
	
	// predefined data //
	public final static String[] DAYS_OF_WEEK = {
		"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
	};
	
	public final static String[] ZODIAC_SIGNS = {
		"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo",
		"Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"
	};
	
	public final static String[] FIRST_NAMES = {
		"Cornell", "Sam", "Allan", "Don", "Mariano", 
		"Jermaine", "Joel", "Dorian", "Russel", "Teodoro", 
		"James", "Rolando", "Marion", "Larry", "Boyce", 
		"Doyle", "Stuart", "Roderick", "Dino", "Davis", 
		"Benton", "Abram", "Warner", "Evan", "Valentine", 
		"Luciano", "Lucius", "Luigi", "Randal", "Donnell", 
		"Herb", "Danny", "Russell", "Terrance", "Forrest", 
		"Jessie", "Numbers", "William", "Abe", "Rene", 
		"Lenny", "Dirk", "Everett", "Erik", "Adolfo", 
		"Kim", "Ken", "Michael", "Denny", "Reynaldo",
		"Chandra", "Necole", "Francene", "Corinna", "Gaye", 
		"Lynelle", "Jennifer", "Nanette", "Chieko", "Loriann", 
		"Shin", "Shondra", "Jacquelyne", "Tegan", "Vernell", 
		"Miss", "Annalee", "Kala", "Hedwig", "Kari", 
		"Richelle", "Nakisha", "Diana", "Claudine", "Angelique", 
		"Alison", "Kemberly", "Katherina", "Lavone", "Ariane", 
		"Drucilla", "Yi", "Illa", "Marietta", "Yetta", 
		"Rhonda", "Kimberlee", "Angelita", "Bambi", "Terisa", 
		"Alyse", "Rubie", "Doria", "Melisa", "Yasuko", 
		"Geraldine", "Michell", "Mirtha", "Racquel", "Lizabeth"
	};
	
	public final static String[] US_STATES = {
		"Alabama", "Alaska", "Arizona", "Arkansas", "California",
		"Colorado", "Connecticut", "Delaware", "Florida", "Georgia",
		"Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
		"Kansas", "Kentucky", "Louisiana", "Maine", "Maryland",
		"Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri",
		"Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey",
		"New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
		"Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
		"South Dakota", "Tennessee", "Texas", "Utah", "Vermont",
		"Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"
	};
	
	public final static String[] COUNTRIES = {
		"Afghanistan", "Albania", "Algeria", "Andorra", "Angola", 
		"Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", 
		"Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", 
		"Belarus", "Belgium", "Belize", "Benin", "Bhutan", 
		"Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", 
		"Bulgaria", "Burkina_Faso", "Burma", "Burundi", "Cambodia", 
		"Cameroon", "Canada", "Cape Verde", "Central African Republic", "Chad", 
		"Chile", "China", "Colombia", "Comoros", "Congo-Kinshasa", 
		"Congo-Brazzaville", "Costa Rica", "Ivory Coast", "Croatia", "Cuba", 
		"Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", 
		"Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", 
		"Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", 
		"Finland", "France", "Gabon", "Gambia", "Georgia", 
		"Germany", "Ghana", "Greece", "Grenada", "Guatemala", 
		"Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", 
		"Hungary", "Iceland", "India", "Indonesia", "Iran", 
		"Iraq", "Ireland", "Israel", "Italy", "Jamaica", 
		"Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", 
		"North Korea", "South Korea", "Kuwait", "Kyrgyzstan", "Laos", 
		"Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", 
		"Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", 
		"Malawi", "Malaysia", "Maldives", "Mali", "Malta", 
		"Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", 
		"Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", 
		"Mozambique", "Namibia", "Nauru", "Nepal", "Netherlands", 
		"New Zealand", "Nicaragua", "Niger", "Nigeria", "Norway", 
		"Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", 
		"Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", 
		"Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", 
		"Saint Vincent and the Grenadines", "Samoa", "San Marino", "São Tomé and Príncipe", "Saudi Arabia", 
		"Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", 
		"Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", 
		"South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", 
		"Swaziland", "Sweden", "Switzerland", "Syria", "Tajikistan", 
		"Tanzania", "Thailand", "Togo", "Tonga", "Trinidad and Tobago", 
		"Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", 
		"Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", 
		"Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", 
		"Yemen", "Zambia", "Zimbabwe", "Abkhazia", "Azawad", 
		"Cook Islands", "Kosovo", "Nagorno-Karabakh", "Niue", "Northern Cyprus", 
		"Palestine", "SADR", "Somaliland", "South Ossetia", "Taiwan", 
		"Transnistria"
	};
	
	public static final DataGenerator<?>[] DATA_GENERATORS = new DataGenerator<?>[]{
		new SimpleDataGenerator<String>(DummyData.COUNTRIES, 0.05),
		new SimpleDataGenerator<String>(DummyData.US_STATES, 0.05),
		new SimpleDataGenerator<String>(DummyData.FIRST_NAMES, 0.05),
		
		new SimpleDataGenerator<String>(DummyData.ZODIAC_SIGNS, 0.05),
		new SimpleDataGenerator<String>(DummyData.DAYS_OF_WEEK, 0.05),
		
		new PrePostStringGenerator("Sparse-", "", 100000, 0.05),
		new PrePostStringGenerator("Normal-", "", 1000, 0.05),
		new PrePostStringGenerator("Dense-", "", 10, 0.05),
		
		new RangedIntegerGenerator(-500000, 500000, 0.05),
		new RangedIntegerGenerator(-500, 500, 0.05),
		new RangedDoubleGenerator(-500000, 500000, 100, 0.05),
		new RangedDoubleGenerator(-5000, 5000, 100, 0.05)
	};
	
	public static final DataGenerator<?>[] DB_DATA_GENERATORS = new DataGenerator<?>[]{
		new SimpleStringGenerator(DummyData.COUNTRIES, 0.05, true),
		new SimpleStringGenerator(DummyData.US_STATES, 0.05, true),
		new SimpleStringGenerator(DummyData.FIRST_NAMES, 0.05, true),
		
		new SimpleStringGenerator(DummyData.ZODIAC_SIGNS, 0.05, true),
		new SimpleStringGenerator(DummyData.DAYS_OF_WEEK, 0.05, true),
		
		new PrePostStringGenerator("'Sparse-", "'", 100000, 0.05),
		new PrePostStringGenerator("'Normal-", "'", 1000, 0.05),
		new PrePostStringGenerator("'Dense-", "'", 10, 0.05),
		
		new RangedIntegerGenerator(-500000, 500000, 0.05),
		new RangedIntegerGenerator(-500, 500, 0.05),
		new RangedDoubleGenerator(-500000, 500000, 100, 0.05),
		new RangedDoubleGenerator(-5000, 5000, 100, 0.05)
	};
	
	public static final String[] COLUMNS = {
		"Country",
		"State",
		"Name",
		"Zodiac Sign",
		"Day of Week",
		"Sparse Data",
		"Normal Data",
		"Dense Data",
		"Int Data A",
		"Int Data B",
		"Double Data A",
		"Double Data B"
	};
	
	public static final String[] DB_COLUMNS = {
		"Country varchar(50)",
		"State varchar(50)",
		"Name varchar(50)",
		"Zodiac varchar(50)",
		"Day varchar(50)",
		"Sparse_Data varchar(50)",
		"Normal_Data varchar(50)",
		"Dense_Data varchar(50)",
		"Int_Data_A int",
		"Int_Data_B int",
		"Double_Data_A decimal",
		"Double_Data_B decimal"
	};
	
	public static final List<String> COLUMN_LABELS = Arrays.asList(new String[] {
			"Country", "State", "Name", "Zodiac Sign", "Day",
			"Sparse Data", "Normal Data", "Dense Data", 
			"Int Data A", "Int Data B", "Double Data A", "Double Data B"
	});
	
}
