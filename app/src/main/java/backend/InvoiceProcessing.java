package backend;




import android.util.Log;
import backend.REST.*;
import backend.REST.common.*;
import su.invoice.exampleapplication.BuildConfig;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class InvoiceProcessing {
    private static final String INVOICE_LOGIN = "demo";
    private static final String INVOICE_API_KEY = "1526fec01b5d11f4df4f2160627ce351";

    private static final String SHOP_NAME = "Тестовый магазин на Android";

    private RestClient restClient = new RestClient(INVOICE_LOGIN, INVOICE_API_KEY);
    private TerminalInfo terminalInfo;
    private PaymentInfo paymentInfo;

    private RestClient.Callback callback;

    public InvoiceProcessing() {
        CREATE_TERMINAL create_terminal = new CREATE_TERMINAL();
        create_terminal.type = TERMINAL_TYPE.dynamical;
        create_terminal.name = SHOP_NAME;

        terminalInfo = restClient.CreateTerminal(create_terminal);
        if(terminalInfo.error != null) {
            Log.d("ERROR", "Terminal Error: " + terminalInfo.description);
        }
    }

    public InvoiceProcessing(String terminal, boolean isAlias) {
        GET_TERMINAL get_terminal = new GET_TERMINAL("");

        if(isAlias) {
            get_terminal.alias = terminal;
        } else {
            get_terminal.id = terminal;
        }

        terminalInfo = restClient.GetTerminal(get_terminal);

        if(terminalInfo.error != null) {
            Log.d("ERROR", "Terminal Error: " + terminalInfo.description);
        }
    }

    public String createPayment(List<ITEM> receipt) {
        restClient.callback = this.callback;
        CREATE_PAYMENT create_payment = new CREATE_PAYMENT();

        SETTINGS settings = new SETTINGS();
        settings.terminal_id = terminalInfo.id;
        ORDER order = new ORDER();
        order.amount = getPriceFromReceipt(receipt);

        create_payment.settings = settings;
        create_payment.receipt = receipt;
        create_payment.order = order;

        paymentInfo = restClient.CreatePayment(create_payment);

        if(paymentInfo.error != null) {
            Log.d("ERROR", "Payment error: " + paymentInfo.description);
            return "";
        }

        return paymentInfo.id;
    }

    public void setCallback(RestClient.Callback callback) {
        this.callback = callback;
    }

    private BigDecimal getPriceFromReceipt(List<ITEM> receipt) {
        double price = 0;

        for(ITEM item : receipt) {
            price += item.resultPrice.doubleValue();
        }

        return new BigDecimal(String.valueOf(price));
    }
}
