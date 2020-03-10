<h1>Invoice payment frame для Android</h1>
<h3>PaymentFrame</h3>
Содержится в папке **invoicepaymentframe**<br>

Использование: <br>
Разместите на вашем layout'е элемент
```xml
    <su.invoice.invoicepaymentframe.PaymentFrame android:layout_width="match_parent"
                                                 android:layout_height="match_parent"
                                                 android:id="@+id/paymentFrame"/>
```
Затем нужно получить id платежа из API вашего магазина<br>
**Ни в коем случае не создавайте платеж на клиенте, как это сделано в примере(Папка app), это не безопасно**
И загрузить PaymentFrame, как это показано ниже
```java
public class MainActivity extends AppCompatActivity {
    PaymentFrame frame;
    
    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        frame = findViewById(R.id.paymentFrame);
    }
    
    public void pay(String paymentId) {
        frame.loadPayment(paymentId); // Загрузить страницу оплаты
        frame.setCallback(new PaymentFrame.PaymentCallback() {
            @Override
            public void onError(@Nullable String statusMsg) {
                //Действие при неудачной оплате
            }

            @Override
            public void onSuccess(PaymentStatus status) {
                //Действие при успешной оплате
            }

            @Override
            public void onOtherStatus(PaymentStatus status, @Nullable String statusMsg) {
                //Действие при других статусах оплаты
            }
        });
    }
}
```
