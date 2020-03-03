package su.invoice.exampleapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import su.invoice.invoicepaymentframe.PaymentFrame;


public class MainActivity extends AppCompatActivity {

    PaymentFrame paymentFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paymentFrame = findViewById(R.id.paymentFrame);
        String id = getIntent().getStringExtra("paymentId");

        paymentFrame.loadPayment(id);
    }
}
