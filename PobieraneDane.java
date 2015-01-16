import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class PobieraneDane {

	String[] data = new String[5];
	String[] day = new String[5];
	int[] high = new int[5];
	int[] low = new int[5];
	String[] text = new String[5];
	int[] sunRiseSet = new int[4];
	String[] sunAmPmTime = new String[2];

	public PobieraneDane() throws UnsupportedEncodingException, IOException {

		URL link = new URL(
				"https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22elbl%C4%85g%2C%20ak%22)&format=xml&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
		String source = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(
				link.openStream(), "UTF-8"));
		String linia;
		while ((linia = in.readLine()) != null) {
			source += linia + "\n";
		}
		in.close();
		int astronomy = source.indexOf("sunrise=");
		String sunrise = source.substring(astronomy + 9, astronomy + 20);
		int sunriceIndex = sunrise.indexOf(" ");
		sunrise = sunrise.substring(0, sunriceIndex);
		sunriceIndex = sunrise.indexOf(":");
		sunRiseSet[0] = Integer.parseInt(sunrise.substring(0, sunriceIndex));
		sunRiseSet[1] = Integer.parseInt(sunrise.substring(sunriceIndex + 1));
		System.out.println(sunRiseSet[0] + ":" + sunRiseSet[1]);
		sunrise = source.substring(astronomy + 9, astronomy + 20);
		sunriceIndex = sunrise.indexOf(" ");
		sunrise = sunrise.substring(sunriceIndex + 1, sunriceIndex + 3);
		sunAmPmTime[0] = sunrise;

		astronomy = source.indexOf("sunset=");
		String sunSet = source.substring(astronomy + 8, astronomy + 19);
		sunriceIndex = sunSet.indexOf(" ");
		sunSet = sunSet.substring(0, sunriceIndex);
		sunriceIndex = sunSet.indexOf(":");
		sunRiseSet[2] = Integer.parseInt(sunSet.substring(0, sunriceIndex));
		sunRiseSet[3] = Integer.parseInt(sunSet.substring(sunriceIndex + 1));
		System.out.println(sunRiseSet[2] + ":" + sunRiseSet[3]);
		sunSet = source.substring(astronomy + 9, astronomy + 20);
		sunriceIndex = sunSet.indexOf(" ");
		sunSet = sunSet.substring(sunriceIndex + 1, sunriceIndex + 3);
		sunAmPmTime[1] = sunSet;

		int i = source.indexOf("<yweather:forecast");
		int a = source.indexOf("guid");
		String s = source.substring(i, a - 1);
		int q = 0;
		String e;
		for (int w = 0; w < 5; w++) {
			q = s.indexOf("/>");
			e = s.substring(0, q + 2);
			s = s.substring(q + 2, s.length()); // linijki tekstu
			data[w] = dataa(e); // data
			day[w] = dzien(e); // dzieñ
			high[w] = maxTemp(e); // max temp
			low[w] = minTemp(e); // min temp
			text[w] = text(e); // tekst
		}
		for (int w = 0; w < 5; w++) {
			System.out.println("Data: " + data[w]);
			System.out.println("Day: " + day[w]);
			System.out.println("High: " + high[w]);
			System.out.println("Low: " + low[w]);
			System.out.println("Text: " + text[w] + "\n\n");
		}
	}

	public static String dataa(String data) {
		String a, b, c;
		int d = data.indexOf("date=");
		String f = data.substring(d + 6, d + 17);
		int ac = f.indexOf(" ");
		if (Integer.parseInt((f.substring(0, ac))) > 9) {
			System.out.println("Jest wiêcej niz 9");
			a = f.substring(3, 6);
			b = f.substring(0, 3);
			c = f.substring(7, f.length());
		} else {
			a = f.substring(2, 5);
			b = f.substring(0, 2);
			c = f.substring(6, f.length() - 1);
		}
		if (a.equals("Jan")) {
			a = "stycznia";
		} else if (a.equals("Feb")) {
			a = "lutego";
		} else if (a.equals("Mar")) {
			a = "marca";
		} else if (a.equals("Apr")) {
			a = "kwietnia";
		} else if (a.equals("May")) {
			a = "maja";
		} else if (a.equals("Jun")) {
			a = "czerwca";
		} else if (a.equals("Jul")) {
			a = "lipca";
		} else if (a.equals("Aug")) {
			a = "sierpnia";
		} else if (a.equals("Sep")) {
			a = "wrzeœnia";
		} else if (a.equals("Oct")) {
			a = "paŸdziernika";
		} else if (a.equals("Nov")) {
			a = "listopada";
		} else if (a.equals("Dec")) {
			a = "grudnia";
		}
		f = b + a + " " + c;
		System.out.println(f);
		return f;
	}

	public static String dzien(String dzien) {
		int g = dzien.indexOf("day=");
		String f = dzien.substring(g + 5, g + 8);
		if (f.equals("Fri")) {
			f = "Pi¹tek";
		} else if (f.equals("Sat")) {
			f = "Sobota";
		} else if (f.equals("Sun")) {
			f = "Niedziela";
		} else if (f.equals("Mon")) {
			f = "Poniedzia³ek";
		} else if (f.equals("Tue")) {
			f = "Wtorek";
		} else if (f.equals("Wed")) {
			f = "Œroda";
		} else if (f.equals("Thu")) {
			f = "Czwartek";
		}
		return f;
	}

	public static int maxTemp(String maxTemp) {
		int g = maxTemp.indexOf("high=");
		String f = maxTemp.substring(g + 6, g + 8);
		int a = Integer.parseInt(f);
		return (((a - 32) * 5) / 9) * (-1);
	}

	public static int minTemp(String minTemp) {
		int g = minTemp.indexOf("low=");
		String f = minTemp.substring(g + 5, g + 7);
		int a = Integer.parseInt(f);
		a = (((a - 32) * 5) / 9) * (-1);

		return a;
	}

	public static String text(String text) {
		int g = text.indexOf("text=");
		int q = text.indexOf("/>");
		String f = text.substring(g + 6, q - 1);
		return f;
	}

}
