package kots.xyz.arithmeticapp;

import android.app.Activity;
import java.util.Random;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kots on 18.03.2017.
 */

public class PlayGame extends Activity implements OnClickListener{

    private int level = 0, answer = 0, operator = 0, operand1 = 0, operand2 = 0;

    //определим константы для четырех операторов, что позволит упорядочить обработку геймплея
    private final int ADD_OPERATOR = 0, SUBTRACT_OPERATOR = 1, MULTIPLY_OPERATOR = 2, DIVIDE_OPERATOR = 3;

    //добавим массив для отображения каждого из этих операторов, с индексом массива, соответствующего постоянной в каждом случае
    private String[] operators = {"+", "-", "x", "/"};

    private int[][] levelMin = {
            {1, 11, 21},
            {1, 5, 10},
            {2, 5, 10},
            {2, 3, 5}};
    private int[][] levelMax = {
            {10, 25, 50},
            {10, 20, 30},
            {5, 10, 15},
            {10, 50, 100}};

    private Random random;

    private TextView question, answerTxt, scoreTxt;
    private ImageView response;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, enterBtn, clearBtn;





    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //отображаем activity_playgame
        setContentView(R.layout.activity_playgame);

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
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            int passedLevel = extras.getInt("level", -1);
            if(passedLevel>=0) level = passedLevel;
        }

        random = new Random();

        chooseQuestion();
    }

    @Override
    public void onClick(View view) {

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
            while((((double)operand1/(double)operand2)%1 > 0) || (operand1==operand2))
            {
                operand1 = getOperand();
                operand2 = getOperand();
            }
        }

        //считаем ответ
        switch(operator)
        {
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
        //Метод возвращает целое число в соответствующем диапазоне
        return random.nextInt(levelMax[operator][level] - levelMin[operator][level] + 1)
                + levelMin[operator][level];
    }



}
