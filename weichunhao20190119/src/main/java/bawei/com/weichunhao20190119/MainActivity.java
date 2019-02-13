package bawei.com.weichunhao20190119;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private int time =5;
    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time--;
            textView.setText(time+"秒");
            if (time==0){

                startActivity(new Intent(MainActivity.this,Main2Activity.class));
                finish();
                handler.removeMessages(0);
            }
            handler.sendEmptyMessageDelayed(0,1000);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.mTime);

        handler.sendEmptyMessageDelayed(0,1000);
    }
}
