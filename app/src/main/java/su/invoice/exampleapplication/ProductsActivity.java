package su.invoice.exampleapplication;

import android.app.Activity;
import android.util.Log;
import backend.REST.RestClient;
import backend.REST.common.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import backend.InvoiceProcessing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class ProductsActivity extends AppCompatActivity {

    Thread networkThread;
    LinearLayout productsLayout;
    Button payButton;

    String terminal = "b88c719946e01b1396ba3724d634982c";

    public View.OnClickListener onPay = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            networkThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    final InvoiceProcessing invoiceProcessing = new InvoiceProcessing(terminal, false);
                    final List<ITEM> receipt = new ArrayList<>();

                    ITEM item1 = new ITEM();
                    item1.price = new BigDecimal("59.9");
                    item1.quantity = 1;
                    item1.resultPrice = new BigDecimal("59.9");
                    item1.name = "Суп";

                    ITEM item2 = new ITEM();
                    item2.price = new BigDecimal("10.0");
                    item2.quantity = 1;
                    item2.resultPrice = new BigDecimal("10.0");
                    item2.name = "Чай";

                    receipt.add(item1);
                    receipt.add(item2);

                    final String paymentId = invoiceProcessing.createPayment(receipt);
                    Log.d("INVOICE_PAYMENT","PAYMENT: "+paymentId);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            goPay(paymentId);
                        }
                    });
                }
            });
            networkThread.start();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        productsLayout = findViewById(R.id.productsLayout);
        payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener(onPay);

        this.loadProducts();
    }

    public void loadProducts() {
        ItemComponent item1 = new ItemComponent(this)
                .setCount(23)
                .setName("Суп")
                .setPrice(59.9);

        ItemComponent item2 = new ItemComponent(this)
                .setCount(1)
                .setName("Чай")
                .setPrice(10);

        productsLayout.addView(item1);
        productsLayout.addView(item2);
    }

    public void goPay(String id) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("paymentId", id);
        startActivity(intent);
    }
}
