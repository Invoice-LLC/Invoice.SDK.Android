package su.invoice.invoicepaymentframe;

import android.os.Handler;
import android.os.Looper;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

public class PaymentStatus {
    public String id;
    public String statusMsg;
    public int statusCode;
    public double amount;
    public double bonusGift;
    public double bonusAmount;

    public interface RequestCallback {
        void onComplete(PaymentStatus status);
    }

    public RequestCallback callback;

    public PaymentStatus get(String id) {
        this.id = id;
        JSONObject response = getStatusFromAPI(id);

        if(!response.isNull("transaction")) {
            try {
                JSONObject transaction = response.getJSONObject("transaction");
               amount = transaction.getDouble("amount");
               bonusGift = transaction.getDouble("bonus_gift");
               bonusAmount = transaction.getDouble("amountbonus");
               statusMsg = transaction.getString("statusMsg");
               statusCode = transaction.getInt("statusNum");
            } catch (JSONException ignore) { }
        }

        if(callback != null) {
            Handler mainHandler = new Handler(Looper.getMainLooper());

            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {
                    callback.onComplete(PaymentStatus.this);
                }
            };
            mainHandler.post(myRunnable);

        }

        return this;
    }

    private JSONObject getStatusFromAPI(String id) {
        String body = "{\"id\": \""+id+"\"}";
        String output = "";
        try {
            URL url = new URL("https://backend.invoice.su/api/customers.payStatus");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            byte[] data = body.getBytes();
            os.write(data);

            connection.connect();

            StringBuilder result = new StringBuilder();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            connection.disconnect();

            output = result.toString();
        } catch (IOException e) {
            output = "null";
        }

        return getJsonFromString(output);
    }

    private JSONObject getJsonFromString(String str) {
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }
}
