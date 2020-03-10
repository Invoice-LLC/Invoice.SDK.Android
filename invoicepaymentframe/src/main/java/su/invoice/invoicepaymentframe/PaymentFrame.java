package su.invoice.invoicepaymentframe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.*;
import androidx.annotation.Nullable;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

public class PaymentFrame extends WebView {

    private static final String PAYMENT_URL = "https://pay.invoice.su/P";
    private String paymentId;
    private PaymentCallback callback;

    public interface PaymentCallback {
        void onError(@Nullable String statusMsg);
        void onSuccess(PaymentStatus status);
        void onOtherStatus(PaymentStatus status, @Nullable String statusMsg);
    }

    public PaymentFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaymentFrame(Context context) {
        super(context);
        init();
    }

    private void init() {
        getSettings().setJavaScriptEnabled(true);
        getSettings().setAppCacheEnabled(true);
        setRedirectHandler();
    }

    public void loadPayment(String id) {
        paymentId = id;
        loadUrl(PAYMENT_URL+id);
    }

    public void loadPayment(Uri uri) {
        String url = uri.toString();
        paymentId = url
                .toLowerCase()
                .replace("http://","")
                .replace("https://","")
                .replace("pay.invoice.su/t", "")
                .replace("pay.invoice.su/p", "");
        loadUrl(url);
    }

    public void setCallback(PaymentCallback callback) {
        this.callback = callback;
    }

    private void setRedirectHandler() {
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if(url.toLowerCase().contains("customers.paystatus")) {
                    onResult();
                }
                return null;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.toLowerCase().contains("pay.invoice.su/t") || url.toLowerCase().contains("pay.invoice.su/p")) {
                    return false;
                }

                if(url.toLowerCase().contains("pay.invoice.su/status/")) {
                    onResult();
                    return true;
                }

                PaymentFrame.super.goBack();
                return true;
            }
        };
        super.setWebViewClient(webViewClient);
    }

    private void onResult() {
        final PaymentStatus status = new PaymentStatus();

        status.callback = new PaymentStatus.RequestCallback() {
            @Override
            public void onComplete(PaymentStatus s) {
                if(callback != null) {
                    switch (s.statusCode) {
                        case 2:
                            callback.onSuccess(s);
                            break;
                        case 6:
                            callback.onError(s.statusMsg);
                            break;
                        default:
                            callback.onOtherStatus(s, s.statusMsg);
                    }
                }
            }
        };

        Thread networkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                status.get(paymentId);
            }
        });
        networkThread.start();
    }

}
