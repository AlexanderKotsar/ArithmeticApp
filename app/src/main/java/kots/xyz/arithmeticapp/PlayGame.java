package kots.xyz.arithmeticapp;

import android.app.Activity;
import java.util.Random;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import android.content.SharedPreferences;

/**
 * Created by kots on 18.03.2017.
 */

public class PlayGame extends Activity implements OnClickListener{

    private int level = 0, answer = 0, operator = 0, operand1 = 0, operand2 = 0;

    //определим константы для четырех операторов, что позволит упорядочить обработку геймплея
    private final int ADD_OPERATOR = 0, SUBTRACT_OPERATOR = 1, MULTIPLY_OPERATOR = 2, DIVIDE_OPERATOR = 3;

    //добавим массив для отображения каждого из этих операторов, с индексом массива, соответствующего постоянной в каждом случае
    private String[] operators = {"+", "-", "x", "/"};

    private int[][] levelMin = {{1, 11, 21},{1, 5, 10},{2, 5, 10},{2, 3, 5}};
    private int[][] levelMax = {{10, 25, 50},{10, 20, 30},{5, 10, 15},{10, 50, 100}};

    private Random random;

    private TextView question, answerTxt, scoreTxt;
    private ImageView response;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, enterBtn, clearBtn;

    private SharedPreferences gamePrefs;
    public static final String GAME_PREFS = "ArithmeticFile";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //отображаем activity_playgame
        setContentView(R.layout.activity_playgame);


        gamePrefs = getSharedPreferences(GAME_PREFS, 0);

        //получаем ссылки на текст и вид изображения
        question = (TextView)findViewById(R.id.question);
        answerTxt = (TextView)findViewById(R.id.answer);
        response = (ImageView)findViewById(R.id.response);
        scoreTxt = (TextView)findViewById(R.id.score);

        //установливаем галочку/крестик изображения, ответ изначально невидимым
        response.setVisibility(View.INVISIBLE);

        //получаем ссылки на номер, очистку и ввод
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btn5 = (Button)findViewById(R.id.btn5);
        btn6 = (Button)findViewById(R.id.btn6);
        btn7 = (Button)findViewById(R.id.btn7);
        btn8 = (Button)findViewById(R.id.btn8);
        btn9 = (Button)findViewById(R.id.btn9);
        btn0 = (Button)findViewById(R.id.btn0);
        enterBtn = (Button)findViewById(R.id.enter);
        clearBtn = (Button)findViewById(R.id.clear);

        //слушаем кннопки
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn0.setOnClickListener(this);
        enterBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);

        //получаем количество пройденных уровней
                            //        Bundle extras = getIntent().getExtras();
                            //        if(extras != null) {
                            //            int passedLevel = extras.getInt("level", -1);
                            //            if(passedLevel>=0) level = passedLevel;
                            //        }

        if(savedInstanceState!=null){
            //restore state
            level=savedInstanceState.getInt("level");
            int exScore = savedInstanceState.getInt("score");
            scoreTxt.setText("Score: "+exScore);
        }
        else{
            Bundle extras = getIntent().getExtras();
            if(extras !=null)
            {
                int passedLevel = extras.getInt("level", -1);
                if(passedLevel>=0) level = passedLevel;
            }
        }

        random = new Random();
        chooseQuestion();
    }
    //метод будет выполняться каждый раз когда нужен новый вопрос. Он подберет операторов и операндов в случайном порядке, в пределах диапазона и уровня. Метод будет выводить вопрос к пользовательскому интерфейсу, готового для ответа пользователя
    private void chooseQuestion(){

        //сброс ответов в окне ввода
        answerTxt.setText("= ?");

        //выбор рандомного оператора в пределе диапазона
        operator = random.nextInt(operators.length);

        operand1 = getOperand();
        operand2 = getOperand();

        //исключаем результат со знаком -
        if(operator == SUBTRACT_OPERATOR){
            while(operand2>operand1){
                operand1 = getOperand();
                operand2 = getOperand();
            }
        }

        //обеспечиваем, что ответ = целое число
        else if(operator==DIVIDE_OPERATOR){
            while((((double)operand1/(double)operand2)%1 > 0) || (operand1==operand2)) {
                operand1 = getOperand();
                operand2 = getOperand();
            }
        }
        //считаем ответ
        switch(operator) {
            case ADD_OPERATOR:
                answer = operand1+operand2;
                break;
            case SUBTRACT_OPERATOR:
                answer = operand1-operand2;
                break;
            case MULTIPLY_OPERATOR:
                answer = operand1*operand2;
                break;
            case DIVIDE_OPERATOR:
                answer = operand1/operand2;
                break;
            default:
                break;
        }
        //отовражаем вопрос пользователю
        question.setText(operand1+" "+operators[operator]+" "+operand2);
    }

    private int getOperand(){
        //return operand number
        //возвращает целое число в соответствующем диапазоне
        return random.nextInt(levelMax[operator][level] - levelMin[operator][level] + 1) + levelMin[operator][level];
    }

    @Override
    public void onClick(View view) {

        //Узнаем, какая кнопка была нажата
        if(view.getId()==R.id.enter){
            //enter button
            String answerContent = answerTxt.getText().toString();

            if(!answerContent.endsWith("?")) {
                //we have an answer
                int enteredAnswer = Integer.parseInt(answerContent.substring(2));

                //храним количество правильных ответов в этом заходе
                int exScore = getScore();

                if(enteredAnswer==answer){
                    //correct
                    //обновляем счет, иконку
                    scoreTxt.setText("Score: "+(exScore+1));
                    response.setImageResource(R.drawable.tick);
                    response.setVisibility(View.VISIBLE);
                }else{
                    //incorrect
                    //сбрасываем результат к нулю и отображение крестика
                    scoreTxt.setText("Score: 0");
                    response.setImageResource(R.drawable.cross);
                    response.setVisibility(View.VISIBLE);
                }
                chooseQuestion();
            }
        }else if(view.getId()==R.id.clear){
            //сбросить ответ
            answerTxt.setText("= ?");
        }else {
            //сначала скрываем картинку ответа
            response.setVisibility(View.INVISIBLE);

            int enteredNum = Integer.parseInt(view.getTag().toString());

            if(answerTxt.getText().toString().endsWith("?"))
                answerTxt.setText("= " + enteredNum);
            else
                answerTxt.append("" + enteredNum);

            setHighScore();
        }
    }
    //считаем количество правильных ответов
    private int getScore(){
        String scoreStr = scoreTxt.getText().toString();
        return Integer.parseInt(scoreStr.substring(scoreStr.lastIndexOf(" ")+1));
    }

    private void setHighScore(){
        //set high score
        int exScore = getScore();
        if(exScore>0){
            //we have a valid score
            SharedPreferences.Editor scoreEdit = gamePrefs.edit();
            DateFormat dateForm = new SimpleDateFormat("dd MMMM yyyy");
            String dateOutput = dateForm.format(new Date());
            String scores = gamePrefs.getString("highScores", "");

            if(scores.length()>0){
                //we have existing scores
                List<Score> scoreStrings = new ArrayList<Score>();
                String[] exScores = scores.split("\\|");
                for(String eSc : exScores){
                    String[] parts = eSc.split(" - ");
                    scoreStrings.add(new Score(parts[0], Integer.parseInt(parts[1])));
                }
                Score newScore = new Score(dateOutput, exScore);
                scoreStrings.add(newScore);

                Collections.sort(scoreStrings);

                StringBuilder scoreBuild = new StringBuilder("");
                for(int s=0; s<scoreStrings.size(); s++){
                    if(s>=10) break;//only want ten
                    if(s>0) scoreBuild.append("|");//pipe separate the score strings
                    scoreBuild.append(scoreStrings.get(s).getScoreText());
                }
                //write to prefs
                scoreEdit.putString("highScores", scoreBuild.toString());
                scoreEdit.commit();
            }
            else{
                //no existing scores
                scoreEdit.putString("highScores", ""+dateOutput+" - "+exScore);
                scoreEdit.commit();
            }
        }
    }

    protected void onDestroy(){
        setHighScore();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // save state
        int exScore = getScore();
        savedInstanceState.putInt("score", exScore);
        savedInstanceState.putInt("level", level);
        super.onSaveInstanceState(savedInstanceState);
    }
}
