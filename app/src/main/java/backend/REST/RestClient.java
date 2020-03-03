package backend.REST;

import android.util.Log;
import backend.REST.common.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;


public class RestClient {
    public String Url = "https://api.invoice.su/api/v2/";

    String authKey64;

    public String Login;
    public String ApiKey;
    public boolean Debug = false;

    public interface Callback {
        void onCompeteRequest(String result);
    }

    public Callback callback;

    public RestClient(String _Login, String _ApiKey) {
        Login = _Login;
        ApiKey = _ApiKey;
    }

    private String Send(String requestType, String json){
        try {
            URL url = new URL(Url + requestType);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            Log.d("INVOICE_PAYMENT", "IN: " + json);
            authKey64 = android.util.Base64.encodeToString((Login + ":" + ApiKey).getBytes(), 0);

            connection.setRequestProperty("Authorization", " Basic "+authKey64);

            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            byte[] data = json.getBytes();

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

            String output = result.toString();

            Log.d("INVOICE_PAYMENT", "OUT: " + output);
            if(callback != null)
                callback.onCompeteRequest("successful");

            return output;
        }catch (IOException e) {
            e.printStackTrace();
            return "null";
        }
    }

    public PaymentInfo CreatePayment(CREATE_PAYMENT request) {
        PaymentInfo result = new PaymentInfo();
        try {
            ObjectMapper mapper = new ObjectMapper();
            print("CreatePayment");
            String json = mapper.writeValueAsString(request);
            print("request: " +  json);
            String response = Send("CreatePayment", json);
            print("response: " + response);

            result = mapper.readValue(response, PaymentInfo.class);
        } catch (Exception exc){
            print(exc.toString());
        }
        return result;
    }

    public PaymentInfo GetPayment(GET_PAYMENT request) {
        PaymentInfo result = new PaymentInfo();
        try {
            ObjectMapper mapper = new ObjectMapper();
            print("GetPayment");
            String json = mapper.writeValueAsString(request);
            print("request: " +  json);
            String response = Send("GetPayment", json);
            print("response: " + response);
            result = mapper.readValue(response, PaymentInfo.class);
        } catch (Exception exc){
            print(exc.toString());
        }
        return result;
    }

    public PaymentInfo GetPaymentByOrder(GET_PAYMENT_FROM_ORDER request) {
        PaymentInfo result = new PaymentInfo();
        try {
            ObjectMapper mapper = new ObjectMapper();
            print("GetPaymentFromOrder");
            String json = mapper.writeValueAsString(request);
            print("request: " +  json);
            String response = Send("GetPaymentByOrder", json);
            print("response: " + response);
            result = mapper.readValue(response, PaymentInfo.class);
        } catch (Exception exc){
            print(exc.toString());
        }
        return result;
    }

    public PaymentInfo ClosePayment(CLOSE_PAYMENT request) {
        PaymentInfo result = new PaymentInfo();
        try {
            ObjectMapper mapper = new ObjectMapper();
            print("ClosePayment");
            String json = mapper.writeValueAsString(request);
            print("request: " +  json);
            String response = Send("ClosePayment", json);
            print("response: " + response);
            result = mapper.readValue(response, PaymentInfo.class);
        } catch (Exception exc){
            print(exc.toString());
        }
        return result;
    }

    public RefundInfo CreateRefund(CREATE_REFUND request) {
        RefundInfo result = new RefundInfo();
        try {
            ObjectMapper mapper = new ObjectMapper();
            print("CreateRefund");
            String json = mapper.writeValueAsString(request);
            print("request: " +  json);
            String response = Send("CreateRefund", json);
            print("response: " + response);
            result = mapper.readValue(response, RefundInfo.class);
        } catch (Exception exc){
            print(exc.toString());
        }
        return result;
    }

    public RefundInfo GetRefund(GET_REFUND request) {
        RefundInfo result = new RefundInfo();
        try {
            ObjectMapper mapper = new ObjectMapper();
            print("GetRefund");
            String json = mapper.writeValueAsString(request);
            print("request: " +  json);
            String response = Send("GetRefund", json);
            print("response: " + response);
            result = mapper.readValue(response, RefundInfo.class);
        } catch (Exception exc){
            print(exc.toString());
        }
        return result;
    }

    public TerminalInfo CreateTerminal(CREATE_TERMINAL request) {
        TerminalInfo result = new TerminalInfo();
        try {
            ObjectMapper mapper = new ObjectMapper();
            print("CreateTerminal");
            String json = mapper.writeValueAsString(request);
            print("request: " +  json);
            String response = Send("CreateTerminal", json);
            print("response: " + response);
            result = mapper.readValue(response, TerminalInfo.class);
        } catch (Exception exc){
            print(exc.toString());
        }
        return result;
    }

    public TerminalInfo GetTerminal(GET_TERMINAL request) {
        TerminalInfo result = new TerminalInfo();
        try {
            ObjectMapper mapper = new ObjectMapper();
            print("GetTerminal");
            String json = mapper.writeValueAsString(request);
            print("request: " +  json);
            String response = Send("GetTerminal", json);
            print("response: " + response);
            result = mapper.readValue(response, TerminalInfo.class);
        } catch (Exception exc){
            print(exc.toString());
        }
        return result;
    }
    
    private void print(String msg){
        System.out.println(msg);
    }
}
