package com.example.s7nodavetest;


import java.util.concurrent.TimeUnit;

import nodave.DataIsoTCP;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText address;
	private EditText test;
	private EditText value;
	
	final int STATUS_NONE = 0; // нет подключения
	final int STATUS_CONNECTING = 1; // подключаемся
	final int STATUS_CONNECTED = 2; // подключено
	final int NONE = 3;

	Handler h;
	//DataIsoTCP tp;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    address = (EditText) findViewById(R.id.plcaddress);
	    test = (EditText) findViewById(R.id.test);
	    value = (EditText) findViewById(R.id.value);
	    
	    h = new Handler() {
		      public void handleMessage(android.os.Message msg) {
		        switch (msg.what) {
		        case STATUS_NONE:
		          test.setText("Not connected");
		          break;
		        case STATUS_CONNECTING:
		          test.setText("Connecting");
		          break;
		        case STATUS_CONNECTED:
		          test.setText("Connected");
		          break;
		        case NONE:
			      test.setText("Initialisation");
			      break;
		        }
		      };
		    };
		    
		    h.sendEmptyMessage(NONE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/** Called when the user clicks the makeConnection button */
	public void makeConnection(View view) {  
	    Thread t = new Thread(new Runnable() {
		      public void run() {
		       
		    	  DataIsoTCP.ConnectIsoTcp(address.getText().toString());
		    	  if (DataIsoTCP.Connection) {
		    		 h.sendEmptyMessage(STATUS_CONNECTED);
		  		}else
		  			 h.sendEmptyMessage(STATUS_NONE);
		      }
		    });
		    t.start();
 	}
	/** Called when the user clicks the write button */
	public void writetoplc(View view) {
	    if (value.getText().length() == 0) {
	    	Toast.makeText(this, "Please enter a valid value",
	        Toast.LENGTH_LONG).show();
	    	address.setText("347");
	        return;
	    }else{
			long inputValue = Long.parseLong(value.getText().toString());
			if (DataIsoTCP.Connection) {
				test.setText("Writing data "+String.valueOf(inputValue));
				DataIsoTCP.WriteData(inputValue);
			}else{
				test.setText("Make first connection");
			}
	    }
 	}

	/** Called when the user clicks the read button */
	public void readfromplc(View view) {
		long var = 0;		
		if (DataIsoTCP.Connection) {
			test.setText("Reading data");
			var = DataIsoTCP.ReadData();
		}else{
			test.setText("Make first connection");
		}
		value.setText(String.valueOf(var));
 	}
}
