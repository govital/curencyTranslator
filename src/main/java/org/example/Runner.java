package org.example;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class Runner {
    private ReadCSV readCSV = new ReadCSV();
    public void runMe() {
        try {
            List<String[]> listFromFile = this.readCSV.read();

            for (String[] line : listFromFile) {
                if ("Base".equals(line[0])) {
                    continue;
                }
                String base = line[0];
                String sum = line[1];
                String target = line[2];

                String urlStr = String.format("https://api.exchangeratesapi.io/latest?base=%s&symbols=%s&access_key=d6943c813d6c3a3d1ca6793e8232a096", base, target);
                URL url = new URL(urlStr);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

//            Map<String, String> parameters = new HashMap<>();
//            parameters.put("param1", "val");

                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
//            out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();

//            con.setRequestProperty("Content-Type", "application/json");

                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);

                int status = con.getResponseCode();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                con.disconnect();

                Reader streamReader = null;

                if (status > 299) {
                    streamReader = new InputStreamReader(con.getErrorStream());
                } else {
                    streamReader = new InputStreamReader(con.getInputStream());
                }
                String res = content.toString();
                if (!res.contains("rates")){
                    continue;
                }
                JSONObject obj = new JSONObject(res);
                Float rate = obj.getJSONObject("rates").getFloat(target);
                Float convertedRate = rate * Integer.parseInt(sum);


                String answer = String.format("for the line: %s -> answer is: %s", Arrays.toString(line), convertedRate);
                System.out.println(answer);


            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
