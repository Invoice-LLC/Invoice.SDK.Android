package su.invoice.exampleapplication;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemComponent extends LinearLayout {

    private TextView name;
    private TextView count;
    private TextView price;

    public ItemComponent(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item, this);

        name = findViewById(R.id.itemName);
        count = findViewById(R.id.count);
        price = findViewById(R.id.price);
    }

    public ItemComponent setName(String name) {
        this.name.setText(name);
        return this;
    }

    public ItemComponent setCount(int count) {
        this.count.setText(String.valueOf(count));
        return this;
    }

    public ItemComponent setPrice(double price) {
        this.price.setText(String.valueOf(price));
        return this;
    }
}
