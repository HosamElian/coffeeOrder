//package com.example.justjava;
/**

 * IMPORTANT: Make sure you are using the correct package name.

 * This example uses the package name:

 * package com.example.android.justjava

 * If you get an error when copying this code into Android studio, update it to match teh package name found

 * in the project's AndroidManifest.xml file.

 **/
package com.example.coffeeorder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**

 * This app displays an order form to order coffee.

 */

public class MainActivity<view> extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    /**
     * This method is called when the order button is clicked.
     *
     */


    public void submitOrder(View view) {

        CheckBox checkBoxWhippiedCream =  findViewById(R.id.WhippedCream);
        boolean chekedcr = checkBoxWhippiedCream.isChecked();
        CheckBox checkBoxchocolate=  findViewById(R.id.chocolate);
        boolean chekedch = checkBoxchocolate.isChecked();
        EditText textInput =findViewById(R.id.enterName);
        String textInputs =textInput.getText().toString();
        int price =calculatePrice(quantity,chekedcr,chekedch);
        hideKeyboard();
        String mesg =createOrderSummary(price,chekedcr,chekedch, textInputs);

        composeEmail(textInputs,mesg);
    }



    /**

     * This method displays the given quantity value on the screen.

     */

    private void display(int numbers) {

        TextView quantityTextView = findViewById(R.id.quantity_text_view);

        quantityTextView.setText("" + numbers);

    }
    /**

     * This method displays the given price on the screen.

     */


    int quantity =0 ;
    /*this method called when plus button is clicked */
    public void increment(View view) {
        quantity = quantity+1;
        display(quantity );
        if(quantity>=100){
            quantity=100;
            display(quantity );
            Toast.makeText(this, "can't be more than one hundred coffee", Toast.LENGTH_SHORT).show();
        }
    }
    /*this method called when minus button is clicked */
    public void decrement(View view) {
        quantity = quantity-1;
        display(quantity );
        if(quantity<=1){
            quantity=1;
            display(quantity );
            Toast.makeText(this, "can't be low than one coffee", Toast.LENGTH_SHORT).show();
        }
    }
    /**

     * Calculates the price of the order based on the current quantity.

     *

     * @return the price

     */

    private int calculatePrice(int quantity,boolean chkedcr,boolean chkedco) {

        int price =  quantity * 5;

        if(chkedcr==true){price+= quantity *1;}
        if (chkedco==true){price+=quantity *2;}

        return price;

    }
    /**
     *
     */

    private String createOrderSummary(int price,boolean chked,boolean chkedch,String name){
        String priceMessge="\n"+getResources().getString(R.string.Name_u)+name;
        priceMessge +="\n" + getResources().getString(R.string.Quantity)+quantity;
        priceMessge +="\n " + getResources().getString(R.string.Total)+ price;
        priceMessge +="\n"+ getResources().getString(R.string.WhippedCream) +chked ;
        priceMessge +="\n"+getResources().getString(R.string.chocolate) +chkedch ;
        priceMessge +="\n"+getResources().getString(R.string.Thank_You);
        return priceMessge;
    }



    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    public void composeEmail(String name,String maseg ) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "just java order"+name);
        intent.putExtra(Intent.EXTRA_TEXT, maseg);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}