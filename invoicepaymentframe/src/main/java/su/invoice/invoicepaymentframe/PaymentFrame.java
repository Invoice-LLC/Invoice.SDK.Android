package su.invoice.invoicepaymentframe;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PaymentFrame extends WebView {

    private static final String PAYMENT_URL = "https://pay.invoice.su/P";

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
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        setRedirectHandler();
    }

    public void loadPayment(String id) {
        loadUrl(PAYMENT_URL+id);
    }

    public void loadPayment(Uri uri) {
        loadUrl(uri.toString());
    }

    private void setRedirectHandler() {
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (    url.contains("pay.invoice.su/T")
                        || url.contains("api2.api8pc.ru")
                        || url.contains("mbmbank.ru")
                        || url.contains("pay.invoice.su/P")
                        || url.contains("pay.invoice.su/t")
                        || url.contains("pay.invoice.su/p")
                ) {
                    return false;
                }

                PaymentFrame.super.goBack();
                return true;
            }
        };
        super.setWebViewClient(webViewClient);
    }
}
