package su.invoice.exampleapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import su.invoice.invoicepaymentframe.PaymentFrame;
import su.invoice.invoicepaymentframe.PaymentStatus;


public class MainActivity extends AppCompatActivity {

    PaymentFrame paymentFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paymentFrame = findViewById(R.id.paymentFrame);
        String id = getIntent().getStringExtra("paymentId");

        paymentFrame.loadPayment(id);
        paymentFrame.setCallback(new PaymentFrame.PaymentCallback() {
            @Override
            public void onError(@Nullable String statusMsg) {
                goBack("Ошибка при оплате заказа");
            }

            @Override
            public void onSuccess(PaymentStatus status) {
                goBack("Заказ оплачен успешно");
            }

            @Override
            public void onOtherStatus(PaymentStatus status, @Nullable String statusMsg) {

            }
        });
    }

    public void goBack(String message) {
        Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
        startActivity(intent);
        finish();

        Toast toast = Toast.makeText(MainActivity.this,
                message,
                Toast.LENGTH_LONG);
        toast.show();
    }
}
