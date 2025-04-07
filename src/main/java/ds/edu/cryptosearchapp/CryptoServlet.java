package ds.edu.cryptosearchapp;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet("/cryptoSearch")
public class CryptoServlet extends HttpServlet {

    private static final String API_KEY = "cdeabfd2-a23a-478d-86d4-cf26ff1f031f";
    private static final String CMC_API_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String symbol = request.getParameter("symbol");

        if (symbol == null || symbol.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing 'symbol' parameter.");
            return;
        }

        try {
            URL url = new URL(CMC_API_URL + "?symbol=" + symbol);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-CMC_PRO_API_KEY", API_KEY);
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder jsonText = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonText.append(line);
            }
            reader.close();

            JSONObject jsonResponse = new JSONObject(jsonText.toString());

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            OutputStream out = response.getOutputStream();
            out.write(jsonResponse.toString().getBytes());
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error occurred: " + e.getMessage());
        }
    }
}
