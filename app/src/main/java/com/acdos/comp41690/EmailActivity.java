package com.acdos.comp41690;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmailActivity extends AppCompatActivity implements View.OnClickListener {

    private Button sendButton;
    private EditText emailAddress;
    private EditText emailSubject;
    private EditText emailMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        sendButton = findViewById(R.id.email_send);
        emailAddress = findViewById(R.id.send_email_to);
        emailSubject = findViewById(R.id.email_subject);
        emailMessage = findViewById(R.id.email_message);
        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == sendButton) {
            if(emailAddress.getText().toString().matches("")) {
                Toast.makeText(this, "Please enter an email address!", Toast.LENGTH_LONG).show();
            }
            if(emailMessage.getText().toString().matches("")) {
                emailMessage.setText(R.string.email_mesage);
            }

            sendEMail();
        }
    }

    private void sendEMail() {
        String addressList = emailAddress.getText().toString();
        String[] addresses = addressList.split(",");
        String subject = emailSubject.getText().toString();
        String message = emailMessage.getText().toString();

        Intent i  = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_EMAIL, addresses);
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, message);

        i.setType("message/rfc822");
        startActivity(Intent.createChooser(i, "Choose an email client."));
        finish();
    }
}
