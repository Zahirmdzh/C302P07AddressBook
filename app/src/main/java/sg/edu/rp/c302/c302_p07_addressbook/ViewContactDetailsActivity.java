package sg.edu.rp.c302.c302_p07_addressbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class ViewContactDetailsActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etMobile;
    private Button btnUpdate, btnDelete;
    private int contactId;
    private AsyncHttpClient client;
    private String firstname,lastname,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact_details);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etMobile = findViewById(R.id.etMobile);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        contactId = intent.getIntExtra("contact_id",-1);

        client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("id",String.valueOf(contactId));



        client.get("http://10.0.2.2/C302_P07/getContactDetails.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    Log.i("JSON Results: ", response.toString());


                    etFirstName.setText(response.getString("firstname"));
                    etLastName.setText(response.getString("lastname"));
                    etMobile.setText(response.getString("mobile"));
                    Toast.makeText(getApplicationContext(),response.getString("success"),Toast.LENGTH_SHORT).show();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        //TODO: call getContactDetails.php with the id as a parameter
        //TODO: set the text fields with the data retrieved

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUpdateOnClick(v);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDeleteOnClick(v);
            }
        });
    }//end onCreate


    private void btnUpdateOnClick(View v) {
        //TODO: retrieve the updated text fields and set as parameters to be passed to updateContact.php


        firstname = etFirstName.getText().toString();
        lastname = etLastName.getText().toString();
        mobile = etMobile.getText().toString();
        RequestParams params = new RequestParams();
        params.put("id",contactId);
        params.add("firstname",firstname);
        params.add("lastname",lastname);
        params.add("mobile",mobile);


        client.post("http://10.0.2.2/C302_P07/updateContact.php",params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    Log.i("JSON Results: ", response.toString());



                    Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }//end btnUpdateOnClick

    private void btnDeleteOnClick(View v) {
        //TODO: retrieve the id and set as parameters to be passed to deleteContact.php



        RequestParams params = new RequestParams();
        params.put("id",contactId);


        client.post("http://10.0.2.2/C302_P07/deleteContact.php",params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    Log.i("JSON Results: ", response.toString());

                    Toast.makeText(getApplicationContext(),response.getString("success"),Toast.LENGTH_SHORT).show();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }//end btnDeleteOnClick

}//end class