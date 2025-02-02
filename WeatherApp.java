import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
public class WeatherApp {
    public static void main(String[] args) {
        String apiKey = "f95fe40554374d5407dc182753489de7";
        String city = "Mumbai";  //Suppose we want to fetch the weather data for Mumbai
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=Mumbai&appid=f95fe40554374d5407dc182753489de7" + city + "&appid=" + apiKey + "&units=metric";

        try {
            //Fetch data from API
            String response = fetchWeatherData(apiUrl);

            //If a valid response is received, it processes it and displays it.
            if (response != null && ! response.isEmpty()) {
                displayWeatherDetails(response);
            } else {
                System.out.println("Unable to fetch weather data. Please check your API key or internet connection.");
               } } catch(Exception e) {
                    System.err.println("An error occured while fetching weather data.");
                    e.printStackTrace();
                }
             
            
        }

        /*
         * Sends an HTTP GET request to the specified URL and retrieves the response as a string.
         * @param apiURl The API endpoint to fetch data from
         * @return The JSON response as a string
         * @throws Exception if an error occurs during the request
         */
        private static String fetchWeatherData(String apiUrl) throws Exception {
            StringBuilder response = new StringBuilder();
            //Open a connection to the API URL
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            //check the response code
            int status = connection.getResponseCode();
            if(status == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                }
            } else {
                System.out.println("Error: HTTP response code "+status);
            }
            connection.disconnect();
            return response.toString();
        }

        /*
         *Following code Parses the JSON response and displays the weather details in a readable format
         @param jsonResponse the JSON response from API
         */
        private static void displayWeatherDetails(String jsonResponse) {
            //PArse the json response
            JSONObject jsonObject = new JSONObject(jsonResponse);

            //Extract weather details
            String cityName = jsonObject.getString("name");
            jsonObject.getString("name");
            JSONObject main = jsonObject.getJSONObject("main");
            double temperature = main.getDouble("temp");
            double feelsLike = main.getDouble("feels_like");
            int humidity = main.getInt("humidity");
            String weatherCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");

            //Displaying the weather details
            System.out.println("Weather Report for " +cityName+ ":" );
            System.out.println("----------------------------------------------------------------------");
            System.out.println("Temperature: " +temperature+ "°C");
            System.out.println("Feels like: " +feelsLike+ "°C");
            System.out.println("Humidity: " +humidity+ "%");
            System.out.println("Condition: " +weatherCondition);
        }
    }

