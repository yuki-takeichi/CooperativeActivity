package tacke.co.jp.cooperativeactivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import tacke.co.jp.cooperativeactivity.R;

/**
 * This is hidden activity that manage the process status of one sequence.
 * It must be hidden (not displayed), only cooperates with child Activities.
 */
public class CooperativeActivity extends ActionBarActivity {

    protected static final String EXTRA_PATH = "EXTRA_PATH";
    protected Status mStatus;
    protected RequestCode mRequestCode;

    public enum RequestCode {
        PATH1,
        PATH2;
    }

    enum Status {
        INITIAL_PATH1,
        INITIAL_PATH2,
        AFTER_A_PATH1,
        AFTER_B_PATH1,
        AFTER_B_PATH2;

        // TODO this is ad-hoc method.
        public static Status fromInteger(Integer integer) {
            return Status.values()[integer];
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cooperative);

        Intent intent = getIntent();
        mRequestCode = (RequestCode)intent.getSerializableExtra(EXTRA_PATH);
        initializeStatus(mRequestCode);
        callInitialActivity(mStatus);
    }

    // TODO rewrite to side-effect oriented style
    protected void initializeStatus(RequestCode requestCode) {
        switch (requestCode) {
            case PATH1:
                mStatus = Status.INITIAL_PATH1;
                break;
            case PATH2:
                mStatus = Status.INITIAL_PATH2;
                break;
        }
    }

    protected void callInitialActivity(Status status) {
        Intent intent;
        switch (status) {
            case INITIAL_PATH1:
                intent = AActivity.intent(this);
                break;
            case INITIAL_PATH2:
                intent = BActivity.intent(this);
                break;
            default:
                throw new RuntimeException("unexpected status of CooperativeActivity");
        }

        // To pass status ordinal value means to pass a sort of continuation. (maybe)
        startActivityForResult(intent, status.ordinal());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO how to treat resultCode
        dispatch(Status.fromInteger(requestCode), resultCode);
    }

    protected void dispatch(Status status, int resultCode) {
        switch (status) {
            case INITIAL_PATH1:
                mStatus = Status.AFTER_A_PATH1;
                startActivityForResult(BActivity.intent(this), mStatus.ordinal());
                break;
            case AFTER_A_PATH1:
                mStatus = Status.AFTER_B_PATH1;
                finish();
                break;
            case INITIAL_PATH2:
                mStatus = Status.AFTER_B_PATH2;
                finish();
                break;
            default:
                throw new RuntimeException("unexpected status of CooperativeActivity");
        }
    }

    public static Intent path1Intent(Context context) {
        Intent intent = new Intent(context, CooperativeActivity.class);
        intent.putExtra(EXTRA_PATH, RequestCode.PATH1);
        return intent;
    }

    public static Intent path2Intent(Context context) {
        Intent intent = new Intent(context, CooperativeActivity.class);
        intent.putExtra(EXTRA_PATH, RequestCode.PATH2);
        return intent;
    }

}
