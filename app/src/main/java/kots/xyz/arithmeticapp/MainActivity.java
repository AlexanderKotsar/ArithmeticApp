package kots.xyz.arithmeticapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private Button playBtn, helpBtn, highBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Получаем ссылки на элементы кнопки
        playBtn = (Button)findViewById(R.id.play_btn);
        helpBtn = (Button)findViewById(R.id.help_btn);
        highBtn = (Button)findViewById(R.id.high_btn);

        //Устанавливаем слушателя для этих кнопок
        playBtn.setOnClickListener(this);
        helpBtn.setOnClickListener(this);
        highBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        //выясняем, какая кнопка была нажата и создем блок кода для ответа на каждый
        if(view.getId()==R.id.play_btn){
            //play button
        }
        else if(view.getId()==R.id.help_btn){
            //how to play button
        }
        else if(view.getId()==R.id.high_btn){
            //high scores button
        }
    }
}
