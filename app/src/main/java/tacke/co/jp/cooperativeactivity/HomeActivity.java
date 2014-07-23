package tacke.co.jp.cooperativeactivity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class HomeActivity extends ActionBarActivity {

    public static final String TAG = "home";

    enum RequestCode {
        PATH1,
        PATH2;

        // TODO this is ad-hoc method.
        public static RequestCode fromInteger(Integer integer) {
            return RequestCode.values()[integer];
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // TODO move to helper
        Button buttonPath1 = (Button)findViewById(R.id.button_path1);
        buttonPath1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(CooperativeActivity.path1Intent(HomeActivity.this), RequestCode.PATH1.ordinal());
            }
        });

        // TODO move to helper
        Button buttonPath2 = (Button)findViewById(R.id.button_path2);
        buttonPath2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(CooperativeActivity.path2Intent(HomeActivity.this), RequestCode.PATH2.ordinal());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // TODO change into class-base dispatch style
        dispatch(RequestCode.fromInteger(requestCode));
    }

    protected void dispatch(RequestCode requestCode) {
        switch (requestCode) {
            case PATH1:
                // some post processes
                Log.v(TAG, "path1 post process");
                break;
            case PATH2:
                // some post processes
                Log.v(TAG, "path2 post process");
                break;
        }
    }
}
