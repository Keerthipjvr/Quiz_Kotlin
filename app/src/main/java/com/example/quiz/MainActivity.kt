package com.example.quiz

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(){

    lateinit var questionsList :  ArrayList<QuestionModel>
    private var index:Int = 0
    lateinit var questionModel : QuestionModel

    private var correctAnswerCount:Int = 0
    private var wrongAnswerCount:Int=0

    lateinit var countDown:TextView
    lateinit var questions:TextView
    lateinit var option1:Button
    lateinit var option2:Button
    lateinit var option3:Button
    lateinit var option4:Button


    private var backPressedTime: Long = 0
    private var backToast: Toast?=null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        countDown = findViewById(R.id.countdown)
        questions = findViewById(R.id.questions)
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)


        questionsList = ArrayList()
        questionsList.add(QuestionModel("How many months are there in a year?","12", "6", "10","7","12"))
        questionsList.add(QuestionModel("How many days are there in a week?", "5", "6", "7","12","12"))
        questionsList.add(QuestionModel("Which gas do Humans need to breath to live?","Carbon Di Oxide","Oxygen","Nitrogen", "Hydrogen","Oxygen"))
        questionsList.add(QuestionModel("which animal can run faster?","Cheetah","Leopard","Tiger","Lion","Cheetah"))
        questionsList.add(QuestionModel("Name one plant that can grow in desert?","Rose","Jasmine","Cactus","Coconut", "Cactus"))
        questionsList.add(QuestionModel("Which city is called Pearl city?","Bangalore","Hyderabad","Mumbai","Vizag","Hyderabad"))
        questionsList.add(QuestionModel("A figure with six sides are called?","Pentagon","Triangle","Square","Hexagon","Hexagon"))
        questionsList.add(QuestionModel("What is AutoPhobia?","Fear of Heights","Fear of Sounds","Fear of Bathing","Fear of Using Automobiles","Fear of Bathing"))
        questionsList.add(QuestionModel("What is the Largest river in the world?","Amazon","Yamuna","Godavari","Krishna","Amazon"))




        questionsList.shuffle()
        questionModel = questionsList[index]

        setAllQuestions()

        countDown()

    }

    fun countDown(){
        var duration:Long=TimeUnit.SECONDS.toMillis(10)

        object :CountDownTimer(duration, 500){
            override fun onTick(millisUntilFinished: Long){
                var sDuration:String=String.format(Locale.ENGLISH,"%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))

                countDown.text = sDuration

            }
            override fun onFinish(){
                index++
                if(index<questionsList.size){
                    questionModel=questionsList[index]
                    setAllQuestions()
                    resetBackground()
                    enableButton()
                    countDown()

                }
                else {
                    gameResult()
                }
            }
        }.start()
    }


    private fun correctAns(option:Button){
        option.background=getDrawable(R.drawable.right_bg)
        correctAnswerCount++
    }

    private fun wrongAns(option:Button){
        option.background=resources.getDrawable(R.drawable.wrong_bg)
        wrongAnswerCount++
    }

    private fun gameResult(){
        var intent = Intent(this, ResultActivity::class.java)

        intent.putExtra("correct",correctAnswerCount.toString())
        intent.putExtra("total", questionsList.size.toString())

        startActivity(intent)
    }


    private fun setAllQuestions(){
        questions.text=questionModel.question
        option1.text=questionModel.option1
        option2.text=questionModel.option2
        option3.text=questionModel.option3
        option4.text=questionModel.option4
    }

    private fun enableButton(){
        option1.isClickable=true
        option2.isClickable=true
        option3.isClickable=true
        option4.isClickable=true
    }

    private fun disableButton(){
        option1.isClickable=false
        option2.isClickable=false
        option3.isClickable=false
        option4.isClickable=false
    }

    private fun resetBackground() {
        option1.background = resources.getDrawable(R.drawable.option_bg)
        option2.background = resources.getDrawable(R.drawable.option_bg)
        option3.background = resources.getDrawable(R.drawable.option_bg)
        option4.background = resources.getDrawable(R.drawable.option_bg)
    }

    fun option1Clicked(view:View){
        disableButton()
        if(questionModel.option1==questionModel.answer){
            option1.background=resources.getDrawable(R.drawable.right_bg)

            correctAns(option1)
        }
        else{
            wrongAns(option1)
        }
    }


    fun option2Clicked(view:View){
        disableButton()
        if(questionModel.option2==questionModel.answer){
            option2.background=resources.getDrawable(R.drawable.right_bg)

            correctAns(option2)
        }
        else
        {
            wrongAns(option2)
        }
    }

    fun option3Clicked(view:View){
        disableButton()
        if(questionModel.option4==questionModel.answer){
            option4.background=resources.getDrawable(R.drawable.right_bg)

            correctAns(option3)
        }
        else
        {
            wrongAns(option3)
        }
    }

    fun option4Clicked(view: View){
        disableButton()
        if(questionModel.option4==questionModel.answer){
            option4.background=resources.getDrawable(R.drawable.right_bg)

            correctAns(option4)
        }
        else{
            wrongAns(option4)
        }
    }

    override fun onBackPressed(){
        if(backPressedTime+2000>System.currentTimeMillis()){
            backToast?.cancel()
            finish()
        }
        else{
            backToast?.show()
        }
        backPressedTime=System.currentTimeMillis()
    }
}

