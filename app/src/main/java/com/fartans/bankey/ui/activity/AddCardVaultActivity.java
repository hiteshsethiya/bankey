package com.fartans.bankey.ui.activity;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fartans.bankey.R;
import com.fartans.bankey.db.DbHandler;
import com.fartans.bankey.helper.CardVaultKeys;
import com.fartans.bankey.model.KeyValue;
import com.fartans.bankey.model.Vault;

import java.util.List;

public class AddCardVaultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card_vault);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_card_vault, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void SaveCardVault(View view) {
        String vaultName = "";
        int vaultPin;

        EditText editTextVaultName = (EditText) findViewById(R.id.edittextCardName);
        EditText editTextPasscode = (EditText) findViewById(R.id.editTextPasscode);

        vaultName = editTextVaultName.getText().toString();
        Vault vault = new Vault();

        EditText editTextCardNumber = (EditText) findViewById(R.id.edittextCardNumber);
        EditText editTextCardHolderName = (EditText) findViewById(R.id.edittextCardHolderName);
        EditText editTextCardExpiryMonth = (EditText) findViewById(R.id.edittextExpiryMonth);
        EditText editTextCardExpiryYear = (EditText) findViewById(R.id.edittextExpiryYear);

        String cardNumber = editTextCardNumber.getText().toString();
        String cardHolderName = editTextCardHolderName.getText().toString();
        String cardExpiryMonth = editTextCardExpiryMonth.getText().toString();
        String cardExpiryYear = editTextCardExpiryYear.getText().toString();

        if (!vaultName.matches("")) {
            vault.setName(vaultName);
            vault.setKeyNumber(0);
            vault.setIsSecure(2);

            if (!editTextPasscode.getText().toString().matches("")) {

                if(cardNumber.isEmpty()){
                    Toast.makeText(this, "Please Enter Card Number!", Toast.LENGTH_LONG).show();
                    editTextCardNumber.getBackground().setColorFilter(getResources().getColor(R.color.primaryColor), PorterDuff.Mode.SRC_ATOP);
                    return;
                }

                if(cardHolderName.isEmpty()){
                    Toast.makeText(this, "Please Enter Card Holder Name!", Toast.LENGTH_LONG).show();
                    editTextCardHolderName.getBackground().setColorFilter(getResources().getColor(R.color.primaryColor), PorterDuff.Mode.SRC_ATOP);
                    return;
                }

                if(cardExpiryMonth.isEmpty()){
                    Toast.makeText(this, "Please Enter Card Expiry Month!", Toast.LENGTH_LONG).show();
                    editTextCardExpiryMonth.getBackground().setColorFilter(getResources().getColor(R.color.primaryColor), PorterDuff.Mode.SRC_ATOP);
                    return;
                }

                if(cardExpiryYear.isEmpty()){
                    Toast.makeText(this, "Please Enter Card Expiry Year!", Toast.LENGTH_LONG).show();
                    editTextCardExpiryYear.getBackground().setColorFilter(getResources().getColor(R.color.primaryColor), PorterDuff.Mode.SRC_ATOP);
                    return;
                }

                vaultPin = Integer.parseInt(editTextPasscode.getText().toString());
                vault.setPasscode(vaultPin);
                vault.setIsSecure(2);
                DbHandler.insertVault(getApplicationContext(), vault);
            }else{
                Toast.makeText(getApplicationContext(), "Please Enter Passcode for the vault", Toast.LENGTH_SHORT).show();
                editTextPasscode.getBackground().setColorFilter(getResources().getColor(R.color.primaryColor), PorterDuff.Mode.SRC_ATOP);
                return;
            }

            List<Vault> vaults = DbHandler.readfromvaultwithKeyNumber(getApplicationContext());
            int vaultId = vaults.get(vaults.size() - 1).getId();

            KeyValue keyValue = new KeyValue();
            keyValue.setName(CardVaultKeys.CardNumber);
            keyValue.setValue(cardNumber);
            keyValue.setVaultId(vaultId);
            DbHandler.insertKeyValue(getApplicationContext(), keyValue);

            keyValue = new KeyValue();
            keyValue.setName(CardVaultKeys.CardHolderName);
            keyValue.setValue(cardHolderName);
            keyValue.setVaultId(vaultId);
            DbHandler.insertKeyValue(getApplicationContext(), keyValue);

            keyValue = new KeyValue();
            keyValue.setName(CardVaultKeys.CardExpiryMonth);
            keyValue.setValue(cardExpiryMonth);
            keyValue.setVaultId(vaultId);
            DbHandler.insertKeyValue(getApplicationContext(), keyValue);

            keyValue = new KeyValue();
            keyValue.setName(CardVaultKeys.CardExpiryYear);
            keyValue.setValue(cardExpiryYear);
            keyValue.setVaultId(vaultId);
            DbHandler.insertKeyValue(getApplicationContext(), keyValue);

            finish();

        } else {
            Toast.makeText(getApplicationContext(), "Please Enter All Fields", Toast.LENGTH_SHORT).show();
            editTextVaultName.getBackground().setColorFilter(getResources().getColor(R.color.primaryColor), PorterDuff.Mode.SRC_ATOP);
            return;
        }

    }
}
