package kots.xyz.arithmeticapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private Button playBtn, helpBtn, highBtn;
    private String[] levelNames = {"Easy", "Medium", "Hard"};


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

        //Создаем диалоговое окно в случае, если нажали на кнопку "Запустить"
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //передает массив имен уровней и настраиваем  слушателей для вариантов
        builder.setTitle("Choose a level")
                .setSingleChoiceItems(levelNames, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //start gameplay
                        startPlay(which);
                    }
                });

        AlertDialog ad = builder.create();
        ad.show();
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

    private void startPlay(int chosenLevel)
    {
        //start gameplay
        Intent playIntent = new Intent(this, PlayGame.class);
        playIntent.putExtra("level", chosenLevel);
        this.startActivity(playIntent);
    }
}
