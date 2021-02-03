package pl.krystian.springmap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class Covid19Confirmed {

    public static final String COVID_CONFIRMED_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    double ParseDouble(String str){
        if(str != null && str.length() >0){
            try {
                return Double.parseDouble(str);
            }catch (Exception e){
                return -1;
            }
        }else return 0;
    }

    @EventListener(ApplicationReadyEvent.class)
    public List<Point> getPoints() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(COVID_CONFIRMED_URL)).build();
        HttpResponse<String> request = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

//       System.out.println(request.body());


        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject(COVID_CONFIRMED_URL,String.class);

        StringReader stringReader = new StringReader(forObject);
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);

        List<Point> points = new ArrayList<>();

        for (CSVRecord strings :parser){
            double lat = ParseDouble(strings.get("Lat"));
            double lon = ParseDouble(strings.get("Long"));
            String  text = strings.get("2/2/21");
            points.add(new Point(lat,lon,text));
        }

       return points;
    }

}
